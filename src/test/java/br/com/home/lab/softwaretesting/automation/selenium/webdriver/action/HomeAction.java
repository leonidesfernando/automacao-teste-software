package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomeAction extends BaseAction<HomePage> {

    public HomeAction(WebDriver webDriver) {
        super(webDriver, new HomePage(webDriver));
    }

    @Override
    public HomePage getPage() {
        return page;
    }

    public void doLogout() {
        page.getLogoutLink().click();
        Assert.assertTrue(getWebDriver().getCurrentUrl().contains("login"));
    }
}
