package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.config.Configurations;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


@AllArgsConstructor
public abstract class BaseSeleniumTest {

    protected WebDriver webDriver;

    private static Configurations config = ConfigFactory.create(Configurations.class);

    @BeforeSuite
    protected void beforeSuite(ITestContext context){
    }

    @Test(priority = 0)
    public void access(){
        webDriver.get(config.url());
    }
    @AfterClass
    @SneakyThrows
    protected void finaliza(){
        webDriver.quit();
    }

    @BeforeClass
    public void init(ITestContext context){
        webDriver = loadWebDriver();
    }

    protected WebDriver loadWebDriver(){
        return config.browser().loadBrowser() ;
    }
}
