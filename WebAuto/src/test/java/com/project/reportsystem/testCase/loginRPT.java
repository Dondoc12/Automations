package com.project.reportsystem.testCase;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.keywords.WebUI;
import com.project.common.BaseTest;
import com.project.reportsystem.pageElement.loginRPTPage;
import org.testng.annotations.Test;

public class loginRPT extends BaseTest {
    public loginRPTPage loginRPTPage = new loginRPTPage();
    @Test
    public void loginSuccess(){
        WebUI.openWebsite("https://uat-report.talgroup.com/");
        WebUI.clearAndFillText(loginRPTPage.username, Constants.RPT_USERNAME);
        WebUI.clearAndFillText(loginRPTPage.password,Constants.RPT_PASSWORD);
        WebUI.clickElement(loginRPTPage.btnLogin);
    }
}
