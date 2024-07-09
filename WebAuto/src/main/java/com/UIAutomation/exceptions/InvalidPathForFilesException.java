package com.UIAutomation.exceptions;

@SuppressWarnings("serial")
public class InvalidPathForFilesException extends Exception {

    public InvalidPathForFilesException(String message) {
        super(message);
    }

    public InvalidPathForFilesException(String message, Throwable cause) {
        super(message, cause);
    }

}