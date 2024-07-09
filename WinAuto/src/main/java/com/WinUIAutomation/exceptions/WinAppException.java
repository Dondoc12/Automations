package com.WinUIAutomation.exceptions;

@SuppressWarnings("serial")
public class WinAppException extends RuntimeException {

    public WinAppException(String message) {
        super(message);
    }

    public WinAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
