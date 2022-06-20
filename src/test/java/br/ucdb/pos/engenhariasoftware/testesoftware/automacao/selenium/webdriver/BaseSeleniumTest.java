package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.config.Configurations;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public abstract class BaseSeleniumTest {

    private static final String BROWSER = "browser";

    protected WebDriver webDriver;

    private static Configurations config = ConfigFactory.create(Configurations.class);

    @BeforeSuite
    protected void beforeSuite(ITestContext context){
    }

    @Test(priority = -1)
    public void access(){
        webDriver.get(config.url());
        SeleniumUtil.fluentWait(webDriver, 120)
                .until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
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
}
