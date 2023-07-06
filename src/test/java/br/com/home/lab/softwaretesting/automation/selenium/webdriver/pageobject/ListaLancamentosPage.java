package br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.components.GridUI;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumUtil;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ListaLancamentosPage extends BasePage {

    @FindBy(id = "novoLancamento")
    private WebElement newEntry;

    @FindBy(id = "itemBusca")
    private WebElement searchItem;

    @FindBy(id = "bth-search")
    private WebElement btnSearch;

    @FindBy(css = "a[title='Gráfico']")
    private WebElement btnDashboard;

    @FindBy(id = "pagina1")
    private WebElement firstPaginationLink;

    @FindBy(id = "homeLink")
    private WebElement homeLink;

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";

    private final static String LIST_TABLE_ID = "divTabelaLancamentos";

    public ListaLancamentosPage(final WebDriver driver) {
        super(driver);
    }

    public GridUI getGrid(){
        return new GridUI(getWebDriver()).id("tabelaLancamentos");
    }

    @Override
    protected boolean isReady() {
        return SeleniumUtil.waitForElementVisible(getWebDriver(), btnSearch).isEnabled()
                && homeLink.isDisplayed() && homeLink.isEnabled();
    }
}
