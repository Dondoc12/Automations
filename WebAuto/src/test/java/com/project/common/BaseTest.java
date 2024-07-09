package com.project.common;

import com.UIAutomation.driver.DriverManagement;
import com.UIAutomation.driver.Target;
import com.UIAutomation.helpers.PropertiesHelpers;
import com.UIAutomation.keywords.WebUI;
import com.project.listener.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.annotations.*;

@Listeners({TestListener.class})
public class BaseTest {

    @Parameters("BROWSER")
    @BeforeMethod
    public void createDriver(@Optional("chrome") String browser) {
        WebDriver driver = ThreadGuard.protect(new Target().createInstance(browser));
        DriverManagement.setDriver(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        WebUI.stopSoftAssertAll();
        DriverManagement.quit();
    }

    public WebDriver createBrowser(@Optional("chrome") String browser) {
        PropertiesHelpers.loadAllFiles();
        WebDriver driver = ThreadGuard.protect(new Target().createInstance(browser));
        driver.manage().window().maximize();
        DriverManagement.setDriver(driver);
        return DriverManagement.getDriver();
    }

}
