package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.DashboardPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class DashboardAction extends BaseAction<DashboardPage> {

    public DashboardAction(WebDriver webDriver) {
        super(webDriver, new DashboardPage(webDriver));
    }

    @Override
    public DashboardPage getPage() {
        return page;
    }

    @Step("On the dashboard page backing to the listing entries page")
    public ListaLancamentosAction goToList(){
        page.getBtnList().click();
        return new ListaLancamentosAction(getWebDriver());
    }
}
