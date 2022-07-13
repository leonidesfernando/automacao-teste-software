package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.BasePage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

public abstract class BaseAction<T extends BasePage> {

    @Getter
    protected WebDriver webDriver;

    protected T page;

    public BaseAction(WebDriver webDriver, T page){
        this.webDriver = webDriver;
        this.page = page;
        this.page.get();
    }

    abstract public T getPage();
}
