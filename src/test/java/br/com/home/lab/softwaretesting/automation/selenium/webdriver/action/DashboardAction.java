package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.DashboardPage;
import org.openqa.selenium.WebDriver;

public class DashboardAction extends BaseAction<DashboardPage> {

    public DashboardAction(WebDriver webDriver) {
        super(webDriver, new DashboardPage(webDriver));
    }

    @Override
    public DashboardPage getPage() {
        return page;
    }

    public void goToList(){
        page.getBtnList().click();
    }
}
