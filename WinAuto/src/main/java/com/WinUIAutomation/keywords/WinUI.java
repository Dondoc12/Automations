package com.WinUIAutomation.keywords;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.enums.WinAppFailureHandling;
import com.WinUIAutomation.helpers.WinAppHelpers;
import com.WinUIAutomation.report.WinAppExtentReportManagement;
import com.WinUIAutomation.report.WinAppExtentTestManagement;
import com.WinUIAutomation.utils.WinAppBrowserInfoUtils;
import com.WinUIAutomation.utils.WinAppDateUtils;
import com.WinUIAutomation.utils.WinAppLogUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.appium.java_client.windows.WindowsElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class WinUI {
    /**
     * The SoftAssert object is created
     */
    private static SoftAssert softAssert = new SoftAssert();

    /**
     * Stop the Soft Assert of TestNG
     */
    public static void stopSoftAssertAll() {
        softAssert.assertAll();
    }
    /**
     * Forced wait with unit of Seconds
     *
     * @param second is a positive integer corresponding to the number of Seconds
     */
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (second * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Smart Waits contains waitForPageLoaded and sleep functions
     */
    public static void smartWait(By by) {
        if (WinAppConstants.ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(),WinAppConstants.WAIT_EXPLICIT);
            wait.until(d -> WinAppDriverManagement.getDriver().findElement(by));
        sleep(WinAppConstants.WAIT_SLEEP_STEP);
    }
    }

    /* Element Control */
    /**
     * Convert the By object to the WebElement
     *
     * @param by is an element of type By
     * @return Returns a WebElement object
     */
    public static WindowsElement getWebElement(By by) {
        return WinAppDriverManagement.getDriver().findElement(by);
    }
    /**
     * Find multiple elements with the locator By object
     *
     * @param by is an element of type By
     * @return Returns a List of WebElement objects
     */
    public static List<WindowsElement> getWebElements(By by) {
        return WinAppDriverManagement.getDriver().findElements(by);
    }
    /**
     * Take entire-page screenshot and add to Extent report
     *
     * @param screenName Screenshot name
     */
    public static void addScreenshotToReport(String screenName) {
        if (WinAppConstants.SCREENSHOT_ALL_STEPS.equals(WinAppConstants.YES)) {
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.addScreenShot(WinAppHelpers.makeSlug(screenName));
            }
            //CaptureHelpers.captureScreenshot(DriverManagement.getDriver(), Helpers.makeSlug(screenshotName));
        }
    }
    public static void uploadFileFromLocalFile(By by,String filePath){
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(),WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        WindowsElement windowsElement = WinAppDriverManagement.getDriver().findElement(by);
        windowsElement.sendKeys(filePath);
        WinAppLogUtils.info("Upload File with Local Form: " + filePath);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.info("Upload File with Local Form: " + filePath);
        }
    }

    // Screenshot control
    /**
     * Take a screenshot of a specific web element. The captured image will be saved in '.png' format.
     *
     * @param screenName Screenshot name
     */
    public static void takeElementScreenshot(By by, String screenName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        try {
            String path = WinAppHelpers.getCurrentDir() + WinAppConstants.EXPORT_CAPTURE_PATH;
            File file = new File(path);
            if (!file.exists()) {
                WinAppLogUtils.info("No Folder: " + path);
                file.mkdir();
                WinAppLogUtils.info("Folder created: " + file);
            }

            File source = getWebElement(by).getScreenshotAs(OutputType.FILE);
            // result.getName() gets the name of the test case and assigns it to the screenshot file name
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            WinAppLogUtils.info("Screenshot taken: " + screenName);
            WinAppLogUtils.info("Screenshot taken current App: " + WinAppConstants.APP_NAME);
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
    /**
     * Take entire-page screenshot, including overflow parts. The captured image will be saved in '.png' format.
     * This method simulates a scroll action to take a number of shots and merge them together to make a full-page screenshot.
     *
     * @param screenName Screenshot name
     */
    public static void takeFullPageScreenshot(String screenName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        try {
            String path = WinAppHelpers.getCurrentDir() + WinAppConstants.EXPORT_CAPTURE_PATH;
            File file = new File(path);
            if (!file.exists()) {
                WinAppLogUtils.info("No Folder: " + path);
                file.mkdir();
                WinAppLogUtils.info("Folder created: " + file);
            }

            WinAppLogUtils.info("Driver for Screenshot: " + WinAppDriverManagement.getDriver());
            // Create reference of TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) WinAppDriverManagement.getDriver();
            // Call the capture screenshot function - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            // result.getName() gets the name of the test case and assigns it to the screenshot file name
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            WinAppLogUtils.info("Screenshot taken: " + screenName);
            WinAppLogUtils.info("Screenshot taken current App: " + WinAppConstants.APP_NAME);
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
    /**
     * Take a screenshot at the element location. Do not capture the entire screen.
     *
     * @param by          is an element of type By
     * @param elementName to name the .png image file
     */
    public static void screenshotElement(By by, String elementName) {
        File scrFile = getWebElement(by).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./" + elementName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // File Path Folder Control
    /**
     * Get the Download Directory path
     *
     * @return the download directory path
     */
    public static String getPathDownloadDirectory() {
        String path = "";
        String machine_name = System.getProperty("user.home");
        path = machine_name + File.separator + "Downloads Folder";
        return path;
    }
    /**
     * Delete all files in Download Directory
     */
    public static void deleteAllFileInDownloadDirectory() {
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File file = new File(pathFolderDownload);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
    /**
     * Delete all files in Download Directory
     *
     * @param pathDirectory the Download Directory path
     */
    public static void deleteAllFileInDirectory(String pathDirectory) {
        try {
            File file = new File(pathDirectory);
            File[] listOfFiles = file.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    new File(listOfFiles[i].toString()).delete();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
    /**
     * Count files in Download Directory
     *
     * @return files total in download directory
     */
    public static int countFilesInDownloadDirectory() {
        String pathFolderDownload = getPathDownloadDirectory();
        File file = new File(pathFolderDownload);
        int i = 0;
        for (File listOfFiles : file.listFiles()) {
            if (listOfFiles.isFile()) {
                i++;
            }
        }
        return i;
    }

    /* Verify Value */
    /**
     * Verify if the given web element is checked.
     *
     * @param by Represent a web element as the By object
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by) {
        smartWait(by);

        boolean checked = getWebElement(by).isSelected();
        if (checked == true) {
            return true;
        } else {
            Assert.assertTrue(false, "The element NOT checked.");
            return false;
        }
    }
    /**
     * Verify files in the Download Directory contain the specified file (CONTAIN)
     *
     * @param fileName the specified file
     * @return true if file is contain in download directory, else is false
     */
    public static boolean verifyFileContainsInDownloadDirectory(String fileName) {
        boolean flag = false;
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File dir = new File(pathFolderDownload);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                flag = false;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(fileName)) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            e.getMessage();
            return flag;
        }
    }
    /**
     * Verify files in the Download Directory contain the specified file (EQUALS)
     *
     * @param fileName the specified file
     * @return true if file is contain in download directory, else is false
     */
    public static boolean verifyFileEqualsInDownloadDirectory(String fileName) {
        boolean flag = false;
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File dir = new File(pathFolderDownload);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                flag = false;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().equals(fileName)) {
                    flag = true;
                }
            }
            return flag;
        } catch (Exception e) {
            e.getMessage();
            return flag;
        }
    }
    /**
     * Verify the file is downloaded (CONTAIN)
     *
     * @param fileName       the specified file
     * @param timeoutSeconds System will wait at most timeout (seconds) to return result
     * @return true if file is downloaded, else is false
     */
    public static boolean verifyDownloadFileContainsName(String fileName, int timeoutSeconds) {
        boolean check = false;
        int i = 0;
        while (i < timeoutSeconds) {
            boolean exist = verifyFileContainsInDownloadDirectory(fileName);
            if (exist == true) {
                i = timeoutSeconds;
                return check = true;
            }
            sleep(1);
            i++;
        }
        return check;
    }
    /**
     * Verify the file is downloaded (EQUALS)
     *
     * @param fileName       the specified file
     * @param timeoutSeconds System will wait at most timeout (seconds) to return result
     * @return true if file is downloaded, else is false
     */
    public static boolean verifyDownloadFileEqualsName(String fileName, int timeoutSeconds) {
        boolean check = false;
        int i = 0;
        while (i < timeoutSeconds) {
            boolean exist = verifyFileEqualsInDownloadDirectory(fileName);
            if (exist == true) {
                i = timeoutSeconds;
                return check = true;
            }
            sleep(1);
            i++;
        }
        return check;
    }
}

