package com.winProject.listener;


import com.WinUIAutomation.annotation.WinAppAnnotation;
import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.enums.WinAppAuthorValue;
import com.WinUIAutomation.enums.WinAppCategoryValue;
import com.WinUIAutomation.helpers.ScreenRecorderHelpers;
import com.WinUIAutomation.helpers.WinAppCaptureHelpers;
import com.WinUIAutomation.helpers.WinAppPropertiesHelpers;
import com.WinUIAutomation.keywords.WinUI;
import com.WinUIAutomation.report.WinAppExtentReportManagement;
import com.WinUIAutomation.utils.WinAppBrowserInfoUtils;
import com.WinUIAutomation.utils.WinAppEmailSendUtils;
import com.WinUIAutomation.utils.WinAppLogUtils;
import com.WinUIAutomation.utils.WinAppZipUtils;
import com.aventstack.extentreports.Status;
import org.testng.*;

import java.awt.*;
import java.io.IOException;

import static com.WinUIAutomation.constants.WinAppConstants.*;


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
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        // After every method in the Test Class
    }

    @Override
    public void onStart(ISuite iSuite) {
        WinAppLogUtils.info("********** RUN STARTED **********");
        WinAppLogUtils.info("========= INSTALLING CONFIGURATION DATA =========");

        WinAppPropertiesHelpers.loadAllFiles();
        WinAppExtentReportManagement.initReports();
        WinAppLogUtils.info("========= INSTALLED CONFIGURATION DATA =========");
        WinAppLogUtils.info("=====> Starting Suite: " + iSuite.getName());
    }

    @Override
    public void onFinish(ISuite iSuite) {
        WinAppLogUtils.info("********** RUN FINISHED **********");
        WinAppLogUtils.info("=====> End Suite: " + iSuite.getName());
        //End Suite and execute Extents Report
        WinAppExtentReportManagement.flushReports();
        //Zip Folder report
        WinAppZipUtils.zipReportFolder();
        //Send mail
        WinAppEmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);

    }

    public WinAppAuthorValue[] getAuthorType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(WinAppAnnotation.class) == null) {
            return null;
        }
        WinAppAuthorValue authorType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(WinAppAnnotation.class).author();
        return authorType;
    }

    public WinAppCategoryValue[] getCategoryType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(WinAppAnnotation.class) == null) {
            return null;
        }
        WinAppCategoryValue categoryType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(WinAppAnnotation.class).category();
        return categoryType;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        WinAppLogUtils.info("Test case: " + getTestName(iTestResult) + " is starting...");
        count_totalTCs = count_totalTCs + 1;

        WinAppExtentReportManagement.createTest(iTestResult.getName());
        WinAppExtentReportManagement.addAuthors(getAuthorType(iTestResult));
        WinAppExtentReportManagement.addCategories(getCategoryType(iTestResult));
        WinAppExtentReportManagement.addDevices();
        WinAppExtentReportManagement.info(WinAppBrowserInfoUtils.getOSInfo());

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            screenRecorder.startRecording(getTestName(iTestResult));
        }

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        WinAppLogUtils.info("Test case: " + getTestName(iTestResult) + " is passed.");
        count_passedTCs = count_passedTCs + 1;

        if (SCREENSHOT_PASSED_TCS.equals(YES)) {
            WinAppCaptureHelpers.captureScreenshot(WinAppDriverManagement.getDriver(), getTestName(iTestResult));
            WinAppExtentReportManagement.addScreenShot(Status.PASS, getTestName(iTestResult));
        }

        WinAppExtentReportManagement.logMessage(Status.PASS, "Test case: " + getTestName(iTestResult) + " is passed.");

        if (VIDEO_RECORD.trim().toLowerCase().equals(YES)) {
            WinUI.sleep(2);
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        WinAppLogUtils.error("FAILED !! Test case " + getTestName(iTestResult) + " is failed.");
        WinAppLogUtils.error(iTestResult.getThrowable());

        count_failedTCs = count_failedTCs + 1;

        if (SCREENSHOT_FAILED_TCS.equals(YES)) {
            WinAppCaptureHelpers.captureScreenshot(WinAppDriverManagement.getDriver(), getTestName(iTestResult));
            WinAppExtentReportManagement.addScreenShot(Status.FAIL, getTestName(iTestResult));
        }

        WinAppExtentReportManagement.logMessage(Status.FAIL, iTestResult.getThrowable().toString());

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            WinUI.sleep(2);
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        WinAppLogUtils.warn("WARNING!! Test case: " + getTestName(iTestResult) + " is skipped.");
        count_skippedTCs = count_skippedTCs + 1;

        if (SCREENSHOT_SKIPPED_TCS.equals(YES)) {
            WinAppCaptureHelpers.captureScreenshot(WinAppDriverManagement.getDriver(), getTestName(iTestResult));
        }

        WinAppExtentReportManagement.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        WinAppExtentReportManagement.logMessage("Test failed but it is in defined success ratio: " + getTestName(iTestResult));
    }

}

