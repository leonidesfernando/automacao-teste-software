package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaLancamentosPage extends BasePage {

    @FindBy(id = "novoLancamento")
    private WebElement newEntry;

    @FindBy(id = "itemBusca")
    private WebElement searchItem;

    @FindBy(id = "bth-search")
    private WebElement btnSearch;

    private final static String LIST_TABLE_ID = "divTabelaLancamentos";

    public ListaLancamentosPage(final WebDriver driver){
        super(driver);
    }

    public ListaLancamentosPage acessa(){
        driver.get("http://localhost:8080/lancamentos/");
        return this;
    }

    public LancamentoPage novoLancamento(){
        aguardarPagina();
        driver.findElement(By.id("novoLancamento")).click();
        return new LancamentoPage(driver);
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    LocalDateTime dataHora, TipoLancamento tipo){

        aguardarPagina();
        buscaLancamentoPorDescricao(descricaoLancamento);
        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String lancamentos = driver.getPageSource();
        return (lancamentos.contains(descricaoLancamento) &&
                lancamentos.contains(dataHora.format(formatoDataLancamento)) &&
                lancamentos.contains(tipo.getDescricao()));
    }

    public void buscaLancamentoPorDescricao(String descricaoLancamento){
        searchItem.sendKeys(descricaoLancamento);
        btnSearch.click();
        aguardarPagina();
    }

    protected void aguardarPagina(){
        SeleniumUtil.fluentWait(driver)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(LIST_TABLE_ID)));
    }
}

