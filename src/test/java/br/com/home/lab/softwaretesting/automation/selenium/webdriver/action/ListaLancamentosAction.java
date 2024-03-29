package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.components.GridUI;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumUtil;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.ListaLancamentosPage;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumUtil.waitForElementInvisible;
import static br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumUtil.waitForElementVisible;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ListaLancamentosAction extends BaseAction<ListaLancamentosPage> {

    private static final String COL_DESCRIPTION = "Descrição";
    private static final String COL_RELEASE_DATE = "Data Lançamento";
    private static final String COL_TYPE = "Tipo";

    enum Botao {
        EDITAR,
        EXCLUIR
    }

    public ListaLancamentosAction(WebDriver webDriver) {
        super(webDriver, new ListaLancamentosPage(webDriver));
    }

    @Step("Going to the home page")
    public ListaLancamentosAction goHome() {
        page.get();
        page.getHomeLink().click();
        SeleniumUtil.waitAjaxCompleted(getWebDriver());
        SeleniumUtil.waitForElementVisible(getWebDriver(), page.getHomeLink());
        return this;
    }

    @Step("Clicking on the button to create a new entry")
    public LancamentoAction novoLancamento() {
        page.get();
        page.getNewEntry().click();
        return new LancamentoAction(getWebDriver());
    }

    @Step("Opening the desired entry for edition")
    public LancamentoAction abreLancamentoParaEdicao() {
        page.get();
        return clicaBotaoEditar();
    }

    @Step("Searching by: ${texto}")
    public void buscaPor(String texto) {
        buscaLancamentoPorDescricao(texto);
        GridUI grid = page.getGrid();
        assertFalse(grid.getElements().isEmpty());
    }

    public boolean existeLancamento(final String descricaoLancamento, String date, TipoLancamento tipo) {

        buscaLancamentoPorDescricao(descricaoLancamento);
        checkEntryExists(descricaoLancamento, date, tipo);
        return true;
    }

    @Step("Checking if the entry with description: '${descricaoLancamento}', date: '${date}' and type: '${tipo}' exists")
    public void checkEntryExists(String descricaoLancamento, String date, TipoLancamento tipo) {
        GridUI grid = page.getGrid();
        assertEquals(1, grid.getElements().size());
        final var gridDescription = grid.getCellValueAt(0, COL_DESCRIPTION);
        assertTrue(gridDescription.contains(descricaoLancamento) || gridDescription.equals(descricaoLancamento));
        assertEquals(grid.getCellValueAt(0, COL_RELEASE_DATE), date);
        assertEquals(grid.getCellValueAt(0, COL_TYPE), tipo.getDescricao());

    }

    @Step("Removing the entry with the description ${descricaoLancamento}")
    public void removeLancamento(final String descricaoLancamento) {
        buscaLancamentoPorDescricao(descricaoLancamento);
        clicaBotaoExcluir();
        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertFalse(grid.areThereElements());
    }

    @Step("Checking if the description '${descricaoLancamento}' exists")
    public boolean existeLancamentoPorDescricao(String descricaoLancamento){
        buscaLancamentoPorDescricao(descricaoLancamento);
        GridUI grid = page.getGrid();
        assertEquals(1, grid.getElements().size());
        return grid.getCellValueAt(0, COL_DESCRIPTION).equals(descricaoLancamento);
    }

    private void clicaBotao(Botao btn){
        GridUI gridUI = page.getGrid();
        gridUI.getButtonsAt(0, 5).get(btn.ordinal()).click();
    }

    @Step("Clicking to edit the desired entry")
    protected LancamentoAction clicaBotaoEditar(){
        clicaBotao(Botao.EDITAR);
        return new LancamentoAction(getWebDriver());
    }

    @Step("Clicking to remove a desired entry")
    protected void clicaBotaoExcluir() {
        clicaBotao(Botao.EXCLUIR);
    }

    @Step("Searching the entry by description")
    public void buscaLancamentoPorDescricao(String descricaoLancamento) {
        page.get();
        waitForElementVisible(getWebDriver(), page.getSearchItem()).clear();
        waitForElementVisible(getWebDriver(), page.getSearchItem()).sendKeys(descricaoLancamento);
        waitForElementVisible(getWebDriver(), page.getBtnSearch()).click();
    }

    @Step("Searching entry by description with pagination (keeping the pagination of the item searched)")
    public void buscaLancamentoPorPaginaDescricao(String descricaoLancamento) {
        page.get();
        waitForElementVisible(getWebDriver(), page.getSearchItem()).clear();
        waitForElementVisible(getWebDriver(), page.getSearchItem()).sendKeys(descricaoLancamento);
        waitForElementVisible(getWebDriver(), page.getFirstPaginationLink()).click();
    }

    @Step("Accessing the Dashboard page by Listing entries page using the button")
    public void gotToDashboard() {
        page.getBtnDashboard().click();
    }


    @Step("Removing all entries, opening the modal, confirming and managing AJAX")
    public boolean removingAllEntries() {
        page.get();
        page.getBtnRemoveAll().click();
        waitForElementVisible(getWebDriver(), page.getModalRemoveAll());
        page.getBtnYesRemoveAll().click();
        waitForElementInvisible(getWebDriver(), page.getModalRemoveAll());
        return checkListingIsEmpty();
    }

    @Step("Checking if the listing is empty")
    public boolean checkListingIsEmpty() {
        GridUI grid = page.getGrid();
        assertFalse(grid.areThereElements());
        return true;
    }

    @Override
    public ListaLancamentosPage getPage() {
        return page;
    }
}
