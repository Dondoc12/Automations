package pageObject;

import com.WinUIAutomation.keywords.ByAccessibilityId;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class pageObject {
        public static By getUsername = ByAccessibilityId.accessibilityId("User");
        public static By getPassword = ByAccessibilityId.accessibilityId("Password");
        public static By getLoginbtn = ByAccessibilityId.accessibilityId("SignIn");
        public static By getSearchButton = ByAccessibilityId.accessibilityId("PART_SearchButton");
        public static By getSearchField = ByAccessibilityId.accessibilityId("PART_Field");
        public static By getPasswordPrompt = ByAccessibilityId.accessibilityId("PasswordPrompt");
        public static By getOKButton = ByAccessibilityId.accessibilityId("ButtonOK");
        public static By getDataset = ByAccessibilityId.accessibilityId("WASBDS");
        public static By getbudgVersion = ByAccessibilityId.accessibilityId("WABVER");
        public static By getYear = ByAccessibilityId.accessibilityId("WAYEA4");
        public static By getApplyButton = ByAccessibilityId.accessibilityId("XT_0168");
        public static By getQuality = ByAccessibilityId.accessibilityId("R23C4");
        public static By getCopyRight = ByAccessibilityId.accessibilityId("PART_CopyRight");

}
