package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.config.Configurations;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.experimental.UtilityClass;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

@UtilityClass
public class SeleniumBootstrap {

    private static final String HEADLESS = "headless";
    private final static Configurations config = ConfigFactory.create(Configurations.class);

    private boolean isHeadlessMode() {
        if (System.getProperty(HEADLESS) == null) {
            return config.headless();
        }
        return Boolean.parseBoolean(System.getProperty(HEADLESS));
    }


    public WebDriver setupExistingBrowser(){
        try {
            return setupFirefox();
        }catch (Exception e){
            return setupChrome();
        }
    }

    public WebDriver setupChrome(){
        WebDriverManager driverManager = WebDriverManager.chromedriver();
        driverManager.setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("disable-extensions");
        chromeOptions.addArguments("--no-sandbox");

        chromeOptions.setHeadless(isHeadlessMode());

        return new ChromeDriver(chromeOptions);
    }

    public WebDriver setupEdge(){
        throw new UnsupportedOperationException();
    }

    public WebDriver setupFirefox(){
        WebDriverManager driverManager = WebDriverManager.firefoxdriver();
        driverManager.setup();

        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
        firefoxProfile.setPreference("browser.download.manager.scanWhenDone", false);
        firefoxProfile.setPreference("browser.download.importedFromSqlite", false);
        firefoxProfile.setPreference("browser.download.panel.shown", false);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
        firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
        firefoxProfile.setPreference("browser.download.manager.useWindow", false);
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);

        FirefoxOptions options = new FirefoxOptions();
        if(isHeadlessMode()){
            options.addArguments("--headless");
        }

        WebDriver driver =  new FirefoxDriver(options);
        driver.manage().window().maximize();
        return driver;
    }
}
