package com.WinUIAutomation.exceptions;

public class WinAppWinAppInvalidPathForExcelWinAppException extends WinAppInvalidPathForFilesWinAppException {

    /**
     * Pass the message that needs to be appended to the stacktrace
     * @param message Details about the exception or custom message
     */
    public WinAppWinAppInvalidPathForExcelWinAppException(String message) {
        super(message);
    }

    /**
     *
     * @param message Details about the exception or custom message
     * @param cause Pass the enriched stacktrace or customised stacktrace
     */
    public WinAppWinAppInvalidPathForExcelWinAppException(String message, Throwable cause) {
        super(message,cause);
    }
}
