package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.config.Configurations;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

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
    @SneakyThrows
    protected void finaliza(){
        webDriver.quit();
    }

    @BeforeClass
    protected void init(ITestContext context){
        webDriver = loadWebDriver();
    }

    protected WebDriver loadWebDriver(){
        if (System.getProperty(BROWSER) == null) {
            return config.browser().loadBrowser();
        }
        return Browser.valueOf(System.getProperty(BROWSER)).loadBrowser();
    }

    @SuppressWarnings("unchecked")
    protected <T> T getContextAttribute(String key, ITestContext context){
        T attribute = (T) context.getAttribute(key);
        Objects.requireNonNull(attribute);
        return attribute;
    }
}
