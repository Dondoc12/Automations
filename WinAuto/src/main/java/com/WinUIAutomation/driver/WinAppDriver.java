package com.WinUIAutomation.driver;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.utils.WinAppLogUtils;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public enum WinAppDriver {
    LAWSON_CLIENT {
        @Override
        public WindowsDriver<WindowsElement> createDriver() throws MalformedURLException {
            try {
                WinAppLogUtils.info("Start create driver");
                return new WindowsDriver<>(new URL("http://127.0.0.1:4723/"), getOptions());
            }
            catch (Exception e){
                WinAppLogUtils.info("Create Driver Fail");
            }
            return null;
        }

        @Override
        public DesiredCapabilities getOptions() {
            String lawsonClientPath = WinAppConstants.LAWSON_CLIENT_PATH;
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            capabilities.setCapability("app", lawsonClientPath);
            capabilities.setCapability("ms:waitForAppLaunch", "5");
            return capabilities;
        }
    };
    public abstract WindowsDriver<WindowsElement> createDriver() throws MalformedURLException;

    public abstract DesiredCapabilities getOptions();
}
