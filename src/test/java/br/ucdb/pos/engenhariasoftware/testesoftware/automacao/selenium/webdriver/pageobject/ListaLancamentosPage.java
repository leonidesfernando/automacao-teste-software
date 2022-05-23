package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaLancamentosPage {

    private WebDriver driver;

    @FindBy(id = "novoLancamento")
    private WebElement newEntry;

    @FindBy(id = "itemBusca")
    private WebElement searchItem;

    @FindBy(id = "bth-search")
    private WebElement btnSearch;

    public ListaLancamentosPage(final WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ListaLancamentosPage acessa(){
        driver.get("http://localhost:8080/lancamentos");
        return this;
    }

    public LancamentoPage novoLancamento(){
        driver.findElement(By.id("novoLancamento")).click();
        return new LancamentoPage(driver);
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    LocalDateTime dataHora, TipoLancamento tipo){

        buscaLancamentoPorDescricao(descricaoLancamento);
        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String lancamentos = driver.getPageSource();
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return (lancamentos.contains(descricaoLancamento) &&
                lancamentos.contains(df.format(valorLancamento)) &&
                lancamentos.contains(dataHora.format(formatoDataLancamento)) &&
                lancamentos.contains(tipo.getDescricao()));
    }

    public void buscaLancamentoPorDescricao(String descricaoLancamento){
        searchItem.sendKeys(descricaoLancamento);
        btnSearch.click();
    }
}

