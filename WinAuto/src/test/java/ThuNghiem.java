import com.WinUIAutomation.driver.WinAppDriver;
import com.WinUIAutomation.driver.WinAppDriverManagement;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.example.data;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.pageObject;

import java.net.MalformedURLException;

public class ThuNghiem {
    public static WebElement getUsername(WindowsDriver driver) {
        return getLocatedElement(driver, "User");
    }
    public static void main(String[] args) throws MalformedURLException {
        WindowsDriver<WindowsElement> driver = (WindowsDriver<WindowsElement>) ThreadGuard.protect(WinAppDriver.LAWSON_CLIENT.createDriver());

        WinAppDriverManagement.setDriver(driver);

        System.out.println("Driver created successfully!");
        if(pageObject.getPasswordPrompt(driver) != null){
            getUsername(driver).clear();
            getUsername(driver).sendKeys(data.userName);
         }
    }
    public static WebElement getLocatedElement(WindowsDriver driver, String elementAutoID) {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            try{
                WebDriverWait wait = new WebDriverWait(driver,20);
                return wait.until(ExpectedConditions.visibilityOf(driver.findElementByAccessibilityId(elementAutoID)));
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }
}
