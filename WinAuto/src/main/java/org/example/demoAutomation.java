package org.example;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pageObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class demoAutomation {
    public static WindowsDriver<WindowsElement> driver;
    @Test
    void test(){
        try{
            openLawSonApp();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            capabilities.setCapability("app", "C:\\Users\\david.le\\AppData\\Local\\Apps\\2.0\\87TZ2E13.BT2\\5PZCH2MX.3J7\\http..tion_52eb4aef56c70532_0009.0001_da8e1d62a18c082e\\LawsonClient.exe");
            capabilities.setCapability("ms:waitForAppLaunch", "5");

            driver = new WindowsDriver<>(new URL("http://127.0.0.1:4723/"), capabilities);
            System.out.println("Driver initialized successfully");
            if(pageObject.getPasswordPrompt(driver) != null){
                pageObject.getUsername(driver).clear();
                pageObject.getUsername(driver).sendKeys(data.userName);
                pageObject.getPassword(driver).clear();
                pageObject.getPassword(driver).sendKeys(data.password);
                pageObject.getLoginbtn(driver).click();
            }

            pageObject.getSearchField(driver).clear();
            pageObject.getSearchField(driver).sendKeys(data.searchData);
            pageObject.getSearchButton(driver).click();

            pageObject.getOKButton(driver).click();
            pageObject.getSearchField(driver).clear();
            pageObject.getSearchField(driver).sendKeys(data.OpenVMIAPI);
            pageObject.getSearchButton(driver).click();
            List<WindowsElement> gribViewData = driver.findElementsByClassName("ListViewItem");
            if(gribViewData.isEmpty()){
                pageObject.getDataset(driver).sendKeys(data.DatasetValue);
                pageObject.getbudgVersion(driver).sendKeys(data.budgVersionValue);
                pageObject.getYear(driver).sendKeys(data.YearValue);
                pageObject.getApplyButton(driver).click();
            }
            gribViewData.get(0).findElementByClassName("TextBox").sendKeys(data.WareHouseValue);
            gribViewData.get(1).findElementByClassName("TextBox").sendKeys(data.MainProductValue);
            gribViewData.get(2).findElementByClassName("TextBox").sendKeys(data.ItemNumberValue);
            pageObject.getApplyButton(driver).click();
            List<WindowsElement> gribViewDetailData = driver.findElementsByClassName("ListViewItem");
            Thread.sleep(1000);
            performDoubleClick(driver, gribViewDetailData.get(0));

            List<WindowsElement> gribViewDetailData2 = driver.findElementsByClassName("ListViewItem");
            gribViewDetailData2.get(22).click();
            String value = gribViewDetailData2.get(22).findElementByAccessibilityId("R23C4").getAttribute("Value.Value").trim();
            System.out.println(value);
            Assert.assertEquals(value,"24");
            TakeImage(driver);
            driver.quit();
        }
        catch (Exception e){
            if(driver != null){
                driver.quit();
            }
        }

    }
//    public static void main(String[] args) throws InterruptedException, MalformedURLException {
//        try{
//            openLawSonApp();
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability("platformName", "windows");
//            capabilities.setCapability("deviceName", "WindowsPC");
//            capabilities.setCapability("app", "C:\\Users\\david.le\\AppData\\Local\\Apps\\2.0\\87TZ2E13.BT2\\5PZCH2MX.3J7\\http..tion_52eb4aef56c70532_0009.0001_da8e1d62a18c082e\\LawsonClient.exe");
//            capabilities.setCapability("ms:waitForAppLaunch", "5");
//
//            driver = new WindowsDriver<>(new URL("http://127.0.0.1:4723/"), capabilities);
//            System.out.println("Driver initialized successfully");
//            if(loginPage.getPasswordPrompt(driver) != null){
//                loginPage.getUsername(driver).clear();
//                loginPage.getUsername(driver).sendKeys(data.userName);
//                loginPage.getPassword(driver).clear();
//                loginPage.getPassword(driver).sendKeys(data.password);
//                loginPage.getLoginbtn(driver).click();
//            }
//            Thread.sleep(10000);
//            loginPage.getSearchField(driver).clear();
//            loginPage.getSearchField(driver).sendKeys(data.searchData);
//            loginPage.getSearchButton(driver).click();
//            Thread.sleep(2500);
//            loginPage.getOKButton(driver).click();
//            loginPage.getSearchField(driver).clear();
//            loginPage.getSearchField(driver).sendKeys(data.OpenVMIAPI);
//            loginPage.getSearchButton(driver).click();
//            List<WindowsElement> gribViewData = driver.findElementsByClassName("ListViewItem");
//            if(gribViewData.isEmpty()){
//                loginPage.getDataset(driver).sendKeys(data.DatasetValue);
//                loginPage.getbudgVersion(driver).sendKeys(data.budgVersionValue);
//                loginPage.getYear(driver).sendKeys(data.YearValue);
//                loginPage.getApplyButton(driver).click();
//            }
//            gribViewData.get(0).findElementByClassName("TextBox").sendKeys(data.WareHouseValue);
//            gribViewData.get(1).findElementByClassName("TextBox").sendKeys(data.MainProductValue);
//            gribViewData.get(2).findElementByClassName("TextBox").sendKeys(data.ItemNumberValue);
//            loginPage.getApplyButton(driver).click();
//            List<WindowsElement> gribViewDetailData = driver.findElementsByClassName("ListViewItem");
//            Thread.sleep(1000);
//            performDoubleClick(driver, gribViewDetailData.get(0));
//
//            List<WindowsElement> gribViewDetailData2 = driver.findElementsByClassName("ListViewItem");
//            gribViewDetailData2.get(22).click();
//            String value = gribViewDetailData2.get(22).findElementByAccessibilityId("R23C4").getAttribute("Value.Value").trim();
//            System.out.println(value);
//            Assert.assertEquals(value,"24");
//  //          killLawSonApp();
//        }
//        catch (Exception e){
//            if(driver != null){
//                driver.quit();
//            }
//        }
//
//    }
    public static void openLawSonApp() {
        String filePath = "C:\\TE\\aaaaaaaa.appref-ms";

        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", filePath);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            process.waitFor(); // Đợi cho đến khi quá trình hoàn thành
            System.out.println("Application launched successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to launch the application: IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Failed to launch the application: InterruptedException");
        }
    }
    public static void killLawSonApp(){
        String processName = "LawsonClient.exe";  // Thay thế bằng tên tiến trình của ứng dụng bạn muốn đóng

        try {
            // Thực thi lệnh taskkill để đóng ứng dụng
            Process process = Runtime.getRuntime().exec("taskkill /IM " + processName + " /F");
            process.waitFor();  // Chờ đợi tiến trình hoàn thành

            System.out.println(processName + " đã bị đóng.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void performDoubleClick(WindowsDriver<WindowsElement> driver, WindowsElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).doubleClick().perform();
        System.out.println("Double click performed on element: " + element);
    }
    public static void TakeImage(WebDriver driver) {
        try {
            File elementScreenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(elementScreenshotFile, new File("test-output/result/" +"thungh" + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



