package com.WinUIAutomation.keywords;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.enums.WinAppFailureHandling;
import com.WinUIAutomation.report.WinAppExtentReportManagement;
import com.WinUIAutomation.report.WinAppExtentTestManagement;
import com.WinUIAutomation.utils.WinAppDateUtils;
import com.WinUIAutomation.utils.WinAppLogUtils;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    // Wait Control
    /**
     * Wait for the given element to be clickable.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            return wait.until(ExpectedConditions.elementToBeClickable(getWindowElement(by)));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }
    /**
     * Wait for the given element to be clickable.
     *
     * @param element an element of object
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element ready to click. " + element.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + element.toString());
        }
        return null;
    }
    /**
     * Wait for the given element to be clickable within the given time (in seconds).
     * Wait for the given element to be clickable within the given time (in seconds).
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as seconds
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by, long timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(),timeOut);
            return wait.until(ExpectedConditions.elementToBeClickable(getWindowElement(by)));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
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
     * Wait for the given element to present within the given time (in seconds).
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as seconds
     * @return an existing WebElement object
     */
    public static WebElement waitForElementPresent(By by, long timeOut) {
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
     * Wait until the given web element is visible.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to be visible
     */
    public static void waitForElementInvisible(By by) {
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        WinAppLogUtils.info("Wait " + by.toString()+ " Invisible") ;
    }
    /**
     * Wait until the given web element is visible.
     *
     * @param element an element of object
     * @return a WebElement object ready to be visible
     */
    public static void waitForElementVisible(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element Visible. " + element.toString());
            Assert.fail("Timeout waiting for the element Visible. " + element.toString());
        }

    }
    public static void checkForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            Actions actions = new Actions(WinAppDriverManagement.getDriver());
            actions.moveToElement(getWindowElement(by)).perform();
            wait.until(ExpectedConditions.visibilityOf(getWindowElement(by)));
        } catch (Throwable error) {
            WinAppLogUtils.error("Timeout waiting for the element Visible. " + getWindowElement(by).toString());
            Assert.fail("Timeout waiting for the element Visible. " + getWindowElement(by).toString());
        }

    }

    /**
     * Wait until the given web element is visible.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by) {
        waitForElementPresent(by);
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
            boolean check = isElementVisible(by, 1);
            if (check) {
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
     * Wait until the given web element is visible within the timeout.
     *
     * @param by      an element of object type By
     * @param timeOut maximum timeout as second
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), timeOut);

            boolean check = verifyElementVisible(by, timeOut);
            if (check) {
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
    // Scroll/Move control
    /**
     * Move to an offset location.
     *
     * @param X x offset
     * @param Y y offset
     * @return true/false
     */
    public static boolean moveToOffset(int X, int Y) {
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
     * Scroll to an offset location
     *
     * @param x x offset
     * @param y y offset
     */
    public static void scrollToPosition(int x, int y) {
        WindowsDriver<WindowsElement> driver = WinAppDriverManagement.getDriver();
        new Actions(driver)
                .moveByOffset(x, y)
                .perform();
        WinAppLogUtils.info("Scroll to position X = " + x + " ; Y = " + y);
    }
    /**
     * Scroll an element into the visible area of the application window. (at TOP)
     *
     * @param by Represent a UI element as the By object
     */
    public static void scrollToElementAtTop(By by) {
        WebElement element = WinAppDriverManagement.getDriver().findElement(by);

        // Perform the scroll action
        new Actions(WinAppDriverManagement.getDriver())
                .moveToElement(element)
                .perform();

        WinAppLogUtils.info("Scroll to element " + by);
    }
    /**
     * Scroll an element into the visible area of the application window. (at BOTTOM)
     *
     * @param by Represent a UI element as the By object
     */
    public static void scrollToElementAtBottom(By by) {
        WebElement element = WinAppDriverManagement.getDriver().findElement(by);
        Dimension elementSize = element.getSize();
        Dimension windowSize = WinAppDriverManagement.getDriver().manage().window().getSize();
        int scrollAmount = elementSize.getHeight() - windowSize.getHeight();
        new Actions(WinAppDriverManagement.getDriver())
                .moveToElement(element, 0, scrollAmount)
                .perform();
        WinAppLogUtils.info("Scroll to element " + by);
    }
    public static void scrollToElementAtBottom(WebElement element) {
        Dimension elementSize = element.getSize();
        Dimension windowSize = WinAppDriverManagement.getDriver().manage().window().getSize();
        int scrollAmount = elementSize.getHeight() - windowSize.getHeight();
        new Actions(WinAppDriverManagement.getDriver())
                .moveToElement(element, 0, scrollAmount)
                .perform();
        WinAppLogUtils.info("Scroll to element " + element.toString());
    }
    /* Element Control */
    public static void performDoubleClick(WebElement element) {
        Actions action = new Actions(WinAppDriverManagement.getDriver());
        action.doubleClick(element).perform();
        System.out.println("Double click performed on element: " + element);
    }
    /**
     * Click on the specified element.
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by) {
        waitForElementClickable(by);

        Objects.requireNonNull(getWindowElement(by)).click();
        WinAppLogUtils.info("Clicked on the element " + by.toString());
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clicked on the element " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }
    /**
     * Click on the specified element.
     *
     * @param element an element of object
     */
    public static void clickElement(WebElement element) {
        waitForElementClickable(element);
        Actions actions = new Actions(WinAppDriverManagement.getDriver());
        actions.moveToElement(element).perform();
        element.click();
        WinAppLogUtils.info("Clicked on the element " + element.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clicked on the element " + element.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }
    /**
     * Click on element with timeout
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by, int timeout) {
        waitForElementClickable(by, timeout);
        Objects.requireNonNull(getWindowElement(by)).click();
        WinAppLogUtils.info("Clicked on the element " + by.toString());

        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clicked on the element " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }
    /**
     * Click on the link on website with text
     *
     * @param linkText is the visible text of a link
     */
    public static void clickLinkText(String linkText) {
        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        Objects.requireNonNull(getWindowElement(By.linkText(linkText))).click();

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
     * Set the value of an input field
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     */
    public static void setText(By by, String value) {
        waitForElementVisible(by);
        Objects.requireNonNull(getWindowElement(by)).sendKeys(value);
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
        waitForElementVisible(by);
        Objects.requireNonNull(getWindowElement(by)).sendKeys(value, keys);
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
        waitForElementVisible(by);
        Objects.requireNonNull(getWindowElement(by)).sendKeys( keys);
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
        waitForElementVisible(by);
        Objects.requireNonNull(getWindowElement(by)).clear();
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
        Objects.requireNonNull(getWindowElement(by)).clear();
        Objects.requireNonNull(getWindowElement(by)).sendKeys(value);
        WinAppLogUtils.info("Clear and Fill " + value + " on " + by.toString());
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Clear and Fill " + value + " on " + by.toString());
        }
        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + WinAppDateUtils.getCurrentDateTime());
    }
    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean hoverOnElement(By by) {
        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveToElement(getWindowElement(by)).perform();
            WinAppLogUtils.info("Hover on element " + by);
            return true;
        } catch (Exception e) {
            return false;
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
     * Move to the given element.
     *
     * @param toElement Represent a web element as the By object
     * @return true/false
     */
    public static boolean moveToElement(By toElement) {
        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveToElement(getWindowElement(toElement)).release(getWindowElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
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
        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.moveToElement(getWindowElement(by)).perform();
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
        waitForElementVisible(fromElement);
        try {
            Actions action = new Actions(WinAppDriverManagement.getDriver());
            action.dragAndDrop(getWindowElement(fromElement), getWindowElement(toElement)).perform();
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

        try {
            Robot robot = new Robot();
            robot.mouseMove(0, 0);
            int X1 = Objects.requireNonNull(getWindowElement(fromElement)).getLocation().getX() + (Objects.requireNonNull(getWindowElement(fromElement)).getSize().getWidth() / 2);
            int Y1 = Objects.requireNonNull(getWindowElement(fromElement)).getLocation().getY() + (Objects.requireNonNull(getWindowElement(fromElement)).getSize().getHeight() / 2);
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
     * Get text of an element
     *
     * @param by an element of object type By
     * @return text of a element
     */
    public static String getTextElement(By by) {
        WinAppLogUtils.info("Get text on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get text on element " + by);
            WinAppExtentReportManagement.pass("The Text is: " + Objects.requireNonNull(waitForElementVisible(by)).getText().trim());
        }
        return Objects.requireNonNull(waitForElementVisible(by)).getText().trim();
    }
    /**
     * Get the value from the element's attribute
     *
     * @param by            an element of object type By
     * @param attributeName attribute name
     * @return element's attribute value
     */
    public static String getAttributeElement(By by, String attributeName) {
        WinAppLogUtils.info("Get attributeName on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get attributeName on element " + by);
            WinAppExtentReportManagement.pass("The attributeName is: " + Objects.requireNonNull(waitForElementVisible(by)).getAttribute(attributeName));
        }
        return Objects.requireNonNull(waitForElementVisible(by)).getAttribute(attributeName);
    }
    /**
     * Get CSS value of an element
     *
     * @param by      Represent a web element as the By object
     * @param cssName is CSS attribute name
     * @return value of CSS attribute
     */
    public static String getCssValueElement(By by, String cssName) {
        WinAppLogUtils.info("Get attributeName on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get getCssValue on element " + by);
            WinAppExtentReportManagement.pass("The getCssValue is: " + Objects.requireNonNull(waitForElementVisible(by)).getCssValue(cssName));
        }
        return Objects.requireNonNull(waitForElementVisible(by)).getCssValue(cssName);
    }
    /**
     * Convert the By object to the WebElement
     *
     * @param by is an element of type By
     * @return Returns a WebElement object
     */
    public static WebElement getWindowElement( By by) {
        for (String windowHandle : WinAppDriverManagement.getDriver().getWindowHandles()) {
            WinAppDriverManagement.getDriver().switchTo().window(windowHandle);
            try {
                WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), 20);
                WebElement element = wait.until(d -> WinAppDriverManagement.getDriver().findElement(by));

                if (element != null) {
                    return element;
                }
            } catch (Exception e) {
                WinAppLogUtils.info("Get Element " + by + " Error");
            }
        }
        return null;
    }
    /**
     * Find multiple elements with the locator By object
     *
     * @param by is an element of type By
     * @return Returns a List of WebElement objects
     */
    public static List<WebElement> getWindowElements(By by) {
        for (String windowHandle : WinAppDriverManagement.getDriver().getWindowHandles()) {
            WinAppDriverManagement.getDriver().switchTo().window(windowHandle);
            try {
                WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
                Object element = wait.until(d -> WinAppDriverManagement.getDriver().findElements(by));
                if (element != null) {
                    return (List<WebElement>) element;
                }
            } catch (Exception e) {
                WinAppLogUtils.info("Get Elements " + by + " Error");
            }
        }
        return null;
    }
    /**
     * Get size of specified element
     *
     * @param by Represent a web element as the By object
     * @return Dimension
     */
    public static Dimension getSizeElement(By by) {
        WinAppLogUtils.info("Get element size on element " + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get element size on " + by);
            WinAppExtentReportManagement.pass("The element size is: " + Objects.requireNonNull(waitForElementVisible(by)).getSize());
        }
        return Objects.requireNonNull(waitForElementVisible(by)).getSize();
    }
    /**
     * Get location of specified element
     *
     * @param by Represent a web element as the By object
     * @return Point
     */
    public static Point getLocationElement(By by) {
        WinAppLogUtils.info("Get Element Location on element" + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get Element Location on " + by);
            WinAppExtentReportManagement.pass("The element Element Location is: " + Objects.requireNonNull(waitForElementVisible(by)).getLocation());
        }
        return Objects.requireNonNull(waitForElementVisible(by)).getLocation();
    }
    /**
     * Select the options with the given label (displayed text).
     *
     * @param by   Represent a web element as the By object
     * @param text the specified text of option
     */
    public static void selectOptionByText(By by, String text) {
        waitForElementVisible(by);
        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
        select.selectByVisibleText(text);
        WinAppLogUtils.info("Select Option " + by + "by text " + text);
    }
    /**
     * Get tag name (HTML tag) of specified element
     *
     * @param by Represent a web element as the By object
     * @return Tag name as String
     */
    public static String getTagNameElement(By by) {
        WinAppLogUtils.info("Get Element TagName on element" + by);
        if (WinAppExtentTestManagement.getExtentTest() != null) {
            WinAppExtentReportManagement.pass("Get Element TagName on " + by);
            WinAppExtentReportManagement.pass("The element Element TagName is: " + Objects.requireNonNull(waitForElementVisible(by)).getTagName());
        }
        return Objects.requireNonNull(waitForElementVisible(by)).getTagName();
    }
    /**
     * Select the options with the given value.
     *
     * @param by    Represent a web element as the By object
     * @param value the specified value of option
     */
    public static void selectOptionByValue(By by, String value) {
        waitForElementVisible(by);
        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
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
        waitForElementVisible(by);
        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
        select.selectByIndex(index);
        WinAppLogUtils.info("Select Option " + by + "by index " + index);
    }
    /**
     * Get list text of specified elements
     *
     * @param by Represent a web element as the By object
     * @return Text list of specified elements
     */
    public static List<String> getListElementsText(By by) {
        waitForElementVisible(by);

        WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(), WinAppConstants.WAIT_EXPLICIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        List<WebElement> listElement = getWindowElements(by);

        List<String> listText = new ArrayList<>();
        for (WebElement e : Objects.requireNonNull(listElement)) {
            listText.add(e.getText());
        }
        return listText;
    }
    /**
     * Get total number of options the given web element has. (select option)
     *
     * @param objectListItem Represent a web element as the By object
     * @return total number of options
     */
    public static int getOptionDynamicTotal(By objectListItem) {
        waitForElementVisible(objectListItem);
        WinAppLogUtils.info("Get total of Option Dynamic with list element. " + objectListItem);
        try {
            List<WebElement> elements = getWindowElements(objectListItem);
            return Objects.requireNonNull(elements).size();
        } catch (Exception e) {
            WinAppLogUtils.info(e.getMessage());
            e.getMessage();
        }
        return 0;
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
        WebElement windowsElement = WinAppDriverManagement.getDriver().findElement(by);
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

            File source = Objects.requireNonNull(getWindowElement(by)).getScreenshotAs(OutputType.FILE);
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
        File scrFile = Objects.requireNonNull(getWindowElement(by)).getScreenshotAs(OutputType.FILE);
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
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
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
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
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
        for (File listOfFiles : Objects.requireNonNull(file.listFiles())) {
            if (listOfFiles.isFile()) {
                i++;
            }
        }
        return i;
    }

    /* Verify Value */
    /**
     * Verify if the options at the given text are selected.
     *
     * @param by   Represent a web element as the By object
     * @param text the specified options text
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByText(By by, String text) {
        waitForElementVisible(by);

        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
        WinAppLogUtils.info("Verify Option Selected by text: " + select.getFirstSelectedOption().getText());

        if (select.getFirstSelectedOption().getText().equals(text)) {
            return true;
        } else {
            Assert.assertEquals(select.getFirstSelectedOption().getText(), text, "The option NOT selected. " + by);
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
        waitForElementVisible(by);

        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
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
     * Verify if a web element is present (findElements.size > 0).
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementExists(By by) {
        waitForElementVisible(by);

        boolean res;
        List<WebElement> elementList = getWindowElements(by);
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
        if (result) {
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
     * Verify text of an element. (equals)
     *
     * @param by          Represent a web element as the By object
     * @param text        Text of the element to verify.
     * @param flowControl Specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextEquals(By by, String text, WinAppFailureHandling flowControl) {
        waitForElementVisible(by);
        boolean result = getTextElement(by).trim().equals(text.trim());
        if (result) {
            WinAppLogUtils.info("Verify text of an element [Equals]: " + result);
        } else {
            WinAppLogUtils.warn("Verify text of an element [Equals]: " + result);
        }

        if (flowControl.equals(WinAppFailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
        }
        if (flowControl.equals(WinAppFailureHandling.CONTINUE_ON_FAILURE)) {
            softAssert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
            if (!result) {
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
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().equals(text.trim());

        if (result) {
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
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().contains(text.trim());

        if (result) {
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
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().contains(text.trim());

        if (result) {
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
     * Verify if two object are equal.
     *
     * @param value1  The object one
     * @param value2  The object two
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyEquals(Object value1, Object value2, String message) {
        boolean result = value1.equals(value2);
        if (result) {
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
     * Verify if the given element is clickable. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementClickable(By by, int timeout, String message) {
        waitForElementVisible(by);

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
            Assert.fail(message + " " + e.getMessage());
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
        waitForElementClickable(by);

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
     * Verify if the given element is clickable.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementClickable(By by) {
        waitForElementClickable(by);

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
     * Verify if the options at the given value are selected.
     *
     * @param by    Represent a web element as the By object
     * @param value the specified options value
     * @return true if options given selected, else is false
     */
    public static boolean verifySelectedByValue(By by, String value) {
        waitForElementVisible(by);

        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
        WinAppLogUtils.info("Verify Option Selected by value: " + select.getFirstSelectedOption().getAttribute("value"));
        if (select.getFirstSelectedOption().getAttribute("value").equals(value)) {
            return true;
        } else {
            Assert.assertEquals(select.getFirstSelectedOption().getAttribute("value"), value, "The option NOT selected. " + by);
            return false;
        }
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
        if (result) {
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
        if (result) {
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
        if (condition) {
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
        if (condition) {
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
        waitForElementVisible(by);

        return getTextElement(by).trim().equals(text.trim());
    }
    /**
     * Verify All Options contains the specified text (select option)
     *
     * @param by   Represent a web element as the By object
     * @param text the specified text
     * @return true if all option contains the specified text
     */
    public static boolean verifyOptionDynamicExist(By by, String text) {
        waitForElementVisible(by);

        try {
            List<WebElement> elements = getWindowElements(by);

            for (WebElement element : elements) {
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
     * Verify if the given web element is checked.
     *
     * @param by Represent a web element as the By object
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by) {
        waitForElementVisible(by);

        boolean checked = Objects.requireNonNull(getWindowElement(by)).isSelected();
        if (checked) {
            return true;
        } else {
            Assert.assertTrue(false, "The element NOT checked.");
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
        waitForElementPresent(by);
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
     * Verify if the given web element does present on DOM.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, String message) {
        waitForElementPresent(by);

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
        waitForElementPresent(by);

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
     * Verify if the given web element does present on DOM. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, int timeout) {
        waitForElementPresent(by);

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
     * Verify if the given web element is checked.
     *
     * @param by      Represent a web element as the By object
     * @param message the custom message if false
     * @return true if the element is checked, otherwise false.
     */
    public static boolean verifyElementChecked(By by, String message) {
        waitForElementVisible(by);

        boolean checked = Objects.requireNonNull(getWindowElement(by)).isSelected();

        if (checked) {
            return true;
        } else {
            Assert.assertTrue(false, message);
            return false;
        }
    }
    /**
     * Verify the number of options equals the specified total
     *
     * @param by    Represent a web element as the By object
     * @param total the specified options total
     */
    public static void verifyOptionTotal(By by, int total) {
        waitForElementPresent(by);
        Select select = new Select(Objects.requireNonNull(getWindowElement(by)));
        WinAppLogUtils.info("Verify Option Total equals: " + total);
        Assert.assertEquals(total, select.getOptions().size());
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
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
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
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
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
            if (exist) {
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
            if (exist) {
                i = timeoutSeconds;
                return check = true;
            }
            sleep(1);
            i++;
        }
        return check;
    }
    /**
     * Verify if the given web element does NOT present on the DOM.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementNotPresent(By by) {
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
     * Verify if the given web element is visible.
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean verifyElementVisible(By by) {
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
}

