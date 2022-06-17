package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

@UtilityClass
public class SeleniumUtil {

    private static final int DEFAULT_TIMEOUT_SECONDS = 60;
    private static final int DEFAULT_POLLING_SECONDS = 2;

    public Wait<WebDriver> fluentWait(WebDriver driver){
        return fluentWait(driver,
                DEFAULT_TIMEOUT_SECONDS,
                DEFAULT_POLLING_SECONDS);
    }

    public Wait<WebDriver> fluentWait(WebDriver driver, int timeout) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(DEFAULT_POLLING_SECONDS))
                .ignoring(StaleElementReferenceException.class);
    }

    public Wait<WebDriver> fluentWait(WebDriver driver, int timeout, int polling) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(StaleElementReferenceException.class);
    }

    public WebElement waitForPresenceOfId(WebDriver driver, String id){
        return waitForPresenceBy(driver, By.id(id));
    }

    private WebElement waitForPresenceBy(WebDriver driver, By by){
        return fluentWait(driver)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
