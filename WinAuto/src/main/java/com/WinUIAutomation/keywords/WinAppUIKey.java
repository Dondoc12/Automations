package com.WinUIAutomation.keywords;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.helpers.WinAppHelpers;
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
    public static void performDoubleClick(By by) {
        Actions action = new Actions(WinAppDriverManagement.getDriver());
        action.moveToElement(WinAppDriverManagement.getDriver().findElement(by)).doubleClick().perform();
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Double Click on "+ by);
        }

    }
    public static void performDoubleClick(WindowsElement element) {
        Actions action = new Actions(WinAppDriverManagement.getDriver());
        action.moveToElement(element).doubleClick().perform();
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Double Click on "+ element);
        }
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
        }
    }
}
