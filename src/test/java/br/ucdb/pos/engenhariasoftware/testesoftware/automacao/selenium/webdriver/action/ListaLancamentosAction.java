package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components.GridUI;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil.waitForElementVisible;

@Slf4j
public class ListaLancamentosAction extends BaseAction<ListaLancamentosPage> {

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";


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

    public boolean existeLancamento(final String descricaoLancamento, String date, TipoLancamento tipo){

        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertEquals(grid.getElements().size(), 1);
        assertEquals(grid.getCellValueAt(0, COL_DESCRIPTION), descricaoLancamento);
        assertEquals(grid.getCellValueAt(0, COL_RELEASE_DATE), date);
        assertEquals(grid.getCellValueAt(0, COL_TYPE), tipo.getDescricao());
        return true;
    }

    public void removeLancamento(final String descricaoLancamento){
        buscaLancamentoPorDescricao(descricaoLancamento);
        clicaBotaoExcluir();
        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertFalse(grid.areThereElements());
    }

    public boolean existeLancamentoPorDescricao(String descricaoLancamento){
        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertEquals(grid.getElements().size(), 1);
        return grid.getCellValueAt(0, COL_DESCRIPTION).equals(descricaoLancamento);
    }

    private void clicaBotao(int index){
        GridUI gridUI = page.getGrid();
        gridUI.getButtonsAt(0, 5).get(index).click();
    }

    protected LancamentoAction clicaBotaoEditar(){
        clicaBotao(0);
        return new LancamentoAction(webDriver);
    }

    protected ListaLancamentosAction clicaBotaoExcluir(){
        clicaBotao(1);
        return this;
    }

    public ListaLancamentosAction buscaLancamentoPorDescricao(String descricaoLancamento){
        try {
            waitForElementVisible(webDriver, page.getSearchItem()).clear();
            waitForElementVisible(webDriver, page.getSearchItem()).sendKeys(descricaoLancamento);
            waitForElementVisible(webDriver, page.getBtnSearch()).click();
        }catch (StaleElementReferenceException e){
            log.info("Trying to search again after get a StaleElementReferenceException");
            waitForElementVisible(webDriver, page.getSearchItem()).clear();
            waitForElementVisible(webDriver, page.getSearchItem()).sendKeys(descricaoLancamento);
            waitForElementVisible(webDriver, page.getBtnSearch()).click();
        }
        return this;
    }


    @Override
    public ListaLancamentosPage getPage() {
        return page;
    }
}
