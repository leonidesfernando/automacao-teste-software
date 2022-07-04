package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components.GridUI;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

@Getter
public class ListaLancamentosPage extends BasePage {

    @CacheLookup @FindBy(id = "novoLancamento")
    private WebElement newEntry;

    @CacheLookup @FindBy(id = "itemBusca")
    private WebElement searchItem;

    @CacheLookup @FindBy(id = "bth-search")
    private WebElement btnSearch;

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";

    private final static String LIST_TABLE_ID = "divTabelaLancamentos";

    public ListaLancamentosPage(final WebDriver driver){
        super(driver);
    }

    public GridUI getGrid(){
        return new GridUI(webDriver).id("tabelaLancamentos");
    }

}

