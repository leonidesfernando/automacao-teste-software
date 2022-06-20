package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components.GridUI;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class ListaLancamentosAction extends BaseAction<ListaLancamentosPage> {

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";

    private final static String LIST_TABLE_ID = "divTabelaLancamentos";


    public ListaLancamentosAction(WebDriver webDriver) {
        super(webDriver, new ListaLancamentosPage(webDriver));
    }


    public LancamentoAction novoLancamento(){
        aguardarPagina();
        webDriver.findElement(By.id("novoLancamento")).click();
        return new LancamentoAction(webDriver);
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    String date, TipoLancamento tipo){

        aguardarPagina();
        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = new GridUI(webDriver).id("tabelaLancamentos");
        assertEquals(grid.getElements().size(), 1);
        assertEquals(grid.getCellValueAt(0, COL_DESCRIPTION), descricaoLancamento);
        assertEquals(grid.getCellValueAt(0, COL_RELEASE_DATE), date);
        assertEquals(grid.getCellValueAt(0, COL_TYPE), tipo.getDescricao());
        return true;
    }

    public void buscaLancamentoPorDescricao(String descricaoLancamento){
        page.getSearchItem().sendKeys(descricaoLancamento);
        page.getBtnSearch().click();
        aguardarPagina();
    }

    protected void aguardarPagina(){
        SeleniumUtil.waitForPresentOfIdWithRetries(webDriver, LIST_TABLE_ID, 5);
    }

    @Override
    public ListaLancamentosPage getPage() {
        return page;
    }
}
