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
/**
 * Keyword WebUI is a generic class that is a preprocessed library with many custom functions from Selenium and Java.
 * Returns is a Class containing Static functions. Callback is used by taking the class name and dotting the function name (WebUI.method)
 * */
public class WinAppUI {

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
     * Smart Waits contains waitForPageLoaded and sleep functions
     */
    public static void smartWait() {
        if (WinAppConstants.ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
            waitForPageLoaded();
        }
        sleep(WinAppConstants.WAIT_SLEEP_STEP);
    }

    /**
     * Take entire-page screenshot and add to Extent report
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
            WinAppLogUtils.info("Screenshot taken current URL: " + getCurrentUrl());
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
            WinAppLogUtils.info("Screenshot taken current URL: " + getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }


    /**
     * Get the Download Directory path
     *
     * @return the download directory path
     */
    public static String getPathDownloadDirectory() {
        String path = "";
        String machine_name = System.getProperty("user.home");
        path = machine_name + File.separator + "Downloads";
        return path;
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
     * Verify the file is downloaded with JavascriptExecutor (EQUALS)
     *
     * @param fileName the specified file
     * @return true if file is downloaded, else is false
     */
    public static boolean verifyFileDownloadedWithJS_Equals(String fileName) {
        openWebsite("chrome://downloads");
        sleep(3);
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        String element = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#show').getAttribute('title')");
        File file = new File(element);
        WinAppLogUtils.info(element);
        WinAppLogUtils.info(file.getName());
        if (file.exists() && file.getName().trim().equals(fileName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verify the file is downloaded with JavascriptExecutor (CONTAINS)
     *
     * @param fileName the specified file
     * @return true if file is downloaded, else is false
     */
    public static boolean verifyFileDownloadedWithJS_Contains(String fileName) {
        openWebsite("chrome://downloads");
        sleep(3);
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        String element = (String) js.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#show').getAttribute('title')");
        File file = new File(element);
        WinAppLogUtils.info(element);
        WinAppLogUtils.info(file.getName());
        if (file.exists() && file.getName().trim().contains(fileName)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Get code text of QR Code image
     *
     * @param by là an element of object type By
     * @return text of QR Code
     */
    public static String getQRCodeFromImage(By by) {
        String qrCodeURL = WinAppUI.getAttributeElement(by, "src");
        //Create an object of URL Class
        URL url = null;
        try {
            url = new URL(qrCodeURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        //Pass the URL class object to store the file as image
        BufferedImage bufferedimage = null;
        try {
            bufferedimage = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Process the image
        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedimage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
        //To Capture details of QR code
        Result result = null;
        try {
            result = new MultiFormatReader().decode(binaryBitmap);
        } catch (com.google.zxing.NotFoundException e) {
            throw new RuntimeException(e);
        }
        return result.getText();
    }

    //Handle HTML5 validation message and valid value

    /**
     * Verify HTML5 message of element required field
     *
     * @param by is an element of type By
     * @return true/false corresponds to required
     */
    public static Boolean verifyHTML5RequiredField(By by) {
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        Boolean verifyRequired = (Boolean) js.executeScript("return arguments[0].required;", getWebElement(by));
        return verifyRequired;
    }

    /**
     * Verify the HTML5 message of the element has a value of Valid
     *
     * @param by is an element of type By
     * @return true/false corresponds to Valid
     */
    public static Boolean verifyHTML5ValidValueField(By by) {
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        Boolean verifyValid = (Boolean) js.executeScript("return arguments[0].validity.valid;", getWebElement(by));
        return verifyValid;
    }

    /**
     * Get HTML5 message of element
     *
     * @param by is an element of type By
     * @return the Text string value of the notification (String)
     */
    public static String getHTML5MessageField(By by) {
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        String message = (String) js.executeScript("return arguments[0].validationMessage;", getWebElement(by));
        return message;
    }

    /**
     * Set window sizes.
     *
     * @param widthPixel  is Width with Pixel
     * @param heightPixel is Height with Pixel
     */
    public static void setWindowSize(int widthPixel, int heightPixel) {
        WinAppDriverManagement.getDriver().manage().window().setSize(new Dimension(widthPixel, heightPixel));
    }

    /**
     * Move the window to the selected position X, Y from the top left corner 0
     *
     * @param X (int) - horizontal
     * @param Y (int) - vertical
     */
    public static void setWindowPosition(int X, int Y) {
        WinAppDriverManagement.getDriver().manage().window().setPosition(new Point(X, Y));
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
    /**
     * Get the JavascriptExecutor object created
     *
     * @return JavascriptExecutor
     */
    public static JavascriptExecutor getJsExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        return js;
    }

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
     * Print out the message in the Console log
     *
     * @param object passes any object
     */
    public static void logConsole(@Nullable Object object) {
        System.out.println(object);
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
     * Allow browser popup notifications on the website
     *
     * @return the value set Allow - belongs to the ChromeOptions object
     */
    public static ChromeOptions notificationsAllow() {
        // Create a Map to store options
        Map<String, Object> prefs = new HashMap<String, Object>();

        // Add keys and values to Map as follows to disable browser notifications
        // Pass argument 1 to ALLOW and 2 to BLOCK
        prefs.put("profile.default_content_setting_values.notifications", 1);

        // Create a ChromeOptions session
        ChromeOptions options = new ChromeOptions();

        // Use the setExperimentalOption function to execute the value through the above prefs object
        options.setExperimentalOption("prefs", prefs);

        //Returns the set value of the ChromeOptions object
        return options;
    }

    /**
     * Block browser popup notifications on the website
     *
     * @return the value of the setup Block - belongs to the ChromeOptions object
     */
    public static ChromeOptions notificationsBlock() {
        // Create a Map to store options
        Map<String, Object> prefs = new HashMap<String, Object>();

        // Add keys and values to Map as follows to disable browser notifications
        // Pass argument 1 to ALLOW and 2 to BLOCK
        prefs.put("profile.default_content_setting_values.notifications", 2);

        // Create a ChromeOptions session
        ChromeOptions options = new ChromeOptions();

        // Use the setExperimentalOption function to execute the value through the above prefs object
        options.setExperimentalOption("prefs", prefs);

        //Returns the set value of the ChromeOptions object
        return options;
    }

    /**
     * Uploading files with a click shows a form to select local files on your computer
     *
     * @param by       is an element of type By
     * @param filePath the absolute path to the file on your computer
     */
    public static void uploadFileWithLocalForm(By by, String filePath) {
        smartWait();

        Actions action = new Actions(WinAppDriverManagement.getDriver());
        //Click to open form upload
        action.moveToElement(getWebElement(by)).click().perform();
        sleep(3);

        // Create Robot class
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // Copy File path to Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        //Check OS for Windows
        if (WinAppBrowserInfoUtils.isWindows()) {
            // Press Control+V to paste
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);

            // Release the Control V
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(2000);
            // Press Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        //Check OS for MAC
        if (WinAppBrowserInfoUtils.isMac()) {
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(1000);

            //Open goto MAC
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_G);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_G);

            //Paste the clipboard value
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(1000);

            //Press Enter key to close the Goto MAC and Upload on MAC
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }

        WinAppLogUtils.info("Upload File with Local Form: " + filePath);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.info("Upload File with Local Form: " + filePath);
        }
    }

    /**
     * Upload files by dragging the link directly into the input box
     *
     * @param by       passes an element of object type By
     * @param filePath absolute path to the file
     */
    public static void uploadFileWithSendKeys(By by, String filePath) {
        smartWait();

        waitForElementVisible(by).sendKeys(filePath);

        WinAppLogUtils.info("Upload File with SendKeys");
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.info("Upload File with SendKeys");
        }
    }

    /**
     * Get current URL from current driver
     *
     * @return the current URL as String
     */
    public static String getCurrentUrl() {
        smartWait();
        WinAppLogUtils.info("Get Current URL: " + WinAppDriverManagement.getDriver().getCurrentUrl());
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.info("Get Current URL: " + WinAppDriverManagement.getDriver().getCurrentUrl());
        }
        return WinAppDriverManagement.getDriver().getCurrentUrl();
    }

    /**
     * Get current web page's title
     *
     * @return the current URL as String
     */
    public static String getPageTitle() {
        smartWait();
        String title = WinAppDriverManagement.getDriver().getTitle();
        WinAppLogUtils.info("Get Page Title: " + WinAppDriverManagement.getDriver().getTitle());
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.info("Get Page Title: " + WinAppDriverManagement.getDriver().getTitle());
        }
        return title;
    }

    /**
     * Verify the web page's title equals with the specified title
     *
     * @param pageTitle The title of the web page that needs verifying
     * @return the current URL as String
     */
    public static boolean VerifyPageTitle(String pageTitle) {
        smartWait();
        return getPageTitle().equals(pageTitle);
    }


    /**
     * Verify if the given text presents anywhere in the page source.
     *
     * @param text
     * @return true/false
     */
    public static boolean verifyPageContainsText(String text) {
        smartWait();
        return WinAppDriverManagement.getDriver().getPageSource().contains(text);
    }

    /**
     * Verify if the given web element is checked.
     *
     * @param by Represent a web element as the By object
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by) {
        smartWait();

        boolean checked = getWebElement(by).isSelected();
        if (checked == true) {
            return true;
        } else {
            Assert.assertTrue(false, "The element NOT checked.");
            return false;
        }
    }

    /**
     * Verify if the given web element is checked.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by, String message) {
        smartWait();
        waitForElementVisible(by);

        boolean checked = getWebElement(by).isSelected();

        if (checked == true) {
            return true;
        } else {
            Assert.assertTrue(false, message);
            return false;
        }
    }

    //Handle dropdown

    /**
     * Select value in dropdown dynamic (not pure Select Option)
     *
     * @param objectListItem is the locator of the list item as a By object
     * @param text           the value to select as Text of the item
     * @return click to select a specified item with Text value
     */
    public static boolean selectOptionDynamic(By objectListItem, String text) {
        smartWait();
        //For dynamic dropdowns (div, li, span,...not select options)
        try {
            List<WindowsElement> elements = getWebElements(objectListItem);

            for (WebElement element : elements) {
                WinAppLogUtils.info(element.getText());
                if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                    element.click();
                    return true;
                }
            }
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            e.getMessage();
        }
        return false;
    }

    /**
     * Verify All Options contains the specified text (select option)
     *
     * @param by   Represent a web element as the By object
     * @param text the specified text
     * @return true if all option contains the specified text
     */
    public static boolean verifyOptionDynamicExist(By by, String text) {
        smartWait();

        try {
            List<WindowsElement> elements = getWebElements(by);

            for (WindowsElement element : elements) {
                WinAppLogUtils.info(element.getText());
                if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                    return true;
                }
            }
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            e.getMessage();
        }
        return false;
    }

    /**
     * Get total number of options the given web element has. (select option)
     *
     * @param objectListItem Represent a web element as the By object
     * @return total number of options
     */
    public static int getOptionDynamicTotal(By objectListItem) {
        smartWait();

        WinAppLogUtils.info("Get total of Option Dynamic with list element. " + objectListItem);
        try {
            List<WindowsElement> elements = getWebElements(objectListItem);
            return elements.size();
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            e.getMessage();
        }
        return 0;
    }

    /**
     * Select the options with the given label (displayed text).
     *
     * @param by   Represent a web element as the By object
     * @param text the specified text of option
     */
    public static void selectOptionByText(By by, String text) {
        smartWait();
        Select select = new Select(getWebElement(by));
        select.selectByVisibleText(text);
        WinAppLogUtils.info("Select Option " + by + "by text " + text);
    }

    /**
     * Select the options with the given value.
     *
     * @param by    Represent a web element as the By object
     * @param value the specified value of option
     */
    public static void selectOptionByValue(By by, String value) {
        smartWait();

        Select select = new Select(getWebElement(by));
        select.selectByValue(value);
        WinAppLogUtils.info("Select Option " + by + "by value " + value);
    }

    /**
     * Select the options with the given index.
     *
     * @param by    Represent a web element as the By object
     * @param index the specified index of option
     */
    public static void selectOptionByIndex(By by, int index) {
        smartWait();

        Select select = new Select(getWebElement(by));
        select.selectByIndex(index);
        WinAppLogUtils.info("Select Option " + by + "by index " + index);
    }

    /**
     * Verify the number of options equals the specified total
     *
     * @param by    Represent a web element as the By object
     * @param total the specified options total
     */
    public static void verifyOptionTotal(By by, int total) {
        smartWait();

        Select select = new Select(getWebElement(by));
        WinAppLogUtils.info("Verify Option Total equals: " + total);
        Assert.assertEquals(total, select.getOptions().size());
    }

    /**
     * Verify if the options at the given text are selected.
     *
     * @param by   Represent a web element as the By object
     * @param text the specified options text
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByText(By by, String text) {
        smartWait();

        Select select = new Select(getWebElement(by));
        WinAppLogUtils.info("Verify Option Selected by text: " + select.getFirstSelectedOption().getText());

        if (select.getFirstSelectedOption().getText().equals(text)) {
            return true;
        } else {
            Assert.assertEquals(select.getFirstSelectedOption().getText(), text, "The option NOT selected. " + by);
            return false;
        }
    }

    /**
     * Verify if the options at the given value are selected.
     *
     * @param by    Represent a web element as the By object
     * @param value the specified options value
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByValue(By by, String value) {
        smartWait();

        Select select = new Select(getWebElement(by));
        WinAppLogUtils.info("Verify Option Selected by value: " + select.getFirstSelectedOption().getAttribute("value"));
        if (select.getFirstSelectedOption().getAttribute("value").equals(value)) {
            return true;
        } else {
            Assert.assertEquals(select.getFirstSelectedOption().getAttribute("value"), value, "The option NOT selected. " + by);
            return false;
        }
    }

    /**
     * Verify if the options at the given index are selected.
     *
     * @param by    Represent a web element as the By object
     * @param index the specified options index
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByIndex(By by, int index) {
        smartWait();

        Select select = new Select(getWebElement(by));
        int indexFirstOption = select.getOptions().indexOf(select.getFirstSelectedOption());
        WinAppLogUtils.info("The First Option selected by index: " + indexFirstOption);
        WinAppLogUtils.info("Expected index: " + index);
        if (indexFirstOption == index) {
            return true;
        } else {
            Assert.assertTrue(false, "The option NOT selected. " + by);
            return false;
        }
    }

    /**
     * Switch to iframe by index of iframe tag
     *
     * @param index index of iframe tag
     */
    public static void switchToFrameByIndex(int index) {
        smartWait();

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        //DriverManagement.getDriver().switchTo().frame(Index);
        WinAppLogUtils.info("Switch to Frame by Index. " + index);
    }

    /**
     * Switch to iframe by ID or Name of iframe tag
     *
     * @param IdOrName ID or Name of iframe tag
     */
    public static void switchToFrameByIdOrName(String IdOrName) {
        smartWait();

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
        WinAppLogUtils.info("Switch to Frame by ID or Name. " + IdOrName);
    }

    /**
     * Switch to iframe by Element is this iframe tag
     *
     * @param by iframe tag
     */
    public static void switchToFrameByElement(By by) {
        smartWait();

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        WinAppLogUtils.info("Switch to Frame by Element. " + by);
    }

    /**
     * Switch to Default Content
     */
    public static void switchToDefaultContent() {
        smartWait();

        WinAppDriverManagement.getDriver().switchTo().defaultContent();
        WinAppLogUtils.info("Switch to Default Content");
    }

    /**
     * Switch to iframe by position of iframe tag
     *
     * @param position index of iframe tag
     */
    public static void switchToWindowOrTabByPosition(int position) {
        smartWait();

        WinAppDriverManagement.getDriver().switchTo().window(WinAppDriverManagement.getDriver().getWindowHandles().toArray()[position].toString());
        WinAppLogUtils.info("Switch to Window or Tab by Position: " + position);
    }

    /**
     * Switch to popup window by title
     *
     * @param title title of popup window
     */
    public static void switchToWindowOrTabByTitle(String title) {
        smartWait();

        //Store the ID of the original window
        String originalWindow = WinAppDriverManagement.getDriver().getWindowHandle();

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        //Wait for the new window or tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        //Loop through until we find a new window handle
        for (String windowHandle : WinAppDriverManagement.getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                WinAppDriverManagement.getDriver().switchTo().window(windowHandle);
                if (WinAppDriverManagement.getDriver().getTitle().equals(title)) {
                    break;
                }
            }
        }

    }

    /**
     * Switch to popup window by URL
     *
     * @param url url of popup window
     */
    public static void switchToWindowOrTabByUrl(String url) {
        smartWait();
        //Store the ID of the original window
        String originalWindow = WinAppDriverManagement.getDriver().getWindowHandle();

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        //Wait for the new window or tab
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        //Loop through until we find a new window handle
        for (String windowHandle : WinAppDriverManagement.getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                WinAppDriverManagement.getDriver().switchTo().window(windowHandle);
                if (WinAppDriverManagement.getDriver().getCurrentUrl().equals(url)) {
                    break;
                }
            }
        }

    }

