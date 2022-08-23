package br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @Getter
    @FindBy(id = "logout")
    private WebElement logoutLink;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected boolean isReady() {
        return logoutLink.isEnabled();
    }
}
