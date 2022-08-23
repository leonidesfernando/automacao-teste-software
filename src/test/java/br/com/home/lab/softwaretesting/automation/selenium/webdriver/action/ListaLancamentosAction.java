package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.components.GridUI;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.ListaLancamentosPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumUtil.waitForElementVisible;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Slf4j
public class ListaLancamentosAction extends BaseAction<ListaLancamentosPage> {

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";

    enum Botao{
        EDITAR,
        EXCLUIR
    }

    public ListaLancamentosAction(WebDriver webDriver) {
        super(webDriver, new ListaLancamentosPage(webDriver));
    }

    public LancamentoAction novoLancamento(){
        page.get();
        page.getNewEntry().click();
        return new LancamentoAction(webDriver);
    }

    public LancamentoAction abreLancamentoParaEdicao(){
        page.get();
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

    private void clicaBotao(Botao btn){
        GridUI gridUI = page.getGrid();
        gridUI.getButtonsAt(0, 5).get(btn.ordinal()).click();
    }

    protected LancamentoAction clicaBotaoEditar(){
        clicaBotao(Botao.EDITAR);
        return new LancamentoAction(webDriver);
    }

    protected void clicaBotaoExcluir(){
        clicaBotao(Botao.EXCLUIR);
    }

    public void buscaLancamentoPorDescricao(String descricaoLancamento){
        page.get();
        waitForElementVisible(webDriver, page.getSearchItem()).clear();
        waitForElementVisible(webDriver, page.getSearchItem()).sendKeys(descricaoLancamento);
        waitForElementVisible(webDriver, page.getBtnSearch()).click();
    }

    public void gotToDashboard(){
        page.getBtnDashboard().click();
    }

    @Override
    public ListaLancamentosPage getPage() {
        return page;
    }
}
