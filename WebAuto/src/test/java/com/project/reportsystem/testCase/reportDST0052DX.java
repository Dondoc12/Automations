package com.project.reportsystem.testCase;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.driver.DriverManagement;
import com.UIAutomation.driver.Target;
import com.UIAutomation.helpers.ExcelHelpers;
import com.UIAutomation.helpers.Helpers;
import com.UIAutomation.keywords.WebUI;
import com.project.common.BaseTest;
import com.project.reportsystem.pageElement.DST0052DXRPTPage;
import com.project.reportsystem.pageElement.homeRPTPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        WebDriver driver = new Target().createInstance("CHROME");
        DriverManagement.setDriver(driver);
        driver.manage().window().maximize();
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
    @Test
    public void downLoadReport(){
        WebDriver driver = new Target().createInstance("CHROME");
        DriverManagement.setDriver(driver);
        driver.manage().window().maximize();
        login.loginSuccess();
        WebUI.clickElement(homeRPTPage.DST0052DX);
        WebUI.clickElement(DST0052DXRPTPage.btnOutPutFolder);
        WebUI.verifySelectedByText(DST0052DXRPTPage.selectDays,"3 Days");
        String requestNumber = "";
        WebElement table = WebUI.getWebElement(By.xpath("/html/body/table[3]/tbody/tr/td/table/tbody/tr/td/table/tbody"));
        List<WebElement> tableData = table.findElements(By.tagName("tr"));
        for (WebElement data : tableData){
            List<WebElement> requestData = data.findElements(By.tagName("td"));
            if(requestData.get(0).getText().equals(requestNumber)){
                WebUI.clickElement(requestData.get(5));
                break;
            }
        }

    }
}

