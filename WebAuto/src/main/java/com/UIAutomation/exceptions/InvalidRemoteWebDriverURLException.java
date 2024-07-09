package com.UIAutomation.exceptions;

@SuppressWarnings("serial")
public class InvalidRemoteWebDriverURLException extends Exception {

    public InvalidRemoteWebDriverURLException(String message) {
        super(message);
    }

    public InvalidRemoteWebDriverURLException(String message, Throwable cause) {
        super(message, cause);
    }

}