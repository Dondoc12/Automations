package com.WinUIAutomation.utils;

import com.WinUIAutomation.constants.WinAppConstants;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class WinAppZipUtils {

    private WinAppZipUtils() {
        super();
    }

    /* Make Zip file of Extent Reports in Project Root folder */
    public static void zipReportFolder() {
        if (WinAppConstants.ZIP_FOLDER.toLowerCase().trim().equals(WinAppConstants.YES)) {
            if ((WinAppConstants.ZIP_FOLDER_PATH != null || !WinAppConstants.ZIP_FOLDER_PATH.isEmpty()) && (WinAppConstants.ZIP_FOLDER_NAME != null || !WinAppConstants.ZIP_FOLDER_NAME.isEmpty())) {
                ZipUtil.pack(new File(WinAppConstants.ZIP_FOLDER_PATH), new File(WinAppConstants.ZIP_FOLDER_NAME));
                WinAppLogUtils.info("Zipped " + WinAppConstants.ZIPPED_EXTENT_REPORTS_FOLDER + " successfully !!");
            } else {
                ZipUtil.pack(new File(WinAppConstants.EXTENT_REPORT_FOLDER_PATH), new File(WinAppConstants.ZIPPED_EXTENT_REPORTS_FOLDER));
                WinAppLogUtils.info("Zipped " + WinAppConstants.ZIPPED_EXTENT_REPORTS_FOLDER + " successfully !!");
            }
        }
    }

    public static void zipFolder(String FolderPath, String ZipName) {
        ZipUtil.pack(new File(FolderPath), new File(ZipName + ".zip"));
        WinAppLogUtils.info("Zipped " + FolderPath + " successfully !!");
    }

    public static void zipFile(String FilePath, String ZipName) {
        String sourceFile = FilePath;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(ZipName + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);
            FileInputStream fis = null;
            fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            zipOut.close();
            fis.close();
            fos.close();

            WinAppLogUtils.info("Zipped " + FilePath + " successfully !!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unZip(String FileZipPath, String FolderOutput) {
        ZipUtil.unpack(new File(FileZipPath), new File(FolderOutput));
        WinAppLogUtils.info("Unzipped " + FileZipPath + " successfully !!");
    }

    public static void unZipFile(String FileZipPath, String FolderOutput) {
        try {
            String fileZip = FileZipPath;
            File outputDir = new File(FolderOutput);

            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(outputDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
            WinAppLogUtils.info("Unzipped " + FileZipPath + " successfully !!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) {
        try {
            File destFile = new File(destinationDir, zipEntry.getName());
            String destDirPath = destinationDir.getCanonicalPath();
            String destFilePath = destFile.getCanonicalPath();
            if (!destFilePath.startsWith(destDirPath + File.separator)) {
                throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
            }
            return destFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}