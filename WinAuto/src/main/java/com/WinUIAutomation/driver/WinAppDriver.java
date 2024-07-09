package com.WinUIAutomation.driver;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.WinUIAutomation.constants.WinAppConstants;

import java.net.MalformedURLException;
import java.net.URL;

public enum WinAppDriver {
    LAWSON_CLIENT {
        @Override
        public WindowsDriver<WindowsElement> createDriver() throws MalformedURLException {
            return new WindowsDriver<>(new URL("http://127.0.0.1:4723/"), getOptions());

        }

        @Override
        public DesiredCapabilities getOptions() {
            String lawsonClientPath = "C:/Users/david.le/AppData/Local/Apps/2.0/87TZ2E13.BT2/5PZCH2MX.3J7/http..tion_52eb4aef56c70532_0009.0001_da8e1d62a18c082e/LawsonClient.exe";
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
