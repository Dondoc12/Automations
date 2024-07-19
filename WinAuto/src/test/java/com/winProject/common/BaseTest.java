package com.winProject.common;


import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriver;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.helpers.WinAppPropertiesHelpers;
import com.WinUIAutomation.keywords.WinUI;
import com.winProject.listener.TestListener;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.net.MalformedURLException;

@Listeners({TestListener.class})
public class BaseTest {

    @Parameters("APPNAME")
    @BeforeMethod
    public void createDriver(@Optional("LAWSON_CLIENT") String appName) throws MalformedURLException {
        WindowsDriver<WindowsElement> driver = WinAppDriver.valueOf(appName).createDriver();
        WinAppDriverManagement.setDriver(driver);
        System.out.println("Driver initialized successfully");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        WinUI.stopSoftAssertAll();
        WinAppDriverManagement.quit();
    }

    public WebDriver createBrowser(String appName) throws MalformedURLException {
        WinAppPropertiesHelpers.loadAllFiles();
        WindowsDriver<WindowsElement> driver = WinAppDriver.valueOf(appName).createDriver();
        driver.manage().window().maximize();
        WinAppDriverManagement.setDriver(driver);
        return WinAppDriverManagement.getDriver();
    }

}
