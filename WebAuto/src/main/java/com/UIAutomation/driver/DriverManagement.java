package com.UIAutomation.driver;

import org.openqa.selenium.WebDriver;

public class DriverManagement {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManagement() {
        super();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManagement.driver.set(driver);
    }

    public static void quit() {
        if (DriverManagement.getDriver() != null){
            DriverManagement.getDriver().quit();
        }
    }

}
