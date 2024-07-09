package com.WinUIAutomation.exceptions;

public class WinAppTargetNotValidException extends IllegalStateException {

    public WinAppTargetNotValidException(String target) {
        super(String.format("Target %s not supported. Use either local or gird", target));
    }

}