import com.WinUIAutomation.constants.WinAppConstants;
import com.WinUIAutomation.driver.WinAppDriver;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import com.WinUIAutomation.keywords.ByAccessibilityId;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.example.data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.example.demoAutomation.TakeImage;

public class ThuNghiem {
    public By buttonAddClient = By.xpath("//a[normalize-space()='Add client']");
    public By heloman = ByAccessibilityId.accessibilityId("");
    public static WebElement getLocatedElement(String elementAutoID) {
        WinAppDriverManagement.getDriver().manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        for (String windowHandle : WinAppDriverManagement.getDriver().getWindowHandles()) {
            WinAppDriverManagement.getDriver().switchTo().window(windowHandle);
            try{
                WebDriverWait wait = new WebDriverWait(WinAppDriverManagement.getDriver(),20);
                return wait.until(ExpectedConditions.visibilityOf(WinAppDriverManagement.getDriver().findElementByAccessibilityId(elementAutoID)));
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }
    public static WebElement getUsername() {
        return getLocatedElement("User");
    }
    public static WebElement getPassword() {
        return getLocatedElement( "Password");
    }
    public static WebElement getLoginbtn() {
        return getLocatedElement("SignIn");
    }
    public static WebElement getSearchButton() {
        return getLocatedElement( "PART_SearchButton");
    }
    public static WebElement getSearchField() {
        return getLocatedElement( "PART_Field");
    }
    public static WebElement getPasswordPrompt() {
        return getLocatedElement( "PasswordPrompt");
    }
    public static WebElement getOKButton(){
        return getLocatedElement("ButtonOK");
    }
    public static WebElement getDataset(){
        return getLocatedElement("WASBDS");
    }
    public static WebElement getbudgVersion(){
        return getLocatedElement("WABVER");
    }
    public static WebElement getYear(){
        return getLocatedElement("WAYEA4");
    }
    public static WebElement getApplyButton(){
        return getLocatedElement("XT_0168");
    }
    public static WebElement getQuality(){
        return getLocatedElement("R23C4");
    }
    public static void main(String[] args) throws MalformedURLException {
        WindowsDriver<WindowsElement> driver = WinAppDriver.valueOf(WinAppConstants.APP_NAME).createDriver();
        WinAppDriverManagement.setDriver(driver);
        System.out.println("Driver created successfully!");
        if(getPasswordPrompt() != null){
            getUsername().clear();
            getUsername().sendKeys(data.userName);
            getPassword().clear();
            getPassword().sendKeys(data.password);
            getLoginbtn().click();
        }
        WebDriverWait wait  = new WebDriverWait(WinAppDriverManagement.getDriver(),20);
        wait.until(ExpectedConditions.invisibilityOf(getLocatedElement("PART_CopyRight")));
        getSearchField().clear();
        getSearchField().sendKeys(data.searchData);
        getSearchButton().click();
        getOKButton().click();
        getSearchField().clear();
        getSearchField().sendKeys(data.OpenVMIAPI);
        getSearchButton().click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("ListViewItem"))));
        List<WindowsElement> gribViewData = driver.findElementsByClassName("ListViewItem");
        if(gribViewData.isEmpty()){
            getDataset().sendKeys(data.DatasetValue);
            getbudgVersion().sendKeys(data.budgVersionValue);
            getYear().sendKeys(data.YearValue);
            getApplyButton().click();
        }
        gribViewData.get(0).findElementByClassName("TextBox").sendKeys(data.WareHouseValue);
        gribViewData.get(1).findElementByClassName("TextBox").sendKeys(data.MainProductValue);
        gribViewData.get(2).findElementByClassName("TextBox").sendKeys(data.ItemNumberValue);
        getApplyButton().click();
        List<WindowsElement> gribViewDetailData = driver.findElementsByClassName("ListViewItem");
        List<WindowsElement> gribViewDetailData2 = driver.findElementsByClassName("ListViewItem");
        gribViewDetailData2.get(22).click();
        String value = gribViewDetailData2.get(22).findElementByAccessibilityId("R23C4").getAttribute("Value.Value").trim();
        System.out.println(value);
        Assert.assertEquals(value,"24");
        TakeImage(driver);
        WinAppDriverManagement.quit();
        System.out.println("Driver shut down successfully!");
    }
}
