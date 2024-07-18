package com.WinUIAutomation.driver;

import com.WinUIAutomation.constants.WinAppConstants;
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
            return new WindowsDriver<>(new URL("http://127.0.0.1:4723/"), getOptions());
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
    private static void openLawSonApp() {
        String lawSonAppDirect = WinAppConstants.LAWSON_DIRECT_LINK;

        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", lawSonAppDirect);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            process.waitFor(); // Đợi cho đến khi quá trình hoàn thành
            System.out.println("Application launched successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to launch the application: IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Failed to launch the application: InterruptedException");
        }
    }
}
