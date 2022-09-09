package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.LoginPage;
import org.openqa.selenium.WebDriver;

import static br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumUtil.waitForElementVisible;

public class LoginAction extends BaseAction<LoginPage> {

    private HomeAction homeAction;

    public LoginAction(WebDriver webDriver) {
        super(webDriver, new LoginPage(webDriver));
    }

    public boolean doLogin(User user) {
        page.submitUserCredentials(user);
        homeAction = new HomeAction(webDriver);
        return checkIfIsLoggedIn();
    }

    private boolean checkIfIsLoggedIn() {
        try {
            waitForElementVisible(webDriver, homeAction.getPage().getLogoutLink());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LoginPage getPage() {
        return page;
    }
}
