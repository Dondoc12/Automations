package com.UIAutomation.driver;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.enums.TargetValue;
import com.UIAutomation.exceptions.TargetNotValidException;
import com.UIAutomation.utils.LogUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Target {

    public WebDriver createInstance() {
        TargetValue target = TargetValue.valueOf(Constants.TARGET.toUpperCase());
        WebDriver webdriver;

        switch (target) {
            case LOCAL:
                //Create new driver from Enum setup in BrowserFactory class
                webdriver = Browser.valueOf(Constants.BROWSER.toUpperCase()).createDriver();
                break;
            case REMOTE:
                //Create new driver on Cloud (Selenium Grid, Docker) from method below
                webdriver = createRemoteInstance(Browser.valueOf(Constants.BROWSER.toUpperCase()).getOptions());
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }
        return webdriver;
    }

    public WebDriver createInstance(String browser) {
        TargetValue target = TargetValue.valueOf(Constants.TARGET.toUpperCase());
        WebDriver webdriver;

        String browserName = (Constants.BROWSER != null && !Constants.BROWSER.isEmpty()) ? Constants.BROWSER
                : browser;

        switch (target) {
            case LOCAL:
                //Create new driver from Enum setup in BrowserFactory class
                webdriver = Browser.valueOf(browserName.toUpperCase()).createDriver();
                break;
            case REMOTE:
                //Create new driver on Cloud (Selenium Grid, Docker) from method below
                webdriver = createRemoteInstance(Browser.valueOf(browserName.toUpperCase()).getOptions());
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }
        return webdriver;
    }

    private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            String gridURL = String.format("http://%s:%s", Constants.REMOTE_URL, Constants.REMOTE_PORT);
            LogUtils.info("Remote URL: " + gridURL);
            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
        } catch (java.net.MalformedURLException e) {
            LogUtils.error("Grid URL is invalid or Grid Port is not available");
            LogUtils.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            LogUtils.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

}
