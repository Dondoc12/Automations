package com.WinUIAutomation.utils;

import com.WinUIAutomation.enums.WinAppDriverValue;
import com.WinUIAutomation.constants.WinAppConstants;

public final class WinAppIconUtils {

    private WinAppIconUtils() {
        super();
    }

    public static String getBrowserIcon() {
        if (WinAppBrowserInfoUtils.getAppInfo().contains(WinAppDriverValue.LAWSON_CLIENT.toString())) {
            return WinAppConstants.ICON_LAWSON_CLIENT;
        } else if (WinAppBrowserInfoUtils.getAppInfo().contains(WinAppDriverValue.EDGE.toString())) {
            return WinAppConstants.ICON_BROWSER_EDGE;
        } else if (WinAppBrowserInfoUtils.getAppInfo().contains(WinAppDriverValue.FIREFOX.toString())) {
            return WinAppConstants.ICON_BROWSER_FIREFOX;
        } else {
            return WinAppBrowserInfoUtils.getAppInfo();
        }
    }

    public static String getOSIcon() {

        String operationSystem = WinAppBrowserInfoUtils.getOSInfo().toLowerCase();
        if (operationSystem.contains("win")) {
            return WinAppConstants.ICON_OS_WINDOWS;
        } else if (operationSystem.contains("nix") || operationSystem.contains("nux") || operationSystem.contains("aix")) {
            return WinAppConstants.ICON_OS_LINUX;
        } else if (operationSystem.contains("mac")) {
            return WinAppConstants.ICON_OS_MAC;
        }
        return operationSystem;
    }
}

