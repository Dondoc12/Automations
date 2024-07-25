package com.UIAutomation.helpers;

import com.UIAutomation.utils.LogUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileHelpers {

    public FileHelpers() {
        super();
    }

    public static void writeTxtFile(String filepath, String text) {
        try {
            File file = new File(filepath);
            while (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text + "\n" + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTxtFile(String filepath) {
        try {
            File f = new File(filepath);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readLineTxtFile(String filepath, int line) {
        List<String> lines;
        String value;
        try {
            lines = Files.readAllLines(new File(filepath).toPath());
            value = lines.get(line);
            return value;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyFile(String source_FilePath, String target_FilePath) {
        try {
            Files.copy(Paths.get(source_FilePath), Paths.get(target_FilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFileLastModified(String folderPath) {
        File dir = new File(folderPath);
        if (dir.isDirectory()) {
            Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile)).max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
            if (opFile.isPresent()) {
                LogUtils.info("getFileLastModified: " + opFile.get().getPath());
                return opFile.get();
            } else {
                LogUtils.info("getFileLastModified: " + opFile.get().getPath());
                return null;
            }
        }

        return null;
    }
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

