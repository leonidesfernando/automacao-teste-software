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

    public void doLogin(User user){
        page.submitUserCredentials(user);
        homeAction = new HomeAction(webDriver);
        checkIfIsLoggedIn();
    }

    private void checkIfIsLoggedIn() {
        waitForElementVisible(webDriver, homeAction.getPage().getLogoutLink());
    }

    @Override
    public LoginPage getPage() {
        return page;
    }
}