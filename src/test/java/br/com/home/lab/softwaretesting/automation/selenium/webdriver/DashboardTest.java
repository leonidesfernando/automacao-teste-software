package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.DashboardAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import org.testng.annotations.Test;

public class DashboardTest extends BaseSeleniumTest {

    ListaLancamentosAction listaLancamentosAction;
    DashboardAction dashboardAction;
    @Test(dependsOnMethods = "access")
    public void accessDashboard(){

        listaLancamentosAction = new ListaLancamentosAction(webDriver);
        dashboardAction = listaLancamentosAction.gotToDashboard();
    }

    @Test(dependsOnMethods = "accessDashboard")
    public void backToList(){
        dashboardAction.goToList();
    }
}
