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
        if (WinAppConstants.APP_NAME == null || WinAppConstants.APP_NAME.isEmpty()) {
            throw new WinAppTargetNotValidException("Browser name is not valid.");
        }
        try {
            return winAppDriver = WinAppDriver.valueOf(WinAppConstants.APP_NAME.toUpperCase()).createDriver();
        } catch (IllegalArgumentException e) {
            throw new WinAppTargetNotValidException("Browser name is not valid.");
        }
    }

    public WindowsDriver<WindowsElement> createInstance(String app) throws MalformedURLException {
        String appName = (WinAppConstants.APP_NAME != null && !WinAppConstants.APP_NAME.isEmpty()) ? WinAppConstants.APP_NAME : app;
        try {
            return winAppDriver = WinAppDriver.valueOf(appName.toUpperCase()).createDriver();
        } catch (IllegalArgumentException e) {
            throw new WinAppTargetNotValidException("Browser name is not valid.");
        }
    }
}
