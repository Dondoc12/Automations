// WinAppTarget.java
package com.WinUIAutomation.driver;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.exceptions.WinAppTargetNotValidException;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class WinAppTarget {
    WindowsDriver<WindowsElement> winAppDriver;

    public WindowsDriver<WindowsElement> createInstance() throws MalformedURLException {
        if (WinAppConstants.BROWSER == null || WinAppConstants.BROWSER.isEmpty()) {
            throw new WinAppTargetNotValidException("Browser name is not valid.");
        }
        try {
            return winAppDriver = WinAppDriver.valueOf(WinAppConstants.BROWSER.toUpperCase()).createDriver();
        } catch (IllegalArgumentException e) {
            throw new WinAppTargetNotValidException("Browser name is not valid.");
        }
    }

    public WebDriver createInstance(String browser) throws MalformedURLException {
        String browserName = (WinAppConstants.BROWSER != null && !WinAppConstants.BROWSER.isEmpty()) ? WinAppConstants.BROWSER : browser;
        try {
            return winAppDriver = WinAppDriver.valueOf(browserName.toUpperCase()).createDriver();
        } catch (IllegalArgumentException e) {
            throw new WinAppTargetNotValidException("Browser name is not valid.");
        }
    }
}
