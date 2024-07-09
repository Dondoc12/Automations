package com.WinUIAutomation.annotation;


import com.WinUIAutomation.enums.WinAppAuthorValue;
import com.WinUIAutomation.enums.WinAppCategoryValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WinAppAnnotation {

    // Get Author
    public WinAppAuthorValue[] author();

    // Get Category
    public WinAppCategoryValue[] category();
}
