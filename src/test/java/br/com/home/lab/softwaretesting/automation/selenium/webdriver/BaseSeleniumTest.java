package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import java.util.Objects;


public abstract class BaseSeleniumTest {

    private static final String BROWSER = "browser";

    protected WebDriver webDriver;

    private static final Configurations config = ConfigFactory.create(Configurations.class);

    @BeforeSuite
    protected void beforeSuite(ITestContext context){
    }

    @Test(priority = -1)
    public void access(){
        webDriver.get(config.url());
    }

    @AfterClass
    protected void finaliza(){
        try{
            webDriver.quit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @BeforeClass
    protected void init(ITestContext context){
        webDriver = loadWebDriver();
    }

    protected WebDriver loadWebDriver(){
        return getBrowser().loadBrowser();
    }
    
    private Browser getBrowser(){
        String browser = System.getProperty(BROWSER);
        if(Strings.isNotNullAndNotEmpty(browser)) {
            return Browser.valueOf(browser.toUpperCase());
        }
        return config.browser();
    }

    @SuppressWarnings("unchecked")
    protected <T> T getContextAttribute(String key, ITestContext context){
        T attribute = (T) context.getAttribute(key);
        Objects.requireNonNull(attribute);
        return attribute;
    }
}
