package com.UIAutomation.report;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManagement {
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentTest getExtentTest() {
        //System.out.println("ExtentTestManager class: " + extentTest.get());
        return extentTest.get();
    }

    public static void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static void unload() {
        extentTest.remove();
    }
}
