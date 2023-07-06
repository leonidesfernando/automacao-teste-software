package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.HomeAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.LoginAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.config.ScreenshotListener;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseSeleniumTest {

    @RegisterExtension
    ScreenshotListener screenshotListener = new ScreenshotListener();
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

    @BeforeAll
    protected void beforeSuite() {
    }

    public void login() {
        assertTrue(login(loggedUser));
    }

    public boolean login(User user) {
        loggedUser = user;
        access();
        return doLogin();
    }

    protected boolean doLogin() {
        LoginAction loginAction = new LoginAction(getWebDriver());
        return loginAction.doLogin(loggedUser);
    }

    public void doLogout() {
        HomeAction homeAction = new HomeAction(getWebDriver());
        homeAction.doLogout();
    }


    protected void access() {
        getWebDriver().get(LoadConfigurationUtil.getUrl());
    }

    @AfterAll
    protected void finaliza() {
        try {
            getWebDriver().quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    protected void init(){
        WebDriver driver = loadWebDriver();
        Objects.requireNonNull(driver);
        webDriver.set(driver);
        screenshotListener.init(driver);
    }

    protected WebDriver loadWebDriver(){
        return getBrowser().loadBrowser();
    }
    
    private Browser getBrowser(){
        String browser = System.getProperty(BROWSER);
        if (StringUtils.isNotBlank(browser)) {
            return Browser.valueOf(browser.toUpperCase());
        }
        return config.browser();
    }
}
