package com.WinUIAutomation.keywords;

import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.report.WinAppExtentReportManagement;
import com.WinUIAutomation.report.WinAppExtentTestManagement;
import com.WinUIAutomation.utils.WinAppBrowserInfoUtils;
import com.WinUIAutomation.utils.WinAppLogUtils;
import com.beust.jcommander.internal.Nullable;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;


public class WinAppUIKey {
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
     * Convert the By object to the WebElement
     *
     * @param by is an element of type By
     * @return Returns a WindowElement object
     */
    public static WebElement getWindowElement(By by) {
        return WinAppDriverManagement.getDriver().findElement(by);
    }
    /**
     * Find multiple elements with the locator By object
     *
     * @param by is an element of type By
     * @return Returns a List of WindowElement objects
     */
    public static List<WindowsElement> getWindowElements(By by) {
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
     * Uploading files with a click shows a form to select local files on your computer
     *
     * @param by       is an element of type By
     * @param filePath the absolute path to the file on your computer
     */
    public static void uploadFileWithLocalForm(By by, String filePath) {
       // smartWait();

        Actions action = new Actions(WinAppDriverManagement.getDriver());
        //Click to open form upload
        action.moveToElement(getWindowElement(by)).click().perform();
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
    public static void performDoubleClick(WindowsElement element) {
        Actions action = new Actions(WinAppDriverManagement.getDriver());
        action.moveToElement(element).doubleClick().perform();
        System.out.println("Double click performed on element: " + element);
    }
}
