package com.project.listener;

import com.UIAutomation.annotation.Annotation;
import com.UIAutomation.driver.DriverManagement;
import com.UIAutomation.enums.AuthorValue;
import com.UIAutomation.enums.CategoryValue;
import com.UIAutomation.helpers.CaptureHelpers;
import com.UIAutomation.helpers.FileHelpers;
import com.UIAutomation.helpers.PropertiesHelpers;
import com.UIAutomation.helpers.ScreenRecorderHelpers;
import com.UIAutomation.keywords.WebUI;
import com.UIAutomation.report.ExtentReportManagement;
import com.UIAutomation.utils.BrowserInfoUtils;
import com.UIAutomation.utils.EmailSendUtils;
import com.UIAutomation.utils.LogUtils;
import com.UIAutomation.utils.ZipUtils;
import com.aventstack.extentreports.Status;
import org.testng.*;

import java.awt.*;
import java.io.IOException;

import static com.UIAutomation.constants.Constants.*;

public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    static int count_totalTCs;
    static int count_passedTCs;
    static int count_skippedTCs;
    static int count_failedTCs;

    private ScreenRecorderHelpers screenRecorder;

    public TestListener() {
        try {
            screenRecorder = new ScreenRecorderHelpers();
        } catch (IOException | AWTException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // Before every method in the Test Class
        //System.out.println(method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // After every method in the Test Class
        //System.out.println(method.getTestMethod().getMethodName());
    }

    @Override
    public void onStart(ISuite iSuite) {
        LogUtils.info("********** RUN STARTED **********");
        LogUtils.info("========= INSTALLING CONFIGURATION DATA =========");
//        try {
//            FileUtils.deleteDirectory(new File("target/allure-results"));
//            System.out.println("Deleted Directory target/allure-results");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        PropertiesHelpers.loadAllFiles();
        ExtentReportManagement.initReports();
        LogUtils.info("========= INSTALLED CONFIGURATION DATA =========");
        LogUtils.info("=====> Starting Suite: " + iSuite.getName());
    }

    @Override
    public void onFinish(ISuite iSuite) {
        LogUtils.info("********** RUN FINISHED **********");
        LogUtils.info("=====> End Suite: " + iSuite.getName());
        //End Suite and execute Extents Report
        ExtentReportManagement.flushReports();
        //Zip Folder report
        ZipUtils.zipReportFolder();
        //Send mail
        EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);

    }

    public AuthorValue[] getAuthorType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Annotation.class) == null) {
            return null;
        }
        AuthorValue authorType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Annotation.class).author();
        return authorType;
    }

    public CategoryValue[] getCategoryType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Annotation.class) == null) {
            return null;
        }
        CategoryValue categoryType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Annotation.class).category();
        return categoryType;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LogUtils.info("Test case: " + getTestName(iTestResult) + " is starting...");
        count_totalTCs = count_totalTCs + 1;

        ExtentReportManagement.createTest(iTestResult.getName());
        ExtentReportManagement.addAuthors(getAuthorType(iTestResult));
        ExtentReportManagement.addCategories(getCategoryType(iTestResult));
        ExtentReportManagement.addDevices();
        ExtentReportManagement.info(BrowserInfoUtils.getOSInfo());

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            screenRecorder.startRecording(getTestName(iTestResult));
        }

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LogUtils.info("Test case: " + getTestName(iTestResult) + " is passed.");
        count_passedTCs = count_passedTCs + 1;

        if (SCREENSHOT_PASSED_TCS.equals(YES)) {
            CaptureHelpers.captureScreenshot(DriverManagement.getDriver(), getTestName(iTestResult));
            ExtentReportManagement.addScreenShot(Status.PASS, getTestName(iTestResult));
        }

        ExtentReportManagement.logMessage(Status.PASS, "Test case: " + getTestName(iTestResult) + " is passed.");

        if (VIDEO_RECORD.trim().toLowerCase().equals(YES)) {
            WebUI.sleep(2);
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LogUtils.error("FAILED !! Test case " + getTestName(iTestResult) + " is failed.");
        LogUtils.error(iTestResult.getThrowable());

        count_failedTCs = count_failedTCs + 1;

        if (SCREENSHOT_FAILED_TCS.equals(YES)) {
            CaptureHelpers.captureScreenshot(DriverManagement.getDriver(), getTestName(iTestResult));
            ExtentReportManagement.addScreenShot(Status.FAIL, getTestName(iTestResult));
        }

        ExtentReportManagement.logMessage(Status.FAIL, iTestResult.getThrowable().toString());

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            WebUI.sleep(2);
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LogUtils.warn("WARNING!! Test case: " + getTestName(iTestResult) + " is skipped.");
        count_skippedTCs = count_skippedTCs + 1;

        if (SCREENSHOT_SKIPPED_TCS.equals(YES)) {
            CaptureHelpers.captureScreenshot(DriverManagement.getDriver(), getTestName(iTestResult));
        }

        ExtentReportManagement.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        ExtentReportManagement.logMessage("Test failed but it is in defined success ratio: " + getTestName(iTestResult));
    }

}

