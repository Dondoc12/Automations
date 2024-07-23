package com.winProject.common;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriver;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.helpers.WinAppHelpers;
import com.WinUIAutomation.helpers.WinAppPropertiesHelpers;
import com.WinUIAutomation.keywords.WinUI;
import com.WinUIAutomation.utils.WinAppLogUtils;
import com.winProject.listener.TestListener;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;

@Listeners({TestListener.class})
public class BaseTest {

    @Parameters("APPNAME")
    @BeforeMethod
    public void createDriver(@Optional("LAWSON_CLIENT") String appName) throws MalformedURLException {
        WindowsDriver<WindowsElement> driver = WinAppDriver.valueOf(appName).createDriver();
        WinAppDriverManagement.setDriver(driver);
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
//    public static void killLawSonApp(){
//        String processName = "LawsonClient.exe";
//
//        try {
//            Process process = Runtime.getRuntime().exec("taskkill /IM " + processName + " /F");
//            process.waitFor();
//
//            WinAppLogUtils.info(processName + " is closed.");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            WinAppLogUtils.info(processName + " closed have error");
//        }
//    }
//    public static void killWinAppDriver(){
//        String processName = "WinAppDriver.exe";
//
//        try {
//            Process process = Runtime.getRuntime().exec("taskkill /IM " + processName + " /F");
//            process.waitFor();
//            WinAppLogUtils.info(processName + " is closed.");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            WinAppLogUtils.info(processName + " closed have error");
//        }
//    }
//    private static void openWinAppDriver() {
//        String winAppDriverPath = WinAppHelpers.getCurrentDir() +  "WindowsApplicationDriver/WinAppDriver.exe";
//        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", "\"\"", winAppDriverPath);
//        processBuilder.redirectErrorStream(true);
//
//        try {
//            Process process = processBuilder.start();
//            process.waitFor();
//            WinAppLogUtils.info("WinAppDriver is started");
//        } catch (IOException e) {
//            e.printStackTrace();
//            WinAppLogUtils.info("Can not start IOException");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            WinAppLogUtils.info("Can not start InterruptedException");
//        }
//    }
//    private static void openLawSonApp() {
//        String lawSonAppDirect = WinAppHelpers.getCurrentDir() + WinAppConstants.LAWSON_DIRECT_LINK;
//
//        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", lawSonAppDirect);
//        processBuilder.redirectErrorStream(true);
//
//        try {
//            Process process = processBuilder.start();
//            process.waitFor();
//            WinAppLogUtils.info("Open lawson App successfully");
//        } catch (IOException e) {
//            e.printStackTrace();
//            WinAppLogUtils.info("Failed to launch the application: IOException");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            WinAppLogUtils.info("Failed to launch the application: InterruptedException");
//        }
//    }


}
