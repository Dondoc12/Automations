package com.WinUIAutomation.exceptions;

public class WinAppHeadlessNotSupportedException extends IllegalStateException {

    public WinAppHeadlessNotSupportedException(String browser) {
        super(String.format("Headless not supported for %s browser", browser));
    }
}
