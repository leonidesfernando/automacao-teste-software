package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.LancamentoPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.math.BigDecimal;

public class LancamentoAction extends BaseAction<LancamentoPage> {


    public LancamentoAction(WebDriver webDriver) {
        super(webDriver, new LancamentoPage(webDriver));
    }

    @Override
    public LancamentoPage getPage() {
        return page;
    }

    protected void aguardarPagina() {
        SeleniumUtil.waitForPresenceOfId(webDriver,
                getPage().getBtnSalvar().getAttribute("id"));
    }

    public void cria(final String descricaoLancamento, final BigDecimal valorLancamento,
                     String date, TipoLancamento tipo, Categoria categoria){

        aguardarPagina();
        if(tipo == TipoLancamento.SAIDA) {
            getPage().getSaida().click(); // informa lançamento: SAÍDA
        }else{
            getPage().getEntrada().click(); // informa lançamento: ENTRADA
        }

        getPage().getDescricao().click();
        getPage().getDescricao().sendKeys(descricaoLancamento);

        getPage().getDataLancamento().sendKeys(date);
        getPage().getDataLancamento().sendKeys(Keys.TAB);

        getPage().getValor().click();
        getPage().getValor().sendKeys(String.valueOf(valorLancamento));

        getPage().getCategoryCombo().selectByValue(categoria.name());
        getPage().getBtnSalvar().click();

        try{
            Assert.fail(String.format("Houve ao salvar o lancamento. provavelmente um campo nao foi preeenchido. %s",
                    getPage().getDivError().getText()));

        }catch (NoSuchElementException e){}
    }
}
