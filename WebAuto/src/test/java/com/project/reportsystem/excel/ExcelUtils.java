package com.project.reportsystem.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private FileInputStream fis;
    private Workbook workbook;
    private Sheet sheet;

    public List<String> getDataByHeader(String excelPath, String sheetName, String headerName) {
        List<String> data = new ArrayList<>();
        try {
            File f = new File(excelPath);
            if (!f.exists()) {
                throw new IOException("File Excel path not found.");
            }

            fis = new FileInputStream(excelPath);

            // Determine the file type and create the appropriate workbook
            if (excelPath.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (excelPath.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IOException("Unsupported file format");
            }

            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " not found.");
            }

            // Find the header row and the column index for the given header name
            int headerRowNum = findHeaderRow(sheet, headerName);
            if (headerRowNum == -1) {
                throw new IllegalArgumentException("Header " + headerName + " not found.");
            }
            int columnIndex = findColumnIndex(sheet.getRow(headerRowNum), headerName);

            // Get data from the column
            for (int rowIndex = headerRowNum + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        data.add(cell.toString());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private int findHeaderRow(Sheet sheet, String headerName) {
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex);
                    if (cell != null && headerName.equals(cell.toString())) {
                        return rowIndex;
                    }
                }
            }
        }
        return -1;
    }

    private int findColumnIndex(Row headerRow, String headerName) {
        for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
            Cell cell = headerRow.getCell(cellIndex);
            if (cell != null && headerName.equals(cell.toString())) {
                return cellIndex;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils();
        List<String> data = excelUtils.getDataByHeader("path/to/excel/file.xlsx", "Sheet1", "Grouping");
        for (String value : data) {
            System.out.println(value);
        }
    }
}
