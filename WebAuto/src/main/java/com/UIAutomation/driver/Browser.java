package com.UIAutomation.driver;

import com.UIAutomation.constants.Constants;
import com.UIAutomation.exceptions.HeadlessNotSupportedException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

public enum Browser {
    CHROME {
        @Override
        public WebDriver createDriver() {
            return new ChromeDriver(getOptions());
        }

        @Override
        public ChromeOptions getOptions() {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            String downloadFilePath = Constants.DOWNLOAD_FOLDER;
            prefs.put("download.default_directory", downloadFilePath);
            prefs.put("download.prompt_for_download", false);
            prefs.put("download.directory_upgrade", true);
            prefs.put("safebrowsing.enabled", true);


            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");

            options.setAcceptInsecureCerts(true);

            if (Boolean.valueOf(Constants.HEADLESS) == true) {
                options.addArguments("--headless=new");
                options.addArguments("window-size=1800,900");
            }

            return options;
        }
    }, FIREFOX {
        @Override
        public WebDriver createDriver() {
           return new FirefoxDriver(getOptions());
        }

        @Override
        public FirefoxOptions getOptions() {
            FirefoxOptions options = new FirefoxOptions();
            FirefoxProfile profile = new FirefoxProfile();

            // Cấu hình các tùy chọn tải xuống
            String downloadFilePath = Constants.DOWNLOAD_FOLDER;
            profile.setPreference("browser.download.dir", downloadFilePath);
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,application/octet-stream,application/zip,text/csv");
            profile.setPreference("pdfjs.disabled", true);

            options.setProfile(profile);
            options.setAcceptInsecureCerts(true);

            if (Boolean.valueOf(Constants.HEADLESS)) {
                options.addArguments("--headless");
                options.addArguments("window-size=1800,900");
            }

            return options;
        }
    }, EDGE {
        @Override
        public WebDriver createDriver() {
            return new EdgeDriver(getOptions());
        }

        @Override
        public EdgeOptions getOptions() {
            EdgeOptions options = new EdgeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            String downloadFilePath = Constants.DOWNLOAD_FOLDER;
            prefs.put("download.default_directory", downloadFilePath);
            prefs.put("download.prompt_for_download", false);
            prefs.put("download.directory_upgrade", true);
            prefs.put("safebrowsing.enabled", true);


            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");

            options.setAcceptInsecureCerts(true);

            if (Boolean.valueOf(Constants.HEADLESS) == true) {
                options.addArguments("--headless=new");
                options.addArguments("window-size=1800,900");
            }

            return options;
        }
    }, SAFARI {
        @Override
        public WebDriver createDriver() {
            return new SafariDriver(getOptions());
        }

        @Override
        public SafariOptions getOptions() {
            SafariOptions options = new SafariOptions();
            options.setAutomaticInspection(false);

            if (TRUE.equals(Boolean.valueOf(Constants.HEADLESS)))
                throw new HeadlessNotSupportedException(options.getBrowserName());

            return options;
        }
    };

    private static final String START_MAXIMIZED = "--start-maximized";

    public abstract WebDriver createDriver();

    public abstract MutableCapabilities getOptions();
}
