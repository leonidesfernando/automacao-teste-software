package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.config.Configurations;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;


public abstract class BaseSeleniumTest {

    protected WebDriver webDriver;

    private static Configurations config = ConfigFactory.create(Configurations.class);

    @BeforeSuite
    protected void beforeSuite(ITestContext context){
    }


    @BeforeClass
    public void init(ITestContext context){
        webDriver = loadWebDriver();
    }

    protected WebDriver loadWebDriver(){
        return config.browser().loadBrowser() ;
    }
}
