package com.UIAutomation.report;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.driver.DriverManagement;
import com.UIAutomation.enums.AuthorValue;
import com.UIAutomation.enums.CategoryValue;
import com.UIAutomation.utils.*;
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

public class ExtentReportManagement {

    private static ExtentReports extentReports;
    private static String link = "";

    public static void initReports() {
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();

            if (Constants.OVERRIDE_REPORTS.trim().equals(Constants.NO)) {
                LogUtils.info("OVERRIDE EXTENT REPORTS = " + Constants.OVERRIDE_REPORTS);
                link = Constants.EXTENT_REPORT_FOLDER_PATH + File.separator + DateUtils.getCurrentDateTimeCustom("_") + "_" + Constants.EXTENT_REPORT_FILE_NAME;
                LogUtils.info("Link Extent Report: " + link);
            } else {
                LogUtils.info("OVERRIDE EXTENT REPORTS = " + Constants.OVERRIDE_REPORTS);
                link = Constants.EXTENT_REPORT_FILE_PATH;
                LogUtils.info("Link Extent Report: " + link);
            }

//            ExtentPDFReporter pdf = new ExtentPDFReporter("reports/ExtentReports/PdfReport.pdf");
//            try {
//                pdf.loadJSONConfig(new File("src/test/resources/pdf-config.json"));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            extentReports.attachReporter(pdf);

            ExtentSparkReporter spark = new ExtentSparkReporter(link);
            extentReports.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(Constants.REPORT_TITLE);
            spark.config().setReportName(Constants.REPORT_TITLE);
            extentReports.setSystemInfo("Framework Name", Constants.REPORT_TITLE);
            extentReports.setSystemInfo("Author", Constants.AUTHOR);

            LogUtils.info("Extent Reports is installed.");
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        ExtentTestManagement.unload();
        ReportUtils.openReports(link);
    }

    public static void createTest(String testCaseName) {
        ExtentTestManagement.setExtentTest(extentReports.createTest(IconUtils.getBrowserIcon() + " " + testCaseName));
        //ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName));
    }

    public static void createTest(String testCaseName, String description) {
        ExtentTestManagement.setExtentTest(extentReports.createTest(testCaseName, description));
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
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) DriverManagement.getDriver()).getScreenshotAs(OutputType.BASE64);

        //Base64 from Screenshot of Selenium
        ExtentTestManagement.getExtentTest().log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

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
        String base64Image = "data:image/png;base64," + ((TakesScreenshot) DriverManagement.getDriver()).getScreenshotAs(OutputType.BASE64);

        //Base64 from Screenshot of Selenium
        ExtentTestManagement.getExtentTest().log(status, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

        //File Path from Screenshot of Java
        //ExtentTestManager.getExtentTest().log(status, MediaEntityBuilder.createScreenCaptureFromPath(CaptureHelpers.getScreenshotAbsolutePath(message)).build());

    }

    synchronized public static void addAuthors(AuthorValue[] authors) {
        if (authors == null) {
            ExtentTestManagement.getExtentTest().assignAuthor("DONDOC");
        } else {
            for (AuthorValue author : authors) {
                ExtentTestManagement.getExtentTest().assignAuthor(author.toString());
            }
        }
    }

    // public static void addCategories(String[] categories) {
    synchronized public static void addCategories(CategoryValue[] categories) {
        if (categories == null) {
            ExtentTestManagement.getExtentTest().assignCategory("REGRESSION");
        } else {
            // for (String category : categories) {
            for (CategoryValue category : categories) {
                ExtentTestManagement.getExtentTest().assignCategory(category.toString());
            }
        }
    }

    synchronized public static void addDevices() {
        ExtentTestManagement.getExtentTest().assignDevice(BrowserInfoUtils.getBrowserInfo());
//		ExtentReportManager.getExtentTest()
//				.assignDevice(BrowserIconUtils.getBrowserIcon() + " : " + BrowserInfoUtils.getBrowserInfo());
    }

    public static void logMessage(String message) {
        ExtentTestManagement.getExtentTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        ExtentTestManagement.getExtentTest().log(status, message);
    }

    public static void logMessage(Status status, Object message) {
        ExtentTestManagement.getExtentTest().log(status, (Throwable) message);
    }

    public static void pass(String message) {
        //LogUtils.info("ExtentReportManager class: " + ExtentTestManager.getExtentTest());
        ExtentTestManagement.getExtentTest().pass(message);
    }

    public static void pass(Markup message) {
        ExtentTestManagement.getExtentTest().pass(message);
    }

    public static void fail(String message) {
        ExtentTestManagement.getExtentTest().fail(message);
    }

    public static void fail(Object message) {
        ExtentTestManagement.getExtentTest().fail((String) message);
    }

    public static void fail(Markup message) {
        ExtentTestManagement.getExtentTest().fail(message);
    }

    public static void skip(String message) {
        ExtentTestManagement.getExtentTest().skip(message);
    }

    public static void skip(Markup message) {
        ExtentTestManagement.getExtentTest().skip(message);
    }

    public static void info(Markup message) {
        ExtentTestManagement.getExtentTest().info(message);
    }

    public static void info(String message) {
        ExtentTestManagement.getExtentTest().info(message);
    }

    public static void warning(String message) {
        ExtentTestManagement.getExtentTest().log(Status.WARNING, message);
    }

}

