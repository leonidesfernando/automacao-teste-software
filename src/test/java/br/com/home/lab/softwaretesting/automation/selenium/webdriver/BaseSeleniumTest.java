package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.HomeAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.LoginAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.util.Strings;

import java.util.Objects;


public abstract class BaseSeleniumTest {

    private static final String BROWSER = "browser";
    private static final Configurations config = ConfigFactory.create(Configurations.class);

    protected final static ScenarioContextData context = new ScenarioContextData();

    private User loggedUser;
    protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();


    public BaseSeleniumTest() {
        loggedUser = LoadConfigurationUtil.getUser();
    }

    public WebDriver getWebDriver() {
        WebDriver driver = webDriver.get();
        Objects.requireNonNull(driver);
        return driver;
    }

    @BeforeSuite
    protected void beforeSuite() {
    }

    public void login() {
        login(loggedUser);
    }

    public void login(User user){
        loggedUser = user;
        access();
        doLogin();
    }

    protected void doLogin() {
        LoginAction loginAction = new LoginAction(getWebDriver());
        loginAction.doLogin(loggedUser);
    }

    public void doLogout() {
        HomeAction homeAction = new HomeAction(getWebDriver());
        homeAction.doLogout();
    }


    protected void access() {
        getWebDriver().get(LoadConfigurationUtil.getUrl());
    }

    @AfterClass
    protected void finaliza() {
        try {
            getWebDriver().quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    protected void init() {
        WebDriver driver = loadWebDriver();
        Objects.requireNonNull(driver);
        webDriver.set(driver);
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
}
