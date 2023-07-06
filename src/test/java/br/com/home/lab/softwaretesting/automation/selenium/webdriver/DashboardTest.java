package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.DashboardAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DashboardTest extends BaseSeleniumTest {

    ListaLancamentosAction listaLancamentosAction;
    DashboardAction dashboardAction;

    public DashboardTest() {
        super();
    }

    @Test
    @Order(1)
    public void loginLancamentos() {
        super.login();
    }

    @Test
    public void accessDashboard(){

        listaLancamentosAction = new ListaLancamentosAction(getWebDriver());
        listaLancamentosAction.gotToDashboard();
    }

    @Test
    public void backToList() {
        dashboardAction = new DashboardAction(getWebDriver());
        dashboardAction.goToList();
    }

    @Test
    @Order(4)
    public void logout() {
        super.doLogout();
    }
}
