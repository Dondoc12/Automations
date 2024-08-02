package com.UIAutomation.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ExcelUtils {
    private FileInputStream fis;
    private Workbook workbook;
    private Sheet sheet;

    public Object[][] getDataHashMap(String excelPath, String sheetName, ArrayList<String> headers) {
        Object[][] data = null;
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

            // Find the header row and the column index for the given headers
            int headerRowNum = findHeaderRow(sheet, headers);
            if (headerRowNum == -1) {
                throw new IllegalArgumentException("Headers not found.");
            }

            int[] columnIndices = new int[headers.size()];
            for (int i = 0; i < headers.size(); i++) {
                columnIndices[i] = findColumnIndex(sheet.getRow(headerRowNum), headers.get(i));
            }

            // Get data from the columns and store in HashMap
            int numRows = sheet.getLastRowNum() - headerRowNum;
            data = new Object[numRows][1];  // Initialize the Object array
            for (int rowIndex = headerRowNum + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        Cell cell = row.getCell(columnIndices[i]);
                        if (cell != null) {
                            String cellValue;
                            switch (cell.getCellType()) {
                                case STRING:
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        cellValue = cell.getDateCellValue().toString();
                                    } else {
                                        double numericValue = cell.getNumericCellValue();
                                        // Convert numeric value to integer if it's a whole number
                                        cellValue = (numericValue % 1 == 0) ? String.valueOf((int) numericValue) : String.valueOf(numericValue);
                                    }
                                    break;
                                case BOOLEAN:
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case FORMULA:
                                    cellValue = cell.getCellFormula();
                                    break;
                                default:
                                    cellValue = "Unsupported cell type";
                                    break;
                            }
                            map.put(headers.get(i), cellValue);
                        } else {
                            map.put(headers.get(i), ""); // Handle empty cells
                        }
                    }
                    data[rowIndex - headerRowNum - 1][0] = map;
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

    public Object[][] getDataHashMap(String excelPath, String sheetName, ArrayList<String> headers, int startRow, int endRow) {
        Object[][] data = null;
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

            // Find the header row and the column index for the given headers
            int headerRowNum = findHeaderRow(sheet, headers);
            if (headerRowNum == -1) {
                throw new IllegalArgumentException("Headers not found.");
            }

            int[] columnIndices = new int[headers.size()];
            for (int i = 0; i < headers.size(); i++) {
                columnIndices[i] = findColumnIndex(sheet.getRow(headerRowNum), headers.get(i));
            }

            // Ensure endRow does not exceed the actual number of rows
            if (endRow > sheet.getLastRowNum()) {
                endRow = sheet.getLastRowNum();
            }

            // Calculate number of rows to return
            int numRows = endRow - startRow + 1;
            data = new Object[numRows][1];

            // Iterate over the specified row range
            for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < headers.size(); i++) {
                        Cell cell = row.getCell(columnIndices[i]);
                        if (cell != null) {
                            String cellValue = "";
                            switch (cell.getCellType()) {
                                case STRING:
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        cellValue = cell.getDateCellValue().toString();
                                    } else {
                                        // Convert numeric value to integer if possible
                                        double numericValue = cell.getNumericCellValue();
                                        cellValue = (numericValue % 1 == 0) ? String.valueOf((int) numericValue) : String.valueOf(numericValue);
                                    }
                                    break;
                                case BOOLEAN:
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case FORMULA:
                                    cellValue = cell.getCellFormula();
                                    break;
                                default:
                                    cellValue = "Unsupported cell type";
                                    break;
                            }
                            map.put(headers.get(i), cellValue);
                        } else {
                            map.put(headers.get(i), "");
                        }
                    }
                    data[rowIndex - startRow][0] = map;
                } else {
                    data[rowIndex - startRow][0] = new HashMap<>();
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
    private int findHeaderRow(Sheet sheet, ArrayList<String> headers) {
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                boolean allHeadersFound = true;
                for (String header : headers) {
                    boolean headerFound = false;
                    for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                        Cell cell = row.getCell(cellIndex);
                        if (cell != null && header.equals(cell.toString())) {
                            headerFound = true;
                            break;
                        }
                    }
                    if (!headerFound) {
                        allHeadersFound = false;
                        break;
                    }
                }
                if (allHeadersFound) {
                    return rowIndex;
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
}
