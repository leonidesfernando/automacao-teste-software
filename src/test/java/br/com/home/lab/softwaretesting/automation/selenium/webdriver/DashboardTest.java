package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.DashboardAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("Regression Tests Epic")
@Feature("Navigating through the Dashboard")
public class DashboardTest extends BaseSeleniumTest {

    ListaLancamentosAction listaLancamentosAction;
    DashboardAction dashboardAction;

    public DashboardTest() {
        super();
    }

    @Description("With a valid credentials user must be able to log into the system")
    @Test
    public void loginLancamentos() {
        super.login();
    }

    @Description("After performing the login the user goes to the expense and income entries list, so he can access the button to go to the Dashboard")
    @Test(dependsOnMethods = "loginLancamentos")
    public void accessDashboard() {

        listaLancamentosAction = new ListaLancamentosAction(getWebDriver());
        listaLancamentosAction.gotToDashboard();
    }

    @Description("A logged user after access the Dashboard page by the button, must be able to return to the expense and income entries list page.")
    @Test(dependsOnMethods = "accessDashboard")
    public void backToList() {
        dashboardAction = new DashboardAction(getWebDriver());
        dashboardAction.goToList();
    }

    @Description("The authenticated user must be able to log out the system")
    @Test(dependsOnMethods = "backToList")
    public void logout() {
        super.doLogout();
    }
}
