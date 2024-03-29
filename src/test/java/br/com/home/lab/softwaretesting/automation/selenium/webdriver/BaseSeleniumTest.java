package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.HomeAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.LoginAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.config.ScreenshotListener;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

import java.util.Objects;
import java.util.concurrent.Semaphore;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseSeleniumTest {

    private final Semaphore semaphore = new Semaphore(1);

    @RegisterExtension
    ScreenshotListener screenshotListener = new ScreenshotListener();
    private static final String BROWSER = "browser";
    private static final Configurations config = ConfigFactory.create(Configurations.class);

    protected final static ScenarioContextData context = new ScenarioContextData();

    private User loggedUser;
    protected static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();


    public BaseSeleniumTest() {
        loggedUser = LoadConfigurationUtil.getUser();
    }

    public synchronized static WebDriver getWebDriver() {
        return webDriver.get();
    }

    @BeforeAll
    protected void beforeSuite() {
    }

    public boolean login() {
        return login(loggedUser);
    }

    @Step("Performing the log in with user {user.username} and password ***")
    public boolean login(User user) {
        loggedUser = user;
        access();
        return doLogin();
    }

    protected boolean doLogin() {
        LoginAction loginAction = new LoginAction(getWebDriver());
        return loginAction.doLogin(loggedUser);
    }

    @Step("Performing logout the current user")
    public void doLogout() {
        HomeAction homeAction = new HomeAction(getWebDriver());
        homeAction.doLogout();
    }


    @Step("Asking the browser to access the URL loaded from LoadConfigurationUtil")
    protected void access() {
        getWebDriver().get(LoadConfigurationUtil.getUrl());
    }

    @AfterAll
    @Step("Closing the browser")
    protected void finaliza() {
        try {
            semaphore.acquire();
            WebDriver driver = getWebDriver();
            if (driver != null) {
                driver.quit();
            }
            webDriver.remove();
            semaphore.release();
            log.info("Driver and browser closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    @Step("Initializing browser and screenshot listener")
    protected void init() {
        try {
            semaphore.acquire();
            WebDriver driver = loadWebDriver();
            Objects.requireNonNull(driver);
            webDriver.set(ThreadGuard.protect(driver));
            screenshotListener.init(driver);
            semaphore.release();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    protected WebDriver loadWebDriver(){
        WebDriver driver = getBrowser().loadBrowser();
        closeBrowserWhenThreadEnds(driver);
        return driver;
    }

    private void closeBrowserWhenThreadEnds(WebDriver driver){
        Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
    }

    private Browser getBrowser(){
        String browser = System.getProperty(BROWSER);
        if (StringUtils.isNotBlank(browser)) {
            return Browser.valueOf(browser.toUpperCase());
        }
        return config.browser();
    }
}
