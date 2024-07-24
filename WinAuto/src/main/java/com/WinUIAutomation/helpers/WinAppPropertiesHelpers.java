package com.WinUIAutomation.helpers;

import com.WinUIAutomation.helpers.WinAppHelpers;
import com.WinUIAutomation.utils.WinAppLanguageUtils;
import com.WinUIAutomation.utils.WinAppLogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;



public class WinAppPropertiesHelpers {

    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;
    private static FileOutputStream out;
    private static String relPropertiesFilePathDefault = "src/test/resources/config/config.properties";
    private static String mainModulePath = WinAppHelpers.getCurrentDir();
    public static Properties loadAllFiles() {
        LinkedList<String> files = new LinkedList<>();
        // Add all file Properties
        files.add("src/test/resources/config/config.properties");
        files.add("src/test/resources/config/data.properties");


        try {
            properties = new Properties();

            for (String f : files) {
                Properties tempProp = new Properties();
                if (!mainModulePath.contains("WinAuto")){
                    mainModulePath = WinAppHelpers.getCurrentDir() + "WinAuto\\";
                }
                linkFile = mainModulePath + f;
                file = new FileInputStream(linkFile);
                tempProp.load(file);
                properties.putAll(tempProp);
            }
            file.close();
            WinAppLogUtils.info("Loaded all properties files.");
            return properties;
        } catch (IOException e) {
            WinAppLogUtils.info("Warning !! Can not Load File: " + linkFile);
            WinAppLogUtils.info("Warning !! Can not Load All File.");
            return new Properties();
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setFile(String relPropertiesFilePath) {
        properties = new Properties();
        try {
            if (!mainModulePath.contains("WinAuto")){
                mainModulePath = WinAppHelpers.getCurrentDir() + "WinAuto\\";
            }
            linkFile = mainModulePath + relPropertiesFilePath;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultFile() {
        properties = new Properties();
        try {
            if (!mainModulePath.contains("WinAuto")){
                mainModulePath = WinAppHelpers.getCurrentDir() + "WinAuto\\";
            }
            linkFile = mainModulePath + relPropertiesFilePathDefault;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String keyValue = null;
        try {
            if (!mainModulePath.contains("WinAuto")){
                mainModulePath = WinAppHelpers.getCurrentDir() + "WinAuto\\";
            }
            if (file == null && properties == null) {
                properties = new Properties();
                linkFile = mainModulePath + relPropertiesFilePathDefault;
                file = new FileInputStream(linkFile);
                properties.load(file);
                file.close();
            }
            // Get value from file
            keyValue = properties.getProperty(key);
            return WinAppLanguageUtils.convertCharset_ISO_8859_1_To_UTF8(keyValue);
            //return keyValue;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return keyValue;
        }
    }

    public static void setValue(String key, String keyValue) {
        try {
            if (!mainModulePath.contains("WinAuto")){
                mainModulePath = WinAppHelpers.getCurrentDir() + "WinAuto\\";
            }
            if (file == null) {
                properties = new Properties();
                file = new FileInputStream(mainModulePath + relPropertiesFilePathDefault);
                properties.load(file);
                file.close();
                out = new FileOutputStream(mainModulePath + relPropertiesFilePathDefault);
            }
            //Write to the same Prop file as the extracted file
            out = new FileOutputStream(linkFile);
            System.out.println(linkFile);
            properties.setProperty(key, keyValue);
            properties.store(out, "Set value to properties file success.");
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}