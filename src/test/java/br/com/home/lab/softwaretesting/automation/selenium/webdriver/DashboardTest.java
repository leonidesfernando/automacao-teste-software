package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.DashboardAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import org.testng.annotations.Test;

public class DashboardTest extends BaseSeleniumTest {

    ListaLancamentosAction listaLancamentosAction;
    DashboardAction dashboardAction;

    public DashboardTest(){
        super(new User("user", "a"));
    }

    @Test(dependsOnMethods = "login")
    public void accessDashboard(){

        listaLancamentosAction = new ListaLancamentosAction(webDriver);
        listaLancamentosAction.gotToDashboard();
    }

    @Test(dependsOnMethods = "accessDashboard")
    public void backToList(){
        dashboardAction = new DashboardAction(webDriver);
        dashboardAction.goToList();
    }
}
