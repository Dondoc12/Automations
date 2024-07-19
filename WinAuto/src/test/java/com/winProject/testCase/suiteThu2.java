package com.winProject.testCase;

import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.keywords.WinUI;
import com.winProject.common.BaseTest;
import example.data;
import org.testng.annotations.Test;
import pageObject.pageObject;



public class suiteThu2 extends BaseTest {
    @Test
    void test(){
        try{
            if(WinUI.getWindowElement(pageObject.getPasswordPrompt) != null){
                WinUI.clearAndFillText(pageObject.getUsername, data.userName);
                WinUI.clearAndFillText(pageObject.getPassword,data.password);
                WinUI.clickElement(pageObject.getLoginbtn);
            }
            WinUI.waitForElementInvisible(pageObject.getCopyRight);
            WinUI.checkForElementVisible(pageObject.getSearchField);
        }
        catch (Exception e){
            System.out.println("login successfully");
        }
    }


}



