package com.winProject.testCase;

import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.keywords.WinUI;
import com.WinUIAutomation.utils.WinAppLogUtils;
import com.winProject.common.BaseTest;
import example.data;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import pageObject.pageObject;
import java.util.List;



public class demoAutomation extends BaseTest {
    @Test
    void test(){
        try{
            if(WinUI.getWindowElement(pageObject.getPasswordPrompt) != null){
                WinUI.clearAndFillText(pageObject.getUsername, data.userName);
                WinUI.clearAndFillText(pageObject.getPassword,data.password);
                WinUI.clickElement(pageObject.getLoginbtn);
                WinUI.waitForElementInvisible(pageObject.getCopyRight);
                System.out.println("login successfully");
            }
            WinUI.clearAndFillText(pageObject.getSearchField,data.searchData);
            WinUI.clickElement(pageObject.getSearchButton);
            WinUI.waitForElementVisible(pageObject.getOKButton);
            WinUI.clickElement(pageObject.getOKButton);
            WinUI.clearAndFillText(pageObject.getSearchField,data.OpenVMIAPI);
            WinUI.clickElement(pageObject.getSearchButton);
            List<WebElement> gribViewData = WinUI.getWindowElements(By.className("ListViewItem"));
            if(gribViewData.isEmpty()){
                WinUI.clearAndFillText(pageObject.getDataset,data.DatasetValue);
                WinUI.clearAndFillText(pageObject.getbudgVersion,data.budgVersionValue);
                WinUI.clearAndFillText(pageObject.getYear,data.YearValue);
                WinUI.clickElement(pageObject.getApplyButton);
            }
            gribViewData.get(0).findElement(By.className("TextBox")).sendKeys(data.WareHouseValue);
            gribViewData.get(1).findElement(By.className("TextBox")).sendKeys(data.MainProductValue);
            gribViewData.get(2).findElement(By.className("TextBox")).sendKeys(data.ItemNumberValue);
            WinUI.clickElement(pageObject.getApplyButton);
            List<WebElement> gribViewDetailData = WinUI.getWindowElements(By.className("ListViewItem"));
            WinUI.clickElement(gribViewDetailData.get(2));
            WinUI.performDoubleClick(gribViewDetailData.get(2));
            WinUI.getWindowElements(By.className("ListViewItem")).get(23).click();
        }
        catch (Exception e){
            if(WinAppDriverManagement.getDriver() != null){
                WinAppLogUtils.info("error");
            }
        }
    }
}



