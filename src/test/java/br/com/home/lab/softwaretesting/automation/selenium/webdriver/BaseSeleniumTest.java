package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.HomeAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.LoginAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseSeleniumTest {

    private static final String BROWSER = "browser";
    private static final Configurations config = ConfigFactory.create(Configurations.class);

    protected WebDriver webDriver;

    private User loggedUser;

    protected final ScenarioContextData context = new ScenarioContextData();


    public BaseSeleniumTest() {
        loggedUser = LoadConfigurationUtil.getUser();
    }

    @BeforeAll
    protected void beforeSuite() {
    }

    @Test
    @Order(1)
    public void login() {
        assertTrue(login(loggedUser));
    }

    public boolean login(User user) {
        loggedUser = user;
        access();
        return doLogin();
    }

    protected boolean doLogin() {
        LoginAction loginAction = new LoginAction(webDriver);
        return loginAction.doLogin(loggedUser);
    }

    public void doLogout() {
        HomeAction homeAction = new HomeAction(webDriver);
        homeAction.doLogout();
    }


    protected void access() {
        webDriver.get(LoadConfigurationUtil.getUrl());
    }

    @AfterAll
    protected void finaliza() {
        try {
            webDriver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    protected void init() {
        webDriver = loadWebDriver();
    }

    protected WebDriver loadWebDriver() {
        return getBrowser().loadBrowser();
    }

    private Browser getBrowser() {
        String browser = System.getProperty(BROWSER);
        if (StringUtils.isNotBlank(browser)) {
            return Browser.valueOf(browser.toUpperCase());
        }
        return config.browser();
    }
}
