package br.com.home.lab.softwaretesting.automation.selenium.webdriver.action;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject.LancamentoPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class LancamentoAction extends BaseAction<LancamentoPage> {


    public LancamentoAction(WebDriver webDriver) {
        super(webDriver, new LancamentoPage(webDriver));
    }

    @Override
    public LancamentoPage getPage() {
        return page;
    }

    public LancamentoAction and() {
        return this;
    }

    public LancamentoAction then() {
        return this;
    }

    public void salvaLancamento() {
        getPage().getBtnSalvar().click();
        try {
            log.info("Trying to save an entry");
            fail(String.format(
                    "Houve ao salvar o lancamento. provavelmente um campo nao foi preeenchido. %s",
                    getPage().getDivError().getText())
            );
        } catch (NoSuchElementException e) {
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
