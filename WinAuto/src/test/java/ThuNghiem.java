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
        return getLocatedElement("User");
    }
    public static void main(String[] args) throws MalformedURLException {
        WindowsDriver<WindowsElement> driver = WinAppDriver.LAWSON_CLIENT.createDriver();

        WinAppDriverManagement.setDriver(driver);

        System.out.println("Driver created successfully!");

    }
    public static WebElement getLocatedElement(String elementAutoID) {
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
}
