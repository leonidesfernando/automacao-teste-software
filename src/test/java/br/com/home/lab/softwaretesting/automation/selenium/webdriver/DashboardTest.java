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

/*
Ver as deps, talvez tenha conflito

        https://www.swtestacademy.com/allure-report-junit/
        https://www.swtestacademy.com/allure-report-testng/
        https://docs.qameta.io/allure/#_installing_a_commandline
        https://docs.qameta.io/allure/#_configuration
        https://javabydeveloper.com/junit-5-with-allure-reports-example/

        https://codoid.com/automation-testing/allure-report-tutorial/
        https://codoid.com/automation-testing/allure-report-vs-extent-report/
*/
@Epic("Regression Tests Epic")
@Feature("Navigating through the Dashboard")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DashboardTest extends BaseSeleniumTest {

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
    //@Story("An authenticated user on the Dashboard page must be able to go to the expense and income entries list page.")
    @Description("A logged user after access the Dashboard page by the button, must be able to return to the expense and income entries list page.")
    void backToList() {
        dashboardAction = new DashboardAction(getWebDriver());
        dashboardAction.goToList();
    }

    @Test
    @Order(4)
    //@Story("The link to log out the system must be shown to an authenticated user")
    @Description("The authenticated user must be able to log out the system")
    void logout() {
        super.doLogout();
    }
}
