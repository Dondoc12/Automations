package com.project.reportsystem.testCase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static void waitForDownloadToComplete(String downloadDir, int timeoutInSeconds) {
        File dir = new File(downloadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File[] initialFiles = dir.listFiles();
        int initialNumberOfFiles = (initialFiles != null) ? initialFiles.length : 0;
        long initialSize = (initialFiles != null) ? Arrays.stream(initialFiles).mapToLong(File::length).sum() : 0;

        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000;
        while (System.currentTimeMillis() < endTime) {
            File[] currentFiles = dir.listFiles();
            int currentNumberOfFiles = (currentFiles != null) ? currentFiles.length : 0;
            long currentSize = (currentFiles != null) ? Arrays.stream(currentFiles).mapToLong(File::length).sum() : 0;
            if (currentNumberOfFiles > initialNumberOfFiles && currentSize > initialSize) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


