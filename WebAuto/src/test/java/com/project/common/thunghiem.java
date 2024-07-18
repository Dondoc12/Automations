package com.project.common;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.driver.DriverManagement;
import com.UIAutomation.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;


public class thunghiem extends BaseTest {
    public static By nea = By.xpath("//*[@id=\"content\"]/h2");
    @Test
    void end() throws InterruptedException {
        WebUI.openWebsite("https://the-internet.herokuapp.com/");
        Actions actions = new Actions(DriverManagement.getDriver());
        actions.doubleClick(WebUI.getWebElement(nea)).perform();
    }
    @Test
    void en(){
        System.out.println(Constants.getExtentReportFilePath());
    }
}
