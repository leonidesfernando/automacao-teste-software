package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.HomeAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.LoginAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
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
    private static final Configurations config = ConfigFactory.create(Configurations.class);

    protected WebDriver webDriver;

    private User loggedUser;


    public BaseSeleniumTest() {
        loggedUser = LoadConfigurationUtil.getUser();
    }

    @BeforeSuite
    protected void beforeSuite(ITestContext context) {
    }

    @Test(priority = -1)
    public void login() {
        login(loggedUser);
    }

    public void login(User user){
        loggedUser = user;
        access();
        doLogin();
    }

    protected void doLogin() {
        LoginAction loginAction = new LoginAction(webDriver);
        loginAction.doLogin(loggedUser);
    }

    public void doLogout() {
        HomeAction homeAction = new HomeAction(webDriver);
        homeAction.doLogout();
    }


    protected void access() {
        webDriver.get(LoadConfigurationUtil.getUrl());
    }

    @AfterClass
    protected void finaliza() {
        try {
            webDriver.quit();
        } catch (Exception e) {
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
