package com.WinUIAutomation.driver;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.utils.WinAppLogUtils;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import java.io.IOException;

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
    public static void killLawSonApp(){
        String processName = "LawsonClient.exe";

        try {
            Process process = Runtime.getRuntime().exec("taskkill /IM " + processName + " /F");
            process.waitFor();  // Chờ đợi tiến trình hoàn thành

            WinAppLogUtils.info(processName + " is closed.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            WinAppLogUtils.info(processName + " closed have error");
        }
    }
}
