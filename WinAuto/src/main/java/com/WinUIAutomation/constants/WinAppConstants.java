package com.WinUIAutomation.constants;

import com.WinUIAutomation.helpers.WinAppHelpers;
import com.WinUIAutomation.helpers.WinAppPropertiesHelpers;
import com.WinUIAutomation.utils.WinAppReportUtils;

import java.io.File;

public class WinAppConstants {

    private WinAppConstants() {
    }

    static {
        WinAppPropertiesHelpers.loadAllFiles();
    }


    public static final String PROJECT_PATH = WinAppHelpers.getCurrentDir();
    public static final String EXCEL_DATA_FILE_PATH = WinAppPropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH");
    public static final String JSON_DATA_FILE_PATH = WinAppPropertiesHelpers.getValue("JSON_DATA_FILE_PATH");
    public static final String LAWSON_CLIENT_PATH =  WinAppPropertiesHelpers.getValue("LAWSON_CLIENT_PATH");
    public static final String LAWSON_DIRECT_LINK=  WinAppPropertiesHelpers.getValue("LAWSON_DIRECT_LINK");

    public static final String WIN_APP_DRIVER_PATH = WinAppPropertiesHelpers.getValue("WIN_APP_DRIVER_PATH");
    public static final String APP_NAME = WinAppPropertiesHelpers.getValue("APP_NAME");
    public static final String PROJECT_NAME = WinAppPropertiesHelpers.getValue("PROJECT_NAME");
    public static final String REPORT_TITLE = WinAppPropertiesHelpers.getValue("REPORT_TITLE");
    public static final String EXTENT_REPORT_NAME = WinAppPropertiesHelpers.getValue("EXTENT_REPORT_NAME");
    public static final String EXTENT_REPORT_FOLDER = WinAppPropertiesHelpers.getValue("EXTENT_REPORT_FOLDER");
    public static final String EXPORT_VIDEO_PATH = WinAppPropertiesHelpers.getValue("EXPORT_VIDEO_PATH");
    public static final String EXPORT_CAPTURE_PATH = WinAppPropertiesHelpers.getValue("EXPORT_CAPTURE_PATH");
    public static final String AUTHOR = WinAppPropertiesHelpers.getValue("AUTHOR");
    public static final String OVERRIDE_REPORTS = WinAppPropertiesHelpers.getValue("OVERRIDE_REPORTS");
    public static final String OPEN_REPORTS_AFTER_EXECUTION = WinAppPropertiesHelpers.getValue("OPEN_REPORTS_AFTER_EXECUTION");
    public static final String SEND_EMAIL_TO_USERS = WinAppPropertiesHelpers.getValue("SEND_EMAIL_TO_USERS");
    public static final String SCREENSHOT_PASSED_TCS = WinAppPropertiesHelpers.getValue("SCREENSHOT_PASSED_TCS");
    public static final String SCREENSHOT_FAILED_TCS = WinAppPropertiesHelpers.getValue("SCREENSHOT_FAILED_TCS");
    public static final String SCREENSHOT_SKIPPED_TCS = WinAppPropertiesHelpers.getValue("SCREENSHOT_SKIPPED_TCS");
    public static final String SCREENSHOT_ALL_STEPS = WinAppPropertiesHelpers.getValue("SCREENSHOT_ALL_STEPS");
    public static final String ZIP_FOLDER = WinAppPropertiesHelpers.getValue("ZIP_FOLDER");
    public static final String ZIP_FOLDER_PATH = WinAppPropertiesHelpers.getValue("ZIP_FOLDER_PATH");
    public static final String ZIP_FOLDER_NAME = WinAppPropertiesHelpers.getValue("ZIP_FOLDER_NAME");
    public static final String VIDEO_RECORD = WinAppPropertiesHelpers.getValue("VIDEO_RECORD");

    public static final String LOCATE = WinAppPropertiesHelpers.getValue("LOCATE");
    public static final String RETRY_TEST_FAIL = WinAppPropertiesHelpers.getValue("RETRY_TEST_FAIL");

    public static final int WAIT_DEFAULT = Integer.parseInt(WinAppPropertiesHelpers.getValue("WAIT_DEFAULT"));
    public static final int WAIT_IMPLICIT = Integer.parseInt(WinAppPropertiesHelpers.getValue("WAIT_IMPLICIT"));
    public static final int WAIT_EXPLICIT = Integer.parseInt(WinAppPropertiesHelpers.getValue("WAIT_EXPLICIT"));
    public static final int WAIT_PAGE_LOADED = Integer.parseInt(WinAppPropertiesHelpers.getValue("WAIT_PAGE_LOADED"));
    public static final int WAIT_SLEEP_STEP = Integer.parseInt(WinAppPropertiesHelpers.getValue("WAIT_SLEEP_STEP"));
    public static final String ACTIVE_PAGE_LOADED = WinAppPropertiesHelpers.getValue("ACTIVE_PAGE_LOADED");

    public static final String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + EXTENT_REPORT_FOLDER;
    public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
    public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + File.separator + EXTENT_REPORT_FILE_NAME;

    //Zip file for Report folder
    public static final String ZIPPED_EXTENT_REPORTS_FOLDER = EXTENT_REPORT_FOLDER + ".zip";

    public static final String YES = "yes";
    public static final String NO = "no";

    public static final String BOLD_START = "<b>";
    public static final String BOLD_END = "</b>";

    /* ICONS - START */

    public static final String ICON_SMILEY_PASS = "<i class='fa fa-smile-o' style='font-size:24px'></i>";
    public static final String ICON_SMILEY_SKIP = "<i class=\"fas fa-frown-open\"></i>";
    public static final String ICON_SMILEY_FAIL = "<i class='fa fa-frown-o' style='font-size:24px'></i>";

    public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
    public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
    public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";

    public static final String ICON_BROWSER_OPERA = "<i class=\"fa fa-opera\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
    public static final String ICON_LAWSON_CLIENT= "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_SAFARI = "<i class=\"fa fa-safari\" aria-hidden=\"true\"></i>";

    public static final String ICON_Navigate_Right = "<i class='fa fa-arrow-circle-right' ></i>";
    public static final String ICON_LAPTOP = "<i class='fa fa-laptop' style='font-size:18px'></i>";
    public static final String ICON_BUG = "<i class='fa fa-bug' ></i>";
    /* style="text-align:center;" */

    public static final String ICON_SOCIAL_GITHUB_PAGE_URL = "";
    public static final String ICON_SOCIAL_LINKEDIN_URL = "";
    public static final String ICON_SOCIAL_GITHUB_URL = "";
    public static final String ICON_SOCIAL_LINKEDIN = "<a href='" + ICON_SOCIAL_LINKEDIN_URL
            + "'><i class='fa fa-linkedin-square' style='font-size:24px'></i></a>";
    public static final String ICON_SOCIAL_GITHUB = "<a href='" + ICON_SOCIAL_GITHUB_URL
            + "'><i class='fa fa-github-square' style='font-size:24px'></i></a>";

    public static final String ICON_CAMERA = "<i class=\"fa fa-camera\" aria-hidden=\"true\"></i>";

    public static final String ICON_BROWSER_PREFIX = "<i class=\"fa fa-";
    public static final String ICON_BROWSER_SUFFIX = "\" aria-hidden=\"true\"></i>";
    /* ICONS - END */

    public static String getExtentReportFilePath() {
        if (EXTENT_REPORT_FILE_PATH.isEmpty()) {
            EXTENT_REPORT_FILE_PATH = WinAppReportUtils.createExtentReportPath();
        }
        return EXTENT_REPORT_FILE_PATH;
    }
}
