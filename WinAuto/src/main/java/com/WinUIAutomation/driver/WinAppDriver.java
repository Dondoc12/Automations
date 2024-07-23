package com.WinUIAutomation.driver;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.helpers.WinAppHelpers;
import com.WinUIAutomation.keywords.WinUI;
import com.WinUIAutomation.utils.WinAppLogUtils;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public enum WinAppDriver {
    LAWSON_CLIENT {
        @Override
        public WindowsDriver<WindowsElement> createDriver() throws MalformedURLException {
            openLawSonApp();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            WinAppLogUtils.info("Start create driver");
            return new WindowsDriver<>(new URL("http://127.0.0.1:4723/"), getOptions());
        }

        @Override
        public DesiredCapabilities getOptions() {
            String lawsonClientPath = WinAppHelpers.getCurrentDir() + WinAppConstants.LAWSON_CLIENT_PATH;
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
    private static void openLawSonApp() {
        String lawSonAppDirect = WinAppHelpers.getCurrentDir() + WinAppConstants.LAWSON_DIRECT_LINK;

        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", lawSonAppDirect);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            process.waitFor();
            WinAppLogUtils.info("Application launched successfully");
        } catch (IOException e) {
            e.printStackTrace();
            WinAppLogUtils.info("Failed to launch the application: IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
            WinAppLogUtils.info("Failed to launch the application: InterruptedException");
        }
    }
}
