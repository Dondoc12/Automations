package com.WinUIAutomation.utils;

import com.WinUIAutomation.constants.WinAppConstants;
import org.testng.Reporter;

public class WinAppBrowserInfoUtils {
    private WinAppBrowserInfoUtils() {
        super();
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getAppInfo() {
        String app = "";
        if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("APP_NAME") == null) {
            app = WinAppConstants.APP_NAME.toUpperCase();
        } else {
            app = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("APP_NAME").trim().toUpperCase();
        }
        return app;
    }

    public static String getOSInfo() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }
}
