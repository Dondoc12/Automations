package org.example;

import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriver;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.keywords.ByAccessibilityId;
import com.WinUIAutomation.keywords.WinUI;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pageObject;

import java.util.List;
import java.util.Objects;

public class demoAutomation {
    public static WindowsDriver<WindowsElement> driver;
    @Test
    void test(){
        try{
            WindowsDriver<WindowsElement> driver = WinAppDriver.valueOf(WinAppConstants.APP_NAME).createDriver();
            WinAppDriverManagement.setDriver(driver);
            System.out.println("Driver initialized successfully");
            if(WinUI.getWindowElement(pageObject.getPasswordPrompt) != null){
                WinUI.clearAndFillText(pageObject.getUsername,data.userName);
                WinUI.clearAndFillText(pageObject.getPassword,data.password);
                WinUI.performDoubleClick(WinUI.getWindowElement(pageObject.getUsername));
                WinUI.clickElement(pageObject.getLoginbtn);
            }
            WinUI.waitForElementInvisible(pageObject.getCopyRight);
            System.out.println("login successfully");
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
            List<WebElement> gribViewDetailData2 = WinUI.getWindowElements(By.className("ListViewItem"));
            WinUI.clickElement(Objects.requireNonNull(gribViewDetailData2).get(22));
            String value = gribViewDetailData2.get(22).findElement(ByAccessibilityId.accessibilityId("R23C4")).getAttribute("Value.Value").trim();
            System.out.println(value);
            Assert.assertEquals(value,"24");
            WinAppDriverManagement.quit();
        }
        catch (Exception e){
            if(driver != null){
                driver.quit();
            }
        }
    }
    public static void performDoubleClick(WindowsDriver<WindowsElement> driver, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).doubleClick().perform();
        System.out.println("Double click performed on element: " + element);
    }

}



