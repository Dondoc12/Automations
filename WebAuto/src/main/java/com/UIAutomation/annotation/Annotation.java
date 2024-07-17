package com.UIAutomation.annotation;


import com.UIAutomation.enums.AuthorValue;
import com.UIAutomation.enums.CategoryValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Annotation {

    // Get Author
    public AuthorValue[] author();

    // Get Category
    public CategoryValue[] category();
}
