package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public abstract class BaseAction<T extends BasePage> {

    protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    protected T page;

    public BaseAction(WebDriver webDriver, T page) {
        Objects.requireNonNull(webDriver);
        BaseAction.webDriver.set(webDriver);
        this.page = page;
        this.page.get();
    }

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    abstract public T getPage();
}
