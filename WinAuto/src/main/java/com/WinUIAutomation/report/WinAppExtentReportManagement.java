package com.WinUIAutomation.report;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.enums.WinAppAuthorValue;
import com.WinUIAutomation.enums.WinAppCategoryValue;
import com.WinUIAutomation.utils.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.util.Objects;

public class WinAppExtentReportManagement {

    private static ExtentReports extentReports;
    private static String link = "";

    public static void initReports() {
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();

            if (WinAppConstants.OVERRIDE_REPORTS.trim().equals(WinAppConstants.NO)) {
                WinAppLogUtils.info("OVERRIDE EXTENT REPORTS = " + WinAppConstants.OVERRIDE_REPORTS);
                link = WinAppConstants.EXTENT_REPORT_FOLDER_PATH + File.separator + WinAppDateUtils.getCurrentDateTimeCustom("_") + "_" + WinAppConstants.EXTENT_REPORT_FILE_NAME;
                WinAppLogUtils.info("Link Extent Report: " + link);
            } else {
                WinAppLogUtils.info("OVERRIDE EXTENT REPORTS = " + WinAppConstants.OVERRIDE_REPORTS);
                link = WinAppConstants.EXTENT_REPORT_FILE_PATH;
                WinAppLogUtils.info("Link Extent Report: " + link);
            }

            ExtentSparkReporter spark = new ExtentSparkReporter(link);
            extentReports.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(WinAppConstants.REPORT_TITLE);
            spark.config().setReportName(WinAppConstants.REPORT_TITLE);
            extentReports.setSystemInfo("Framework Name", WinAppConstants.REPORT_TITLE);
            extentReports.setSystemInfo("Author", WinAppConstants.AUTHOR);

            WinAppLogUtils.info("Extent Reports is installed.");
        }
    }
    public static String getReportLinkPath(){
        String reportLinkPath = link;
        System.out.println("Link Extent Report: " + reportLinkPath);
        return reportLinkPath;
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        WinAppExtentTestManagement.unload();
        WinAppReportUtils.openReports(link);
    }

    public static void createTest(String testCaseName) {
        WinAppExtentTestManagement.setExtentTest(extentReports.createTest(WinAppIconUtils.getBrowserIcon() + " " + testCaseName));
        //ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName));
    }

    public static void createTest(String testCaseName, String description) {
        WinAppExtentTestManagement.setExtentTest(extentReports.createTest(testCaseName, description));
    }

    public static void removeTest(String testCaseName) {
        extentReports.removeTest(testCaseName);
    }

    /**
     * Adds the screenshot.
     *
     * @param message the message
     */
    public static void addScreenShot(String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) WinAppDriverManagement.getDriver()).getScreenshotAs(OutputType.BASE64);

        //Base64 from Screenshot of Selenium
        WinAppExtentTestManagement.getExtentTest().log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

        //File Path from Screenshot of Java
        //ExtentTestManager.getExtentTest().log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(String.valueOf(CaptureHelpers.getScreenshotFile(message))).build());

    }

    /**
     * Adds the screenshot.
     *
     * @param status  the status
     * @param message the message
     */
    public static void addScreenShot(Status status, String message) {
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) WinAppDriverManagement.getDriver()).getScreenshotAs(OutputType.BASE64);

        //Base64 from Screenshot of Selenium
        WinAppExtentTestManagement.getExtentTest().log(status, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

        //File Path from Screenshot of Java
        //ExtentTestManager.getExtentTest().log(status, MediaEntityBuilder.createScreenCaptureFromPath(CaptureHelpers.getScreenshotAbsolutePath(message)).build());

    }

    synchronized public static void addAuthors(WinAppAuthorValue[] authors) {
        if (authors == null) {
            WinAppExtentTestManagement.getExtentTest().assignAuthor("DONDOC");
        } else {
            for (WinAppAuthorValue author : authors) {
                WinAppExtentTestManagement.getExtentTest().assignAuthor(author.toString());
            }
        }
    }

    // public static void addCategories(String[] categories) {
    synchronized public static void addCategories(WinAppCategoryValue[] categories) {
        if (categories == null) {
            WinAppExtentTestManagement.getExtentTest().assignCategory("REGRESSION");
        } else {
            // for (String category : categories) {
            for (WinAppCategoryValue category : categories) {
                WinAppExtentTestManagement.getExtentTest().assignCategory(category.toString());
            }
        }
    }

    synchronized public static void addDevices() {
        WinAppExtentTestManagement.getExtentTest().assignDevice(WinAppBrowserInfoUtils.getAppInfo());
//		ExtentReportManager.getExtentTest()
//				.assignDevice(BrowserIconUtils.getBrowserIcon() + " : " + BrowserInfoUtils.getBrowserInfo());
    }

    public static void logMessage(String message) {
        WinAppExtentTestManagement.getExtentTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        WinAppExtentTestManagement.getExtentTest().log(status, message);
    }

    public static void logMessage(Status status, Object message) {
        WinAppExtentTestManagement.getExtentTest().log(status, (Throwable) message);
    }

    public static void pass(String message) {
        //LogUtils.info("ExtentReportManager class: " + ExtentTestManager.getExtentTest());
        WinAppExtentTestManagement.getExtentTest().pass(message);
    }

    public static void pass(Markup message) {
        WinAppExtentTestManagement.getExtentTest().pass(message);
    }

    public static void fail(String message) {
        WinAppExtentTestManagement.getExtentTest().fail(message);
    }

    public static void fail(Object message) {
        WinAppExtentTestManagement.getExtentTest().fail((String) message);
    }

    public static void fail(Markup message) {
        WinAppExtentTestManagement.getExtentTest().fail(message);
    }

    public static void skip(String message) {
        WinAppExtentTestManagement.getExtentTest().skip(message);
    }

    public static void skip(Markup message) {
        WinAppExtentTestManagement.getExtentTest().skip(message);
    }

    public static void info(Markup message) {
        WinAppExtentTestManagement.getExtentTest().info(message);
    }

    public static void info(String message) {
        WinAppExtentTestManagement.getExtentTest().info(message);
    }

    public static void warning(String message) {
        WinAppExtentTestManagement.getExtentTest().log(Status.WARNING, message);
    }

}