    /**
     * Close current Window
     */
    public static void closeCurrentWindow() {
        WinAppLogUtils.info("Close current Window." + getCurrentUrl());
        WinAppDriverManagement.getDriver().close();
        WinAppLogUtils.info("Close current Window");
    }


    /**
     * Get the total number of popup windows the given web page.
     *
     * @param number the specified number
     * @return true/false
     */
    public static boolean verifyTotalOfWindowsOrTab(int number) {
        return new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT).until(ExpectedConditions.numberOfWindowsToBe(number));
    }
    /**
     * Switch to Main Window
     */
    public static void switchToMainWindow() {
        smartWait();
        WinAppDriverManagement.getDriver().switchTo().window(WinAppDriverManagement.getDriver().getWindowHandles().toArray()[0].toString());
        WinAppLogUtils.info("Switch to Main Window." + WinAppDriverManagement.getDriver());
    }

    /**
     * Switch to Main Window by ID
     *
     * @param originalWindow ID of main window
     */
    public static void switchToMainWindow(String originalWindow) {
        smartWait();
        WinAppDriverManagement.getDriver().switchTo().window(originalWindow);
        WinAppLogUtils.info("Switch to Main Window." + originalWindow);
    }

    /**
     * Switch to Last Window
     */
    public static void switchToLastWindow() {
        smartWait();
        Set<String> windowHandles = WinAppDriverManagement.getDriver().getWindowHandles();
        WebDriver newDriver = WinAppDriverManagement.getDriver().switchTo().window(WinAppDriverManagement.getDriver().getWindowHandles().toArray()[windowHandles.size() - 1].toString());
        WinAppLogUtils.info("Switch to Last Window." + newDriver.getCurrentUrl());
    }

    /**
     * Click Accept on Alert
     */
    public static void acceptAlert() {
        sleep(WinAppConstants.WAIT_SLEEP_STEP);
        WinAppDriverManagement.getDriver().switchTo().alert().accept();
        WinAppLogUtils.info("Click Accept on Alert.");
    }

    /**
     * Click Dismiss on Alert
     */
    public static void dismissAlert() {
        sleep(WinAppConstants.WAIT_SLEEP_STEP);
        WinAppDriverManagement.getDriver().switchTo().alert().dismiss();
        WinAppLogUtils.info("Click Dismiss on Alert.");
    }

    /**
     * Get text on Alert
     */
    public static String getTextAlert() {
        sleep(WinAppConstants.WAIT_SLEEP_STEP);
        WinAppLogUtils.info("Get text ion alert: " + WinAppDriverManagement.getDriver().switchTo().alert().getText());
        return WinAppDriverManagement.getDriver().switchTo().alert().getText();
    }

    /**
     * Set text on Alert
     */
    public static void setTextAlert(String text) {
        sleep(WinAppConstants.WAIT_SLEEP_STEP);
        WinAppDriverManagement.getDriver().switchTo().alert().sendKeys(text);
        WinAppLogUtils.info("Set " + text + " on Alert.");
    }

    /**
     * Verify if alert does present
     *
     * @param timeOut Timeout waiting for alert to present.(in seconds)
     * @return true/false
     */
    public static boolean verifyAlertPresent(int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeOut);
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Throwable error) {
            WinAppLogUtils.error("Alert NOT Present.");
            Assert.fail("Alert NOT Present.");
            return false;
        }
    }

    /**
     * Get list text of specified elements
     *
     * @param by Represent a web element as the By object
     * @return Text list of specified elements
     */
    public static List<String> getListElementsText(By by) {
        smartWait();

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        List<WindowsElement> listElement = getWebElements(by);
        List<String> listText = new ArrayList<>();

        for (WebElement e : listElement) {
            listText.add(e.getText());
        }

        return listText;
    }

    /**
     * Verify if a web element is present (findElements.size > 0).
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementExists(By by) {
        smartWait();

        boolean res;
        List<WindowsElement> elementList = getWebElements(by);
        if (elementList.size() > 0) {
            res = true;
            WinAppLogUtils.info("Element existing");
        } else {
            res = false;
            WinAppLogUtils.error("Element not exists");

        }
        return res;
    }


    /**
     * Verify if two object are equal.
     *
     * @param value1 The object one
     * @param value2 The object two
     * @return true/false
     */
    public static boolean verifyEquals(Object value1, Object value2) {
        boolean result = value1.equals(value2);
        if (result == true) {
            WinAppLogUtils.info("Verify Equals: " + value1 + " = " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.pass("Verify Equals: " + value1 + " = " + value2);
            }
        } else {
            WinAppLogUtils.info("Verify Equals: " + value1 + " != " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.fail("Verify Equals: " + value1 + " != " + value2);
            }
            Assert.assertEquals(value1, value2, value1 + " != " + value2);
        }
        return result;
    }

    /**
     * Verify if two object are equal.
     *
     * @param value1  The object one
     * @param value2  The object two
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyEquals(Object value1, Object value2, String message) {
        boolean result = value1.equals(value2);
        if (result == true) {
            WinAppLogUtils.info("Verify Equals: " + value1 + " = " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.pass("Verify Equals: " + value1 + " = " + value2);
            }
        } else {
            WinAppLogUtils.info("Verify Equals: " + value1 + " != " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.fail("Verify Equals: " + value1 + " != " + value2);
            }
            Assert.assertEquals(value1, value2, message);
        }
        return result;
    }

    /**
     * Verify if the first object contains the second object.
     *
     * @param value1 The first object
     * @param value2 The second object
     * @return true/false
     */
    public static boolean verifyContains(String value1, String value2) {
        boolean result = value1.contains(value2);
        if (result == true) {
            WinAppLogUtils.info("Verify Equals: " + value1 + " CONTAINS " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.pass("Verify Contains: " + value1 + " CONTAINS " + value2);
            }
        } else {
            WinAppLogUtils.info("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.fail("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            }
            Assert.assertEquals(value1, value2, value1 + " NOT CONTAINS " + value2);
        }
        return result;
    }

    /**
     * Verify if the first object contains the second object.
     *
     * @param value1  The first object
     * @param value2  The second object
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyContains(String value1, String value2, String message) {
        boolean result = value1.contains(value2);
        if (result == true) {
            WinAppLogUtils.info("Verify Equals: " + value1 + " CONTAINS " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.pass("Verify Contains: " + value1 + " CONTAINS " + value2);
            }
        } else {
            WinAppLogUtils.info("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.fail("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            }
            Assert.assertEquals(value1, value2, message);
        }
        return result;
    }

    /**
     * Verify the condition is true.
     *
     * @return true/false
     */
    public static boolean verifyTrue(Boolean condition) {
        if (condition == true) {
            WinAppLogUtils.info("Verify TRUE: " + condition);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.pass("Verify TRUE: " + condition);
            }
        } else {
            WinAppLogUtils.info("Verify TRUE: " + condition);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.fail("Verify TRUE: " + condition);
            }
            Assert.assertTrue(condition, "The condition is FALSE.");
        }
        return condition;
    }

    /**
     * Verify the condition is true.
     *
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyTrue(Boolean condition, String message) {
        if (condition == true) {
            WinAppLogUtils.info("Verify TRUE: " + condition);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.pass("Verify TRUE: " + condition);
            }
        } else {
            WinAppLogUtils.info("Verify TRUE: " + condition);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.fail("Verify TRUE: " + condition);
            }
            Assert.assertTrue(condition, message);
        }
        return condition;
    }


    /**
     * Verify text of an element. (equals)
     *
     * @param by   Represent a web element as the By object
     * @param text Text of the element to verify.
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementText(By by, String text) {
        smartWait();
        waitForElementVisible(by);

        return getTextElement(by).trim().equals(text.trim());
    }

    /**
     * Verify text of an element. (equals)
     *
     * @param by          Represent a web element as the By object
     * @param text        Text of the element to verify.
     * @param flowControl Specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextEquals(By by, String text, WinAppFailureHandling flowControl) {
        smartWait();

        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().equals(text.trim());

        if (result == true) {
            WinAppLogUtils.info("Verify text of an element [Equals]: " + result);
        } else {
            WinAppLogUtils.warn("Verify text of an element [Equals]: " + result);
        }

        if (flowControl.equals(WinAppFailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
        }
        if (flowControl.equals(WinAppFailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
            if (result == false) {
                WinAppExtentReportManagement.fail("The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
            }
        }
        if (flowControl.equals(WinAppFailureHandling.OPTIONAL)) {
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.warning("Verify text of an element [Equals] - " + result);
                WinAppExtentReportManagement.warning("The actual text is '" + getTextElement(by).trim() + "' not equals expected text '" + text.trim() + "'");
            }
        }

        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());

        return getTextElement(by).trim().equals(text.trim());
    }

    /**
     * Verify text of an element. (equals)
     *
     * @param by   Represent a web element as the By object
     * @param text Text of the element to verify.
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextEquals(By by, String text) {
        smartWait();
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().equals(text.trim());

        if (result == true) {
            WinAppLogUtils.info("Verify text of an element [Equals]: " + result);
        } else {
            WinAppLogUtils.warn("Verify text of an element [Equals]: " + result);
        }

        Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.warning("Verify text of an element [Equals] : " + result);
            WinAppExtentReportManagement.warning("The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
        return result;
    }

    /**
     * Verify text of an element. (contains)
     *
     * @param by          Represent a web element as the By object
     * @param text        Text of the element to verify.
     * @param flowControl Specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextContains(By by, String text, WinAppFailureHandling flowControl) {
        smartWait();
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().contains(text.trim());

        if (result == true) {
            WinAppLogUtils.info("Verify text of an element [Contains]: " + result);
        } else {
            WinAppLogUtils.warn("Verify text of an element [Contains]: " + result);
        }

        if (flowControl.equals(WinAppFailureHandling.STOP_ON_FAILURE)) {
            Assert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        }
        if (flowControl.equals(WinAppFailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        }
        if (flowControl.equals(WinAppFailureHandling.OPTIONAL)) {
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.warning("Verify text of an element [Contains] - " + result);
            }
        }

        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());

        return getTextElement(by).trim().contains(text.trim());
    }

    /**
     * Verify text of an element. (contains)
     *
     * @param by   Represent a web element as the By object
     * @param text Text of the element to verify.
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextContains(By by, String text) {
        smartWait();
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().contains(text.trim());

        if (result == true) {
            WinAppLogUtils.info("Verify text of an element [Contains]: " + result);
        } else {
            WinAppLogUtils.warn("Verify text of an element [Contains]: " + result);
        }

        Assert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.info("Verify text of an element [Contains] : " + result);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());

        return result;
    }

    /**
     * Verify if the given element is clickable.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementClickable(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            WinAppLogUtils.info("Verify element clickable " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element clickable " + by);
            }
            return true;
        } catch (Exception e) {
            WinAppLogUtils.error(e.getMessage());
            Assert.fail("FAILED. Element not clickable " + by);
            return false;
        }
    }

    /**
     * Verify if the given element is clickable. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementClickable(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            WinAppLogUtils.info("Verify element clickable " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element clickable " + by);
            }
            return true;
        } catch (Exception e) {
            WinAppLogUtils.error("FAILED. Element not clickable " + by);
            WinAppLogUtils.error(e.getMessage());
            Assert.fail("FAILED. Element not clickable " + by);
            return false;
        }
    }

    /**
     * Verify if the given element is clickable. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementClickable(By by, int timeout, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            WinAppLogUtils.info("Verify element clickable " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element clickable " + by);
            }
            return true;
        } catch (Exception e) {
            WinAppLogUtils.error(message);
            WinAppLogUtils.error(e.getMessage());
            Assert.fail(message + "" + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementPresent(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WinAppLogUtils.info("Verify element present " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info("The element does NOT present. " + e.getMessage());
            Assert.fail("The element does NOT present. " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WinAppLogUtils.info("Verify element present " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info("The element does NOT present. " + e.getMessage());
            Assert.fail("The element does NOT present. " + e.getMessage());
            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WinAppLogUtils.info("Verify element present " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("The element does NOT present. " + e.getMessage());
                Assert.fail("The element does NOT present. " + e.getMessage());
            } else {
                WinAppLogUtils.error(message);
                Assert.fail(message);
            }

            return false;
        }
    }

    /**
     * Verify if the given web element does present on DOM. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, int timeout, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WinAppLogUtils.info("Verify element present " + by);
            if (WinAppExtentTestManagement.getExtentTest() != null) {
                WinAppExtentReportManagement.info("Verify element present " + by);
            }
            addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("The element does NOT present. " + e.getMessage());
                Assert.fail("The element does NOT present. " + e.getMessage());
            } else {
                WinAppLogUtils.error(message);
                Assert.fail(message);
            }

            return false;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WinAppLogUtils.error("The element presents. " + by);
            Assert.fail("The element presents. " + by);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WinAppLogUtils.error("Element is present " + by);
            Assert.fail("The element presents. " + by);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("The element presents. " + by);
                Assert.fail("The element presents. " + by);
            } else {
                WinAppLogUtils.error(message);
                Assert.fail(message + " " + by);
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify if the given web element does NOT present on the DOM. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by, int timeout, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("The element presents. " + by);
                Assert.fail("The element presents. " + by);
            } else {
                WinAppLogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify element is visible. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean isElementVisible(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            WinAppLogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify if the given web element is visible.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementVisible(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            WinAppLogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            Assert.fail("The element is NOT visible. " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is visible. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementVisible(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            WinAppLogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            WinAppLogUtils.error("The element is not visible. " + e.getMessage());
            Assert.fail("The element is NOT visible. " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is visible.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementVisible(By by, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            WinAppLogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("The element is not visible. " + by);
                Assert.fail("The element is NOT visible. " + by);
            } else {
                WinAppLogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element is visible. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementVisible(By by, int timeout, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            WinAppLogUtils.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("The element is not visible. " + by);
                Assert.fail("The element is NOT visible. " + by);
            } else {
                WinAppLogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        }
    }


    /**
     * Verify if the given web element is NOT visible.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            WinAppLogUtils.error("FAILED. The element is visible " + by);
            Assert.fail("FAILED. The element is visible " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            WinAppLogUtils.error("FAILED. The element is visible " + by);
            Assert.fail("FAILED. The element is visible " + by);
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("FAILED. The element is visible " + by);
                Assert.fail("FAILED. The element is visible " + by);
            } else {
                WinAppLogUtils.error(message + " " + by);
                Assert.fail(message + " " + by);
            }
            return false;
        }
    }

    /**
     * Verify if the given web element is NOT visible. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementNotVisible(By by, int timeout, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                WinAppLogUtils.error("FAILED. The element is visible " + by);
                Assert.fail("FAILED. The element is visible " + by);
            } else {
                WinAppLogUtils.error(message + " " + by);
                Assert.fail(message + " " + by);
            }
            return false;
        }
    }


    /**
     * Scroll an element into the visible area of the browser window. (at TOP)
     *
     * @param by Represent a web element as the By object
     */
    public static void scrollToElementAtTop(By by) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
        WinAppLogUtils.info("Scroll to element " + by);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at BOTTOM)
     *
     * @param by Represent a web element as the By object
     */
    public static void scrollToElementAtBottom(By by) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
        WinAppLogUtils.info("Scroll to element " + by);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at TOP)
     *
     * @param webElement Represent a web element as the By object
     */
    public static void scrollToElementAtTop(WebElement webElement) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
        WinAppLogUtils.info("Scroll to element " + webElement);
    }

    /**
     * Scroll an element into the visible area of the browser window. (at BOTTOM)
     *
     * @param webElement Represent a web element as the By object
     */
    public static void scrollToElementAtBottom(WebElement webElement) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", webElement);
        WinAppLogUtils.info("Scroll to element " + webElement);
    }

    /**
     * Scroll to an offset location
     *
     * @param X x offset
     * @param Y y offset
     */
    public static void scrollToPosition(int X, int Y) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
        WinAppLogUtils.info("Scroll to position X = " + X + " ; Y = " + Y);
    }

    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean hoverOnElement(By by) {
        smartWait();

        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            WinAppLogUtils.info("Hover on element " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean mouseHover(By by) {
        smartWait();

        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            WinAppLogUtils.info("Mouse hover on element " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Drag and drop an element onto another element.
     *
     * @param fromElement represent the drag-able element
     * @param toElement   represent the drop-able element
     * @return true/false
     */
    public static boolean dragAndDrop(By fromElement, By toElement) {
        smartWait();

        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            //action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Drag and drop an element onto another element. (HTML5)
     *
     * @param fromElement represent the drag-able element
     * @param toElement   represent the drop-able element
     * @return true/false
     */
    public static boolean dragAndDropHTML5(By fromElement, By toElement) {
        smartWait();

        try {
            Robot robot = new Robot();
            robot.mouseMove(0, 0);

            int X1 = getWebElement(fromElement).getLocation().getX() + (getWebElement(fromElement).getSize().getWidth() / 2);
            int Y1 = getWebElement(fromElement).getLocation().getY() + (getWebElement(fromElement).getSize().getHeight() / 2);
            System.out.println(X1 + " , " + Y1);

            int X2 = getWebElement(toElement).getLocation().getX() + (getWebElement(toElement).getSize().getWidth() / 2);
            int Y2 = getWebElement(toElement).getLocation().getY() + (getWebElement(toElement).getSize().getHeight() / 2);
            System.out.println(X2 + " , " + Y2);

            //This place takes the current coordinates plus 120px which is the browser header (1920x1080 current window)
            //Header: chrome is being controlled by automated test software
            sleep(1);
            robot.mouseMove(X1, Y1 + 120);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            sleep(1);
            robot.mouseMove(X2, Y2 + 120);
            sleep(1);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            return true;
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Drag an object and drop it to an offset location.
     *
     * @param fromElement represent the drag-able element
     * @param X           x offset
     * @param Y           y offset
     * @return true/false
     */
    public static boolean dragAndDropToOffset(By fromElement, int X, int Y) {
        smartWait();

        try {
            Robot robot = new Robot();
            robot.mouseMove(0, 0);
            int X1 = getWebElement(fromElement).getLocation().getX() + (getWebElement(fromElement).getSize().getWidth() / 2);
            int Y1 = getWebElement(fromElement).getLocation().getY() + (getWebElement(fromElement).getSize().getHeight() / 2);
            System.out.println(X1 + " , " + Y1);
            sleep(1);

            //This place takes the current coordinates plus 120px which is the browser header (1920x1080 current window)
            //Header: chrome is being controlled by automated test software
            robot.mouseMove(X1, Y1 + 120);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            sleep(1);
            robot.mouseMove(X, Y + 120);
            sleep(1);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Move to the given element.
     *
     * @param toElement Represent a web element as the By object
     * @return true/false
     */
    public static boolean moveToElement(By toElement) {
        smartWait();

        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Move to an offset location.
     *
     * @param X x offset
     * @param Y y offset
     * @return true/false
     */
    public static boolean moveToOffset(int X, int Y) {
        smartWait();

        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            return false;
        }
    }

    /**
     * Reload the current web page.
     */
    public static void reloadPage() {
        smartWait();

        WinAppDriverManagement.getDriver().navigate().refresh();
        waitForPageLoaded();
        WinAppLogUtils.info("Reloaded page " + WinAppDriverManagement.getDriver().getCurrentUrl());
    }


    /**
     * Fills the border color of the specified element.
     *
     * @param by passes the element object in the form By
     * @return Colors red borders for Elements on the website
     */
    public static WebElement highLightElement(By by) {
        smartWait();

        // draw a border around the found element
        if (WinAppDriverManagement.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) WinAppDriverManagement.getDriver()).executeScript("arguments[0].style.border='3px solid red'", waitForElementVisible(by));
            sleep(1);
            WinAppLogUtils.info("Highlight on element " + by);
        }
        return getWebElement(by);
    }

    /**
     * Navigate to the specified URL.
     *
     * @param URL the specified url
     */
    public static void openWebsite(String URL) {
        sleep(WinAppConstants.WAIT_SLEEP_STEP);

        WinAppDriverManagement.getDriver().get(URL);
        waitForPageLoaded();

        WinAppLogUtils.info("Open website with URL: " + URL);

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Open website with URL: " + URL);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());

    }

    /**
     * Navigate to the specified web page.
     *
     * @param URL the specified url
     */
    public static void navigateToUrl(String URL) {
        WinAppDriverManagement.getDriver().navigate().to(URL);
        waitForPageLoaded();

        WinAppLogUtils.info("Navigate to URL: " + URL);

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Navigate to URL: " + URL);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());

    }

    /**
     * Set the value of an input field
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     */
    public static void setText(By by, String value) {
        waitForElementVisible(by).sendKeys(value);
        WinAppLogUtils.info("Set text " + value + " on " + by.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Set text " + value + " on " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Set the value of an input field and press the key on the keyboard
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     * @param keys  key on the keyboard to press
     */
    public static void setText(By by, String value, Keys keys) {
        waitForElementVisible(by).sendKeys(value, keys);
        WinAppLogUtils.info("Set text " + value + " on " + by + " and press key " + keys.name());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Set text " + value + " on " + by + " and press key " + keys.name());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Simulates keystroke events on the specified element, as though you typed the value key-by-key.
     *
     * @param by   an element of object type By
     * @param keys key on the keyboard to press
     */
    public static void sendKeys(By by, Keys keys) {
        waitForElementVisible(by).sendKeys(keys);
        WinAppLogUtils.info("Press key " + keys.name() + " on element " + by);

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Press key " + keys.name() + " on element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Simulates keystroke events at the current position, as though you typed the value key-by-key.
     *
     * @param keys key on the keyboard to press
     */
    public static void sendKeys(Keys keys) {
        Actions actions = new Actions(WinAppDriverManagement.getDriver());
        actions.sendKeys(keys);
        WinAppLogUtils.info("Press key " + keys.name() + " on keyboard");

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Press key " + keys.name() + " on keyboard");
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Clear all text of the element.
     *
     * @param by an element of object type By
     */
    public static void clearText(By by) {
        waitForElementVisible(by).clear();
        WinAppLogUtils.info("Clear text in textbox " + by.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clear text in textbox " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Clear all text of the element with press Ctrl A > Delete
     *
     * @param by an element of object type By
     */
    public static void clearTextCtrlA(By by) {
        waitForElementVisible(by);
        Actions actions = new Actions(WinAppDriverManagement.getDriver());
        actions.click(getWebElement(by)).build().perform();
        //actions.moveToElement(getWebElement(by)).click().build();
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
        actions.sendKeys(Keys.DELETE).build().perform();

        WinAppLogUtils.info("Clear text in textbox " + by.toString());
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clear text in textbox " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Clear all text of the element then set the text on that element.
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     */
    public static void clearAndFillText(By by, String value) {
        waitForElementVisible(by).clear();
        waitForElementVisible(by).sendKeys(value);
        WinAppLogUtils.info("Clear and Fill " + value + " on " + by.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clear and Fill " + value + " on " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Click on the specified element.
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by) {
        waitForElementClickable(by).click();
        WinAppLogUtils.info("Clicked on the element " + by.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clicked on the element " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Click on element with timeout
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by, int timeout) {
        waitForElementClickable(by, timeout).click();
        WinAppLogUtils.info("Clicked on the element " + by.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clicked on the element " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Click on Elements on the web with Javascript (click implicitly without fear of being hidden)
     *
     * @param by an element of object type By
     */
    public static void clickElementWithJs(By by) {
        waitForElementPresent(by);
        //Scroll to element với Javascript Executor
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
        //Click with JS
        js.executeScript("arguments[0].click();", getWebElement(by));

        WinAppLogUtils.info("Click on element with JS: " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Click on element with JS: " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Click on the link on website with text
     *
     * @param linkText is the visible text of a link
     */
    public static void clickLinkText(String linkText) {
        smartWait();
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        WebElement elementWaited = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
        elementWaited.click();

        WinAppLogUtils.info("Click on link text " + linkText);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Click on link text " + linkText);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Right-click on the Element object on the web
     *
     * @param by an element of object type By
     */
    public static void rightClickElement(By by) {
        Actions action = new Actions(WinAppDriverManagement.getDriver());
        action.contextClick(waitForElementClickable(by)).build().perform();
        WinAppLogUtils.info("Right click on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Right click on element " + by);
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }

    /**
     * Get text of an element
     *
     * @param by an element of object type By
     * @return text of a element
     */
    public static String getTextElement(By by) {
        smartWait();
        WinAppLogUtils.info("Get text on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get text on element " + by);
            WinAppExtentReportManagement.pass("The Text is: " + waitForElementVisible(by).getText().trim());
        }
        return waitForElementVisible(by).getText().trim();
    }

    /**
     * Get the value from the element's attribute
     *
     * @param by            an element of object type By
     * @param attributeName attribute name
     * @return element's attribute value
     */
    public static String getAttributeElement(By by, String attributeName) {
        smartWait();
        WinAppLogUtils.info("Get attributeName on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get attributeName on element " + by);
            WinAppExtentReportManagement.pass("The attributeName is: " + waitForElementVisible(by).getAttribute(attributeName));
        }
        return waitForElementVisible(by).getAttribute(attributeName);
    }

    /**
     * Get CSS value of an element
     *
     * @param by      Represent a web element as the By object
     * @param cssName is CSS attribute name
     * @return value of CSS attribute
     */
    public static String getCssValueElement(By by, String cssName) {
        smartWait();
        WinAppLogUtils.info("Get attributeName on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get getCssValue on element " + by);
            WinAppExtentReportManagement.pass("The getCssValue is: " + waitForElementVisible(by).getCssValue(cssName));
        }
        return waitForElementVisible(by).getCssValue(cssName);
    }

    /**
     * Get size of specified element
     *
     * @param by Represent a web element as the By object
     * @return Dimension
     */
    public static Dimension getSizeElement(By by) {
        smartWait();
        WinAppLogUtils.info("Get element size on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get element size on " + by);
            WinAppExtentReportManagement.pass("The element size is: " + waitForElementVisible(by).getSize());
        }
        return waitForElementVisible(by).getSize();
    }

    /**
     * Get location of specified element
     *
     * @param by Represent a web element as the By object
     * @return Point
     */
    public static Point getLocationElement(By by) {
        smartWait();
        WinAppLogUtils.info("Get Element Location on element" + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get Element Location on " + by);
            WinAppExtentReportManagement.pass("The element Element Location is: " + waitForElementVisible(by).getLocation());
        }
        return waitForElementVisible(by).getLocation();
    }

    /**
     * Get tag name (HTML tag) of specified element
     *
     * @param by Represent a web element as the By object
     * @return Tag name as String
     */
    public static String getTagNameElement(By by) {
        smartWait();
        WinAppLogUtils.info("Get Element TagName on element" + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get Element TagName on " + by);
            WinAppExtentReportManagement.pass("The element Element TagName is: " + waitForElementVisible(by).getTagName());
        }
        return waitForElementVisible(by).getTagName();
    }

    /**
     * Check the value of each column of the table when searching according to EQUAL conditions (equals)
     *
     * @param column column position
     * @param value  value to compare
     */
    public static void checkEqualsValueOnTableByColumn(int column, String value) {
        smartWait();
        sleep(1);
        List<WindowsElement> totalRows = getWebElements(By.xpath("//tbody/tr"));
        WinAppLogUtils.info("Number of results for keywords (" + value + "): " + totalRows.size());

        if (totalRows.size() < 1) {
            WinAppLogUtils.info("Not found value: " + value);
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = waitForElementVisible(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                res = title.getText().toUpperCase().equals(value.toUpperCase());
                WinAppLogUtils.info("Row " + i + ": " + res + " - " + title.getText());
                Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + " equals no value: " + value);
            }
        }
    }

    /**
     * Check the value of each column of the table when searching according to the CONTAINS condition (contains)
     *
     * @param column column position
     * @param value  value to compare
     */
    public static void checkContainsValueOnTableByColumn(int column, String value) {
        smartWait();
        sleep(1);
        List<WindowsElement> totalRows = getWebElements(By.xpath("//tbody/tr"));
        WinAppLogUtils.info("Number of results for keywords (" + value + "): " + totalRows.size());

        if (totalRows.size() < 1) {
            WinAppLogUtils.info("Not found value: " + value);
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = waitForElementVisible(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                res = title.getText().toUpperCase().contains(value.toUpperCase());
                WinAppLogUtils.info("Row " + i + ": " + res + " - " + title.getText());
                Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + " contains no value: " + value);
            }
        }
    }

    /**
     * Check the value of each column of the table when searching according to the CONTAINS condition with custom xpath
     *
     * @param column           column position
     * @param value            value to compare
     * @param xpathToTRtagname xpath value up to TR tag
     */
    public static void checkContainsValueOnTableByColumn(int column, String value, String xpathToTRtagname) {
        smartWait();

        //xpathToTRtagname is locator from table to "tr" tagname of data section: //tbody/tr, //div[@id='example_wrapper']//tbody/tr, ...
        List<WindowsElement> totalRows = WinAppDriverManagement.getDriver().findElements(By.xpath(xpathToTRtagname));
        sleep(1);
        WinAppLogUtils.info("Number of results for keywords (" + value + "): " + totalRows.size());

        if (totalRows.size() < 1) {
            WinAppLogUtils.info("Not found value: " + value);
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = waitForElementVisible(By.xpath(xpathToTRtagname + "[" + i + "]/td[" + column + "]"));
                res = title.getText().toUpperCase().contains(value.toUpperCase());
                WinAppLogUtils.info("Row " + i + ": " + res + " - " + title.getText());
                Assert.assertTrue(res, "Row " + i + " (" + title.getText() + ")" + " contains no value " + value);
            }
        }
    }

    /**
     * Get the value of a column from the table
     *
     * @param column column position
     * @return array of values of a column
     */
    public static ArrayList getValueTableByColumn(int column) {
        smartWait();

        List<WindowsElement> totalRows = WinAppDriverManagement.getDriver().findElements(By.xpath("//tbody/tr"));
        sleep(1);
        WinAppLogUtils.info("Number of results for column (" + column + "): " + totalRows.size()); //Không thích ghi log thì xóa nhen

        ArrayList arrayList = new ArrayList<String>();

        if (totalRows.size() < 1) {
            WinAppLogUtils.info("Not found value !!");
        } else {
            for (int i = 1; i <= totalRows.size(); i++) {
                boolean res = false;
                WebElement title = WinAppDriverManagement.getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[" + column + "]"));
                arrayList.add(title.getText());
                WinAppLogUtils.info("Row " + i + ":" + title.getText()); //Không thích ghi log thì xóa nhen
            }
        }

        return arrayList;
    }

    //Wait Element

    /**
     * Wait until the given web element is visible within the timeout.
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as second
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by, int timeOut) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeOut);

            boolean check = verifyElementVisible(by, timeOut);
            if (check == true) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtTop(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
            WinAppLogUtils.error("Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }

    /**
     * Wait until the given web element is visible.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by) {
        smartWait();
        waitForElementPresent(by);

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            boolean check = isElementVisible(by, 1);
            if (check == true) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtBottom(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to be clickable within the given time (in seconds).
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as seconds
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by, long timeOut) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(),timeOut);
            return wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
            WinAppLogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to be clickable.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            return wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to present within the given time (in seconds).
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as seconds
     * @return an existing WebElement object
     */
    public static WebElement waitForElementPresent(By by, long timeOut) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeOut);
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element to exist. " + by.toString());
            Assert.fail("Timeout waiting for the element to exist. " + by.toString());
        }

        return null;
    }

    /**
     * Wait for the given element to present
     *
     * @param by an element of object type By
     * @return an existing WebElement object
     */
    public static WebElement waitForElementPresent(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            WinAppLogUtils.error("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for an alert to present.
     */
    public static boolean waitForAlertPresent() {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Throwable error) {
            WinAppLogUtils.error("Alert NOT present.");
            Assert.fail("Alert NOT present.");
            return false;
        }
    }

    /**
     * Wait for an alert to present.
     *
     * @param timeOut Timeout waiting for an alert to present.
     */
    public static boolean waitForAlertPresent(int timeOut) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeOut);
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Throwable error) {
            WinAppLogUtils.error("Alert NOT present.");
            Assert.fail("Alert NOT present.");
            return false;
        }
    }

    /**
     * Wait until the given web element has an attribute with the specified name.
     *
     * @param by            an element of object type By
     * @param attributeName The name of the attribute to wait for.
     * @return true/false
     */
    public static boolean waitForElementHasAttribute(By by, String attributeName) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            return wait.until(ExpectedConditions.attributeToBeNotEmpty(waitForElementPresent(by), attributeName));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout for element " + by.toString() + " to exist attribute: " + attributeName);
            Assert.fail("Timeout for element " + by.toString() + " to exist attribute: " + attributeName);
        }
        return false;
    }

    /**
     * Verify if the web element has an attribute with the specified name and value.
     *
     * @param by             an element of object type By
     * @param attributeName  The name of the attribute to wait for.
     * @param attributeValue The value of attribute
     * @return true/false
     */
    public static boolean verifyElementAttributeValue(By by, String attributeName, String attributeValue) {
        smartWait();

        waitForElementVisible(by);
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.attributeToBe(by, attributeName, attributeValue));
            return true;
        } catch (Throwable error) {
            WinAppLogUtils.error("Object: " + by.toString() + ". Not found value: " + attributeValue + " with attribute type: " + attributeName + ". Actual get Attribute value is: " + getAttributeElement(by, attributeName));
            Assert.fail("Object: " + by.toString() + ". Not found value: " + attributeValue + " with attribute type: " + attributeName + ". Actual get Attribute value is: " + getAttributeElement(by, attributeName));
            return false;
        }
    }

    /**
     * Verify if the web element has an attribute with the specified name.
     *
     * @param by            Represent a web element.
     * @param attributeName The name of the attribute to wait for.
     * @param timeOut       System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementHasAttribute(By by, String attributeName, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeOut);
            wait.until(ExpectedConditions.attributeToBeNotEmpty(waitForElementPresent(by), attributeName));
            return true;
        } catch (Throwable error) {
            WinAppLogUtils.error("Not found Attribute " + attributeName + " of element " + by.toString());
            Assert.fail("Not found Attribute " + attributeName + " of element " + by.toString());
            return false;
        }
    }


    /**
     * Wait for a page to load with the default time from the config
     */
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            WinAppLogUtils.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load. (" + WinAppConstants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Wait for a page to load within the given time (in seconds)
     */
    public static void waitForPageLoaded(int timeOut) {
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            WinAppLogUtils.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load. (" + WinAppConstants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Wait for JQuery to finish loading with default time from config
     */
    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();

        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            assert driver != null;
            return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
        };

        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

        //Wait JQuery until it is Ready!
        if (!jqueryReady) {
            WinAppLogUtils.info("JQuery is NOT Ready!");
            try {
                //Wait for jQuery to load
                wait.until(jQueryLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for JQuery load. (" + WinAppConstants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Wait for Angular to finish loading with default time from config
     */
    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        JavascriptExecutor js = (JavascriptExecutor) WinAppDriverManagement.getDriver();
        final String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> {
            assert driver != null;
            return Boolean.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());
        };

        //Get Angular is Ready
        boolean angularReady = Boolean.parseBoolean(js.executeScript(angularReadyScript).toString());

        //Wait ANGULAR until it is Ready!
        if (!angularReady) {
            WinAppLogUtils.warn("Angular is NOT Ready!");
            //Wait for Angular to load
            try {
                //Wait for jQuery to load
                wait.until(angularLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for Angular load. (" + WinAppConstants.WAIT_PAGE_LOADED + "s)");
            }
        }

    }

}