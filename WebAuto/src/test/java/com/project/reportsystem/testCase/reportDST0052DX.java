package com.project.reportsystem.testCase;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.helpers.FileHelpers;
import com.UIAutomation.helpers.Helpers;
import com.UIAutomation.helpers.deleteFolderHelper;
import com.UIAutomation.keywords.WebUI;
import com.UIAutomation.report.ExtentTestManagement;
import com.UIAutomation.utils.LogUtils;
import com.UIAutomation.utils.ZipUtils;
import com.aventstack.extentreports.Status;
import com.project.common.BaseTest;
import com.UIAutomation.utils.ExcelUtils;
import com.project.reportsystem.pageElement.DST0052DXRPTPage;
import com.project.reportsystem.pageElement.homeRPTPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class reportDST0052DX extends BaseTest {
    private static loginRPT login = new loginRPT();
    private static homeRPTPage homeRPTPage = new homeRPTPage();
    private static DST0052DXRPTPage DST0052DXRPTPage = new DST0052DXRPTPage();
    @DataProvider(name = "reportDST0052DXData")
    public static Object[][] getReportDST0052DXData() {
        String excelPath = Helpers.getCurrentDir() + Constants.REPORTDST0052DX_EXCEL_DATA_FILE_PATH;
        ExcelUtils excelUtils = new ExcelUtils();
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Facility");
        headers.add("StartDate");
        headers.add("EndDate");
        Object[][] data = excelUtils.getDataHashMap(excelPath, "Sheet1", headers);

        // Calculate the number of rows
        int rowCount = data.length;
        Object[][] testdata = new Object[rowCount][3];  // Initialize the array with proper size

        // Populate the testdata array
        for (int i = 0; i < rowCount; i++) {
            if (data[i][0] != null) {
                HashMap<String, String> map = (HashMap<String, String>) data[i][0];
                String facility = map.get("Facility");
                String startDate = map.get("StartDate");
                String endDate = map.get("EndDate");
                testdata[i][0] = facility;
                testdata[i][1] = startDate;
                testdata[i][2] = endDate;
            }
        }
        return testdata;
    }
    @Test(dataProvider = "reportDST0052DXData",groups = {"Gen Report"})
    public void genreportDST0052DXSuccess(String facility, String startDate, String endDate){
        login.loginSuccess();
        WebUI.clickElement(By.xpath("/html/body/table[1]/tbody/tr/td[2]/font/nobr[1]/a[2]"));
        WebUI.clickElement(homeRPTPage.DST0052DX);
        WebUI.clearAndFillText(DST0052DXRPTPage.Facility,facility);
        WebUI.clearAndFillText(DST0052DXRPTPage.StartDate,startDate);
        WebUI.clearAndFillText(DST0052DXRPTPage.EndDate,endDate);
        WebUI.selectOptionByText(DST0052DXRPTPage.OutputFormat,"Excel (Data only)");
        WebUI.clickElement(DST0052DXRPTPage.btnSubmit);
        WebUI.sleep(1);
        String requestNumber = WebUI.getTextElement(DST0052DXRPTPage.requestNumber);
        WebUI.clickElement(DST0052DXRPTPage.btnOutPutFolderOnSubmitSuccessPage);
        WebElement table = WebUI.getWebElement(By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody"));
        List<WebElement> tableData = table.findElements(By.tagName("tr"));
        for (WebElement data : tableData){
            List<WebElement> requestData = data.findElements(By.tagName("td"));
            if(requestData.get(0).getText().equals(requestNumber)){
                String Parameters = requestData.get(6).getText();
                String[] values = Parameters.split(";");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                String value3 = values[2];
                String value4 = values[3];
                String value5 = values[4];
                Assert.assertEquals(value3,facility);
                Assert.assertEquals(value4,startDate);
                Assert.assertEquals(value5,endDate);
                break;
            }
        }
    }
    @DataProvider(name = "Requestnumber")
    public static Object[][] getConfigJsonData() {
        String excelPath = Helpers.getCurrentDir() + Constants.REPORTDST0052DX_DOWNLOAD_EXCEL_DATA_FILE_PATH;
        ExcelUtils excelUtils = new ExcelUtils();
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Requestnumber");
        Object[][] data = excelUtils.getDataHashMap(excelPath, "Sheet1", headers);
        int rowCount = data.length;
        Object[][] testdata = new Object[rowCount][1];
        for (int i = 0; i < rowCount; i++) {
            if (data[i][0] != null) {
                HashMap<String, String> map = (HashMap<String, String>) data[i][0];
                String requestNumber = map.get("Requestnumber");
                testdata[i][0] = requestNumber;
            }
        }
        return testdata;
    }
    @Test (dataProvider = "Requestnumber", groups = {"Check Data"})
    public void checkUnShipRecord(String requestNumber) {
        String directoryPath = Constants.DOWNLOAD_FOLDER;
        deleteFolderHelper.deleteFiles(directoryPath);
        login.loginSuccess();
        WebUI.clickElement(homeRPTPage.DST0052DX);
        WebUI.clickElement(DST0052DXRPTPage.btnOutPutFolder);
        WebUI.selectOptionByText(DST0052DXRPTPage.selectDays,"3 Days");
        WebElement table = WebUI.getWebElement(By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody"));
        List<WebElement> tableData = table.findElements(By.tagName("tr"));
        for (int i = 0; i <= tableData.size();i++){
            List<WebElement> requestData = tableData.get(i).findElements(By.tagName("td"));
                if(requestData.size() > 5 && requestData.get(0).getText().equals(requestNumber)){
                    int j = i +1;
                    By downloadxlsxlink = By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[" + j + "]/td["+ 6 +"]/a");
                    WebUI.clickElement(downloadxlsxlink);
                    FileHelpers.waitForDownloadToComplete(Constants.DOWNLOAD_FOLDER,30);
                    List<String> fileNames = FileHelpers.listFiles(directoryPath);
                    for(String filename : fileNames){
                        if(filename.contains(requestNumber) && filename.contains(".zip")){
                            String filPath = directoryPath + filename;
                            ZipUtils.unZip(filPath,directoryPath);
                        }
                    }
                    List<String> excelfileNames = FileHelpers.listFiles(directoryPath);
                    for (String excelfilename : excelfileNames) {
                        if (excelfilename.contains(".xls") && excelfilename.contains(requestNumber) && !excelfilename.contains(".zip")) {
                            ExcelUtils excelUtils = new ExcelUtils();
                            ArrayList<String> headers = new ArrayList<>();
                            headers.add("LOT#");
                            headers.add("STAS");
                            headers.add("2nd grade A/B/C or garment grade U");
                            Object[][] data = excelUtils.getDataHashMap(Constants.DOWNLOAD_FOLDER + excelfilename, "Sheet1", headers);
                            for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                                Object[] row = data[rowIndex];
                                if (row[0] != null) {
                                    HashMap<String, String> map = (HashMap<String, String>) row[0];
                                    String stas = map.get("STAS");
                                    String grade = map.get("2nd grade A/B/C or garment grade U");
                                    String lotNumber = map.get("LOT#");
                                    if (stas != null && stas.equals("3") && grade != null && grade.equals("U")) {
                                        boolean check = lotNumber.endsWith("U");
                                        if (!check) {
                                            String errorMessage = "Row: " + (rowIndex + 1) + " - LOT# " + lotNumber + " STAS: " + stas + " Grade: " + grade + " - Fail";
                                            ExtentTestManagement.getExtentTest().log(Status.FAIL, errorMessage);
                                        }
                                    } else {
                                        if (lotNumber != null) {
                                            if (lotNumber.endsWith("U")) {
                                                String errorMessage = "Row: " + (rowIndex + 1) + " - LOT# " + lotNumber + " STAS: " + stas + " Grade: " + grade + " - Fail";
                                                ExtentTestManagement.getExtentTest().log(Status.FAIL, errorMessage);
                                            }
                                        } else {
                                            LogUtils.info("Missing Data at Row: " + (rowIndex + 1));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
        if (ExtentTestManagement.getExtentTest().getStatus() == Status.FAIL) {
            Assert.fail("There are errors in the report. Please check the ExtentReports for details.");
        }
    }
    @Test (groups = {"Check Data"})
    public void checkUnShipSingleRecord() {
        String requestNumber = "142100";
        String directoryPath = Constants.DOWNLOAD_FOLDER;
        deleteFolderHelper.deleteFiles(directoryPath);
        login.loginSuccess();
        WebUI.clickElement(By.xpath("/html/body/table[1]/tbody/tr/td[2]/font/nobr[1]/a[2]"));
        WebUI.clickElement(homeRPTPage.DST0052DX);
        WebUI.clickElement(DST0052DXRPTPage.btnOutPutFolder);
        WebUI.selectOptionByText(DST0052DXRPTPage.selectDays, "All");

        WebElement table = WebUI.getWebElement(By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody"));
        List<WebElement> tableData = table.findElements(By.tagName("tr"));
        for (int i = 0; i < tableData.size(); i++) {
            List<WebElement> requestData = tableData.get(i).findElements(By.tagName("td"));
            if (requestData.size() > 5 && requestData.get(0).getText().equals(requestNumber)) {
                int j = i + 1;
                By downloadxlsxlink = By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[" + j + "]/td[" + 6 + "]/a");
                WebUI.clickElement(downloadxlsxlink);
                WebUI.sleep(3);
                FileHelpers.waitForDownloadToComplete(Constants.DOWNLOAD_FOLDER, 30);
                List<String> fileNames = FileHelpers.listFiles(directoryPath);
                for (String filename : fileNames) {
                    if (filename.contains(requestNumber) && filename.contains(".zip")) {
                        String filPath = directoryPath + filename;
                        ZipUtils.unZip(filPath, directoryPath);
                    }
                }
                List<String> excelfileNames = FileHelpers.listFiles(directoryPath);
                for (String excelfilename : excelfileNames) {
                    if (excelfilename.contains(".xls") && excelfilename.contains(requestNumber) && !excelfilename.contains(".zip")) {
                        ExcelUtils excelUtils = new ExcelUtils();
                        ArrayList<String> headers = new ArrayList<>();
                        headers.add("LOT#");
                        headers.add("STAS");
                        headers.add("2nd grade A/B/C or garment grade U");
                        Object[][] data = excelUtils.getDataHashMap(Constants.DOWNLOAD_FOLDER + excelfilename, "Sheet1", headers);
                        for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                            Object[] row = data[rowIndex];
                            if (row[0] != null) {
                                HashMap<String, String> map = (HashMap<String, String>) row[0];
                                String stas = map.get("STAS");
                                String grade = map.get("2nd grade A/B/C or garment grade U");
                                String lotNumber = map.get("LOT#");
                                if    (stas != null && stas.equals("3") && grade != null && grade.equals("U")) {
                                    boolean check = lotNumber.endsWith("U");
                                    if (!check) {
                                        String errorMessage = "Row: " + (rowIndex + 1) + " - LOT# " + lotNumber + " STAS: " + stas + " Grade: " + grade + " - Fail";
                                        ExtentTestManagement.getExtentTest().log(Status.FAIL, errorMessage);
                                    }
                                } else {
                                    if (lotNumber != null) {
                                        if (lotNumber.endsWith("U")) {
                                            String errorMessage = "Row: " + (rowIndex + 1) + " - LOT# " + lotNumber + " STAS: " + stas + " Grade: " + grade + " - Fail";
                                            ExtentTestManagement.getExtentTest().log(Status.FAIL, errorMessage);
                                        }
                                    } else {
                                        LogUtils.info("Missing Data at Row: " + (rowIndex + 1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ExtentTestManagement.getExtentTest().getStatus() == Status.FAIL) {
            ExtentTestManagement.getExtentTest().log(Status.PASS,"All row is passed");
        }
    }
}