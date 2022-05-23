package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LancamentoPage extends BasePage {

    @FindBy(id = "tipoLancamento1")
    private WebElement entrada;

    @FindBy(id = "tipoLancamento2")
    private WebElement saida;

    @FindBy(id = "descricao")
    private WebElement descricao;

    @FindBy(name = "dataLancamento")
    private WebElement dataLancamento;

    @FindBy(id = "valor")
    private WebElement valor;

    @FindBy(id = "categoria")
    private WebElement categoriaWebElement;

    @FindBy(id = "btnSalvar")
    private WebElement btnSalvar;


    public LancamentoPage(final WebDriver driver){
        super(driver);
    }

    @Override
    protected void aguardarPagina() {
        SeleniumUtil.fluentWait(driver)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(btnSalvar.getAttribute("id"))));
    }

    public void cria(final String descricaoLancamento, final BigDecimal valorLancamento,
                     LocalDateTime dataHora, TipoLancamento tipo, Categoria categoria){

        aguardarPagina();
        if(tipo == TipoLancamento.SAIDA) {
            saida.click(); // informa lançamento: SAÍDA
        }else{
            entrada.click(); // informa lançamento: ENTRADA
        }

        descricao.click();
        descricao.sendKeys(descricaoLancamento);

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataLancamento.sendKeys(dataHora.format(formatoDataLancamento));
        dataLancamento.sendKeys(Keys.TAB);

        valor.click();
        valor.sendKeys(String.valueOf(valorLancamento));

        Select categoriaCb = new Select(categoriaWebElement);
        categoriaCb.selectByValue(categoria.name());
        btnSalvar.click();

        try{
            WebElement errorMessage = driver.findElement(By.cssSelector("div.alert.alert-danger"));
            Assert.fail(String.format("Houve ao salvar o lancamento. provavelmente um campo nao foi preeenchido. %s", errorMessage.getText()));

        }catch (NoSuchElementException e){

        }
    }
}


