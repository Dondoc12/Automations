package com.project.reportsystem.testCase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListFilesInDirectory {
    public static List<String> listFiles(String directoryName) {
        File directory = new File(directoryName);
        List<String> fileNames = new ArrayList<>();
        File[] filesList = directory.listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                } else if (file.isDirectory()) {
                    fileNames.addAll(listFiles(file.getAbsolutePath()));
                }
            }
        } else {
            System.out.println("The specified directory does not exist or is not a directory.");
        }
        return fileNames;
    }
}


