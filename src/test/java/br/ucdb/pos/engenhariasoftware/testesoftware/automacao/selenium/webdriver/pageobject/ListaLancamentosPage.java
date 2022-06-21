package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components.GridUI;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertEquals;

@Getter
public class ListaLancamentosPage extends BasePage {

    @FindBy(id = "novoLancamento")
    private WebElement newEntry;

    @FindBy(id = "itemBusca")
    private WebElement searchItem;

    @FindBy(id = "bth-search")
    private WebElement btnSearch;

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";

    private final static String LIST_TABLE_ID = "divTabelaLancamentos";

    public ListaLancamentosPage(final WebDriver driver){
        super(driver);
    }

    public ListaLancamentosPage acessa(){
        webDriver.get("http://localhost:8080/lancamentos/");
        return this;
    }

    public LancamentoPage novoLancamento(){
        aguardarPagina();
        webDriver.findElement(By.id("novoLancamento")).click();
        return new LancamentoPage(webDriver);
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    LocalDateTime dataHora, TipoLancamento tipo){

        aguardarPagina();
        buscaLancamentoPorDescricao(descricaoLancamento);
        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        GridUI grid = new GridUI(webDriver).id("tabelaLancamentos");
        assertEquals(grid.getElements().size(), 1);
        assertEquals(grid.getCellValueAt(0, COL_DESCRIPTION), descricaoLancamento);
        assertEquals(grid.getCellValueAt(0, COL_RELEASE_DATE), dataHora.format(formatoDataLancamento));
        assertEquals(grid.getCellValueAt(0, COL_TYPE), tipo.getDescricao());
        return true;
    }

    public void buscaLancamentoPorDescricao(String descricaoLancamento){
        searchItem.sendKeys(descricaoLancamento);
        btnSearch.click();
        aguardarPagina();
    }

    protected void aguardarPagina(){
        SeleniumUtil.waitForPresenceOfId(webDriver, LIST_TABLE_ID);
    }
}

