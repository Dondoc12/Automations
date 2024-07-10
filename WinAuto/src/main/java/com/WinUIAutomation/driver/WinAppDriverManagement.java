package com.WinUIAutomation.driver;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.WebDriver;

public class WinAppDriverManagement {

    private static final ThreadLocal<WindowsDriver<WindowsElement>> driver = new ThreadLocal<>();

    private WinAppDriverManagement() {
        super();
    }

    public static WindowsDriver<WindowsElement> getDriver() {
        return driver.get();
    }

    public static void setDriver(WindowsDriver<WindowsElement> driver) {
        WinAppDriverManagement.driver.set(driver);
    }

    public static void quit() {
        if (WinAppDriverManagement.getDriver() != null){
            WinAppDriverManagement.getDriver().quit();
        }
    }

}
