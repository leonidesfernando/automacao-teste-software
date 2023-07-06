package br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.function.BiFunction;


public class SeleniumUtil {

    private static final Logger logger = LogManager.getLogger(SeleniumUtil.class);

    private static final int DEFAULT_TIMEOUT_SECONDS = 60;
    private static final int DEFAULT_POLLING_SECONDS = 2;

    public static Wait<WebDriver> fluentWait(WebDriver driver){
        return fluentWait(driver,
                DEFAULT_TIMEOUT_SECONDS,
                DEFAULT_POLLING_SECONDS);
    }

    public Wait<WebDriver> fluentWait(WebDriver driver, int seconds) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofSeconds(DEFAULT_POLLING_SECONDS))
                .ignoring(StaleElementReferenceException.class);
    }

    public static Wait<WebDriver> fluentWait(WebDriver driver, int timeout, int polling) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(StaleElementReferenceException.class);
    }

    public static void waitForPageLoad(WebDriver driver){
        waitAjaxCompleted(driver);
    }

    public static void waitAjaxCompleted(WebDriver driver){
        /*fluentWait(driver)
                .until(
                        d -> ((JavascriptExecutor)driver)
                                .executeScript("return document.readyState")
                                .equals("complete"));*/
        fluentWait(driver)
                .until(dr -> String
                        .valueOf(((JavascriptExecutor) dr).executeScript(
                                "return document.readyState")
                        )
                        .equals("complete"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
    }

    public static WebElement waitForPresenceOfId(WebDriver driver, String id) {
        return waitForPresenceBy(driver, By.id(id));
    }

    public static WebElement waitForPresenceOfSelector(WebDriver driver, String selector) {
        return waitForPresenceBy(driver, By.cssSelector(selector));
    }

    public static WebElement waitForPresenceOfXpath(WebDriver driver, String xpath) {
        return waitForPresenceBy(driver, By.xpath(xpath));
    }

    public static void waitForPresentOfIdWithRetries(WebDriver driver, String id, int retries) {
        waitForWithRetries(SeleniumUtil::waitForPresenceOfId, driver, id, retries);
    }

    public void waifForPresenceOfXpathWithRetries(WebDriver driver, String xpath, int retries) {
        waitForWithRetries(SeleniumUtil::waitForPresenceOfXpath, driver, xpath, retries);
    }

    private static WebElement waitForPresenceBy(WebDriver driver, By by){
        return fluentWait(driver)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static WebElement waitForElementVisible(WebDriver driver, WebElement element){
        return fluentWait(driver)
                .until(ExpectedConditions.visibilityOf(element));
    }

    private static <WD, T> WebElement waitForWithRetries(BiFunction<WD, T, WebElement> function,
                                                         WD driver, T elementIdentifier, int retries){
        var counter = 0;
        do{
            try{
                return function.apply(driver, elementIdentifier);
            }catch (Exception e){
                waitSomeTime((WebDriver) driver);
                counter++;
                logger.info("Error to wait for element by id: {} at {} retry",
                        elementIdentifier.toString(), counter);
            }
        }while (counter < retries);
        String errorMessage = String.format("Was not possible wait for element by id %s after %d retries",
                elementIdentifier, retries);
        logger.error(errorMessage);
        throw new IllegalStateException(errorMessage);
    }

    private static void waitSomeTime(WebDriver driver){
        waitSomeTime(driver,800);
    }

    private static void waitSomeTime(WebDriver driver, int miliseconds){
        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofMillis(miliseconds));
    }
}
