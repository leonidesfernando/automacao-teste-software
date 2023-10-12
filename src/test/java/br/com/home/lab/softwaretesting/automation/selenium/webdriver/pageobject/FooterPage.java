package br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class FooterPage extends BasePage{

    @Getter
    @CacheLookup
    @FindBy(css = "p[data-qa='footer-title']")
    private WebElement title;
    public FooterPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected boolean isReady() {
        return title.isEnabled() && title.isDisplayed();
    }
}
