package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components.GridUI;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
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
        page.getNewEntry().click();
        return new LancamentoAction(webDriver);
    }

    public LancamentoAction abreLancamentoParaEdicao(){
        return clicaBotaoEditar();
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    String date, TipoLancamento tipo){

        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertEquals(grid.getElements().size(), 1);
        assertEquals(grid.getCellValueAt(0, COL_DESCRIPTION), descricaoLancamento);
        assertEquals(grid.getCellValueAt(0, COL_RELEASE_DATE), date);
        assertEquals(grid.getCellValueAt(0, COL_TYPE), tipo.getDescricao());
        return true;
    }

    public boolean existeLancamentoPorDescricao(String descricaoLancamento){
        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertEquals(grid.getElements().size(), 1);
        return grid.getCellValueAt(0, COL_DESCRIPTION).equals(descricaoLancamento);
    }

    private LancamentoAction clicaBotaoEditar(){
        GridUI gridUI = page.getGrid();
        gridUI.getButtonsAt(0, 5).get(0).click();
        return new LancamentoAction(webDriver);
    }

    public ListaLancamentosAction buscaLancamentoPorDescricao(String descricaoLancamento){
        page.getSearchItem().clear();
        page.getSearchItem().sendKeys(descricaoLancamento);
        page.getBtnSearch().click();
        return this;
    }


    @Override
    public ListaLancamentosPage getPage() {
        return page;
    }
}
