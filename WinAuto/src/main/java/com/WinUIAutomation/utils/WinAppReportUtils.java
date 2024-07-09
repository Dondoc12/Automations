package com.WinUIAutomation.utils;

import com.WinUIAutomation.exceptions.WinAppWinAppInvalidPathForExtentReportFileWinAppException;
import com.WinUIAutomation.exceptions.WinAppException;
import com.WinUIAutomation.constants.WinAppConstants;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WinAppReportUtils {

    private WinAppReportUtils() {

    }

    public static String createExtentReportPath() {
        String link = "";
        if (WinAppConstants.OVERRIDE_REPORTS.trim().equals(WinAppConstants.NO)) {
            System.out.println("OVERRIDE_REPORTS = " + WinAppConstants.OVERRIDE_REPORTS);
            link = WinAppConstants.EXTENT_REPORT_FOLDER_PATH + File.separator + WinAppDateUtils.getCurrentDate() + "_"
                    + WinAppConstants.EXTENT_REPORT_FILE_NAME;
            System.out.println("Created link report: " + link);
            return link;
        } else {
            System.out.println("OVERRIDE_REPORTS = " + WinAppConstants.OVERRIDE_REPORTS);
            link = WinAppConstants.EXTENT_REPORT_FOLDER_PATH + File.separator + WinAppConstants.EXTENT_REPORT_FILE_NAME;
            System.out.println("Created link report: " + link);
            return link;
        }
    }

    public static void openReports(String linkReport) {
        if (WinAppConstants.OPEN_REPORTS_AFTER_EXECUTION.trim().equalsIgnoreCase(WinAppConstants.YES)) {
            try {
                Desktop.getDesktop().browse(new File(linkReport).toURI());
            } catch (FileNotFoundException fileNotFoundException) {
                throw new WinAppWinAppInvalidPathForExtentReportFileWinAppException("Extent Report file you are trying to reach is not found", fileNotFoundException.fillInStackTrace());
            } catch (IOException ioException) {
                throw new WinAppException("Extent Report file you are trying to reach got IOException while reading the file", ioException.fillInStackTrace());
            }
        }
    }
}

