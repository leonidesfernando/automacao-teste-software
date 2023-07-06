package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.DashboardAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import org.testng.annotations.Test;

public class DashboardTest extends BaseSeleniumTest {

    ListaLancamentosAction listaLancamentosAction;
    DashboardAction dashboardAction;

    public DashboardTest() {
        super();
    }

    @Test
    public void loginLancamentos() {
        super.login();
    }

    @Test(dependsOnMethods = "loginLancamentos")
    public void accessDashboard() {

        listaLancamentosAction = new ListaLancamentosAction(getWebDriver());
        listaLancamentosAction.gotToDashboard();
    }

    @Test(dependsOnMethods = "accessDashboard")
    public void backToList() {
        dashboardAction = new DashboardAction(getWebDriver());
        dashboardAction.goToList();
    }

    @Test(dependsOnMethods = "backToList")
    public void logout() {
        super.doLogout();
    }
}
