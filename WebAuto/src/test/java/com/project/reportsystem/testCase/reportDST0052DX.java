package com.project.reportsystem.testCase;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.driver.DriverManagement;
import com.UIAutomation.driver.Target;
import com.UIAutomation.helpers.ExcelHelpers;
import com.UIAutomation.helpers.Helpers;
import com.UIAutomation.keywords.WebUI;
import com.UIAutomation.utils.ZipUtils;
import com.project.common.BaseTest;
import com.project.reportsystem.pageElement.DST0052DXRPTPage;
import com.project.reportsystem.pageElement.homeRPTPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class reportDST0052DX extends BaseTest {
    private static loginRPT login = new loginRPT();
    private static homeRPTPage homeRPTPage = new homeRPTPage();
    private static DST0052DXRPTPage DST0052DXRPTPage = new DST0052DXRPTPage();
    @DataProvider(name = "reportDST0052DXdata", parallel = true)
    public static Object[][] reportDST0052DXdata(){
        ExcelHelpers excelHelpers = new ExcelHelpers();
        Object[][] data = excelHelpers.getDataHashTable(Helpers.getCurrentDir() + Constants.REPORTDST0052DX_EXCEL_DATA_FILE_PATH, "Sheet1", 1, 1);
        return data;
    }
    @Test
    public void genreportDST0052DXSuccess(){
        login.loginSuccess();
        WebUI.clickElement(homeRPTPage.DST0052DX);
        WebUI.clearAndFillText(DST0052DXRPTPage.Facility,"TAV");
        WebUI.clearAndFillText(DST0052DXRPTPage.StartDate,"20240510");
        WebUI.clearAndFillText(DST0052DXRPTPage.EndDate,"20240511");
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
                Assert.assertEquals(value3,"TAV");
                Assert.assertEquals(value4,"20240510");
                Assert.assertEquals(value5,"20240511");
                break;
            }
        }
    }
    public static void main(String[]args){
        WebDriver driver = new Target().createInstance("CHROME");
        DriverManagement.setDriver(driver);
        driver.manage().window().maximize();
        login.loginSuccess();
        WebUI.clickElement(homeRPTPage.DST0052DX);
        WebUI.clickElement(DST0052DXRPTPage.btnOutPutFolder);
        WebUI.selectOptionByText(DST0052DXRPTPage.selectDays,"3 Days");
        String requestNumber = "142100";
        WebElement table = WebUI.getWebElement(By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody"));
        List<WebElement> tableData = table.findElements(By.tagName("tr"));
        for (int i = 0; i <= tableData.size();i++){
            List<WebElement> requestData = tableData.get(i).findElements(By.tagName("td"));
                if(requestData.size() > 5 && requestData.get(0).getText().equals(requestNumber)){
                    int j = i +1;
                    By downloadxlsxlink = By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[" + j + "]/td["+ 6 +"]/a");
                    WebUI.clickElement(downloadxlsxlink);
                    break;
                }
        }
        WebUI.sleep(5);
        String directoryPath = Constants.DOWNLOAD_FOLDER;
        List<String> fileNames = ListFilesInDirectory.listFiles(directoryPath);
        for(String filename : fileNames){
            if(filename.contains(requestNumber) && filename.contains(".zip")){
                String filPath = directoryPath + filename;
                ZipUtils.unZip(filPath,directoryPath);
            }
        }
        List<String> excelfileNames = ListFilesInDirectory.listFiles(directoryPath);
        for(String excelfilename : excelfileNames){
            if(excelfilename.contains(".xls") && !excelfilename.contains(".zip")){
                ExcelHelpers excelHelpers = new ExcelHelpers();
                Object[][] data = excelHelpers.getDataHashTable(excelfilename, "Sheet1", 2, 100,1);
            }
        }
    }
}

