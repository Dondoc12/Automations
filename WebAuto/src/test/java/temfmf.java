import com.UIAutomation.constants.Constants;
import com.UIAutomation.helpers.Helpers;
import com.UIAutomation.helpers.PropertiesHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.testng.annotations.Test;

public class temfmf {
    String a = Constants.BROWSER;
    @Test
    void end(){
        System.out.println(a);
        WebDriver driver = new ChromeDriver();

    }
}
