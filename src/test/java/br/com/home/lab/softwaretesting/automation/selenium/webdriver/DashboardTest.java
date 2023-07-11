package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.DashboardAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Regression Tests Epic")
@Feature("Navigating through the Dashboard")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DashboardTest extends BaseSeleniumTest {

    ListaLancamentosAction listaLancamentosAction;
    DashboardAction dashboardAction;


    public DashboardTest() {
        super();
    }


    @Test
    @Order(1)
    @Description("With a valid credentials user must be able to log into the system")
    void loginLancamentos() {
        assertTrue(super.login());
    }

    @Test
    @Order(2)
    @Description("After performing the login the user goes to the expense and income entries list, so he can access the button to go to the Dashboard")
    void accessDashboard() {

        listaLancamentosAction = new ListaLancamentosAction(getWebDriver());
        listaLancamentosAction.gotToDashboard();
    }

    @Test
    @Order(3)
    @Description("A logged user after access the Dashboard page by the button, must be able to return to the expense and income entries list page.")
    void backToList() {
        dashboardAction = new DashboardAction(getWebDriver());
        dashboardAction.goToList();
    }

    @Test
    @Order(4)
    @Description("The authenticated user must be able to log out the system")
    void logout() {
        super.doLogout();
    }
}
