package pageObject;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class pageObject {
        public static WebElement getUsername(WindowsDriver driver) {
                return getLocatedElement(driver, "User");
        }

        public static WebElement getPassword(WindowsDriver driver) {
                return getLocatedElement(driver, "Password");
        }

        public static WebElement getLoginbtn(WindowsDriver driver) {
                return getLocatedElement(driver, "SignIn");
        }

        public static WebElement getSearchButton(WindowsDriver driver) {
                return getLocatedElement(driver, "PART_SearchButton");
        }

        public static WebElement getSearchField(WindowsDriver driver) {
                return getLocatedElement(driver, "PART_Field");
        }

        public static WebElement getPasswordPrompt(WindowsDriver driver) {
                return getLocatedElement(driver, "PasswordPrompt");
        }
        public static WebElement getOKButton(WindowsDriver driver){
                return getLocatedElement(driver,"ButtonOK");
        }
        public static WebElement getDataset(WindowsDriver driver){
                return getLocatedElement(driver,"WASBDS");
        }
        public static WebElement getbudgVersion(WindowsDriver driver){
                return getLocatedElement(driver,"WABVER");
        }
        public static WebElement getYear(WindowsDriver driver){
                return getLocatedElement(driver,"WAYEA4");
        }
        public static WebElement getApplyButton(WindowsDriver driver){
                return getLocatedElement(driver,"XT_0168");
        }
        public static WebElement getQuality(WindowsDriver driver){
                return getLocatedElement(driver,"R23C4");
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
        public static WebElement getLocatedByName(WindowsDriver driver, String name){
                try{
                        WebDriverWait wait = new WebDriverWait(driver,20);
                        return wait.until(ExpectedConditions.visibilityOf(driver.findElementByName(name)));
                }
                catch (Exception e){
                        return null;
                }
        }

}
