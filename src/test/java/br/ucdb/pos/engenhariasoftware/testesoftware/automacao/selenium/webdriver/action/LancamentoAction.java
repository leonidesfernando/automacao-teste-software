package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.LancamentoPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.math.BigDecimal;

@Slf4j
public class LancamentoAction extends BaseAction<LancamentoPage> {


    public LancamentoAction(WebDriver webDriver) {
        super(webDriver, new LancamentoPage(webDriver));
    }

    @Override
    public LancamentoPage getPage() {
        return page;
    }

    public void salvaLancamento(){
        getPage().getBtnSalvar().click();
        try{
            Assert.fail(String.format(
                    "Houve ao salvar o lancamento. provavelmente um campo nao foi preeenchido. %s",
                    getPage().getDivError().getText())
            );
        }catch (NoSuchElementException e){
            log.info("Entry save successfully");
        }
    }

    public void salvaLancamento(String descricaoLancamento, BigDecimal valorLancamento,
                                String date, TipoLancamento tipo, Categoria categoria){
        fillData(descricaoLancamento, valorLancamento, date, tipo, categoria);
        salvaLancamento();
    }

    public LancamentoAction setDescricao(String descricaoLancamento){
        getPage().getDescricao().clear();
        getPage().getDescricao().click();
        getPage().getDescricao().sendKeys(descricaoLancamento);
        return this;
    }

    private void fillData(String descricaoLancamento, BigDecimal valorLancamento,
                          String date, TipoLancamento tipo, Categoria categoria){

        getPage().setTipoLancamento(tipo);
        setDescricao(descricaoLancamento);
        getPage().getDataLancamento().sendKeys(date);
        getPage().getDataLancamento().sendKeys(Keys.TAB);

        getPage().getValor().click();
        getPage().getValor().sendKeys(String.valueOf(valorLancamento));
        getPage().getCategoryCombo().selectByValue(categoria.name());
    }
}
