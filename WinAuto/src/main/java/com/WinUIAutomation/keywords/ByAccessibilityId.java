package com.WinUIAutomation.keywords;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;



public class ByAccessibilityId extends By {

    private final String accessibilityId;

    public ByAccessibilityId(String accessibilityId) {
        this.accessibilityId = accessibilityId;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        if (context instanceof WindowsDriver) {
            WindowsDriver<WindowsElement> driver = (WindowsDriver<WindowsElement>) context;
            return (List<WebElement>)(List<?>)driver.findElements(By.xpath("//*[@AutomationId='" + accessibilityId + "']"));
        } else {
            throw new IllegalArgumentException("SearchContext must be an instance of WindowsDriver");
        }
    }
    @Override
    public String toString() {
        return "By.accessibilityId: " + accessibilityId;
    }
    public static By accessibilityId(String accessibilityId) {
        return new ByAccessibilityId(accessibilityId);
    }
}
