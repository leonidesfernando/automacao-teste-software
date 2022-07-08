package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.DashboardPage;
import org.openqa.selenium.WebDriver;

public class DashboardAction extends BaseAction<DashboardPage> {

    public DashboardAction(WebDriver webDriver) {
        super(webDriver, new DashboardPage(webDriver));
    }

    @Override
    public DashboardPage getPage() {
        return page;
    }

    public ListaLancamentosAction goToList(){
        page.getBtnList().click();
        return new ListaLancamentosAction(getWebDriver());
    }
}
