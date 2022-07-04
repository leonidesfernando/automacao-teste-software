package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


public class LancamentoPage extends BasePage {

    @CacheLookup @FindBy(id = "tipoLancamento1")
    private WebElement entrada;

    @CacheLookup @FindBy(id = "tipoLancamento2")
    private WebElement saida;

    @CacheLookup @Getter @FindBy(id = "descricao")
    private WebElement descricao;

    @CacheLookup @Getter @FindBy(name = "dataLancamento")
    private WebElement dataLancamento;

    @CacheLookup @Getter @FindBy(id = "valor")
    private WebElement valor;

    @CacheLookup @FindBy(id = "categoria")
    private WebElement categoriaWebElement;

    @CacheLookup @Getter @FindBy(id = "btnSalvar")
    private WebElement btnSalvar;


    public LancamentoPage(final WebDriver driver){
        super(driver);
    }

    public Select getCategoryCombo(){
        return new Select(categoriaWebElement);
    }

    public WebElement getDivError(){
        return webDriver.findElement(By.cssSelector("div.alert.alert-danger"));
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento){
        if(tipoLancamento == TipoLancamento.SAIDA) {
            saida.click(); // informa lançamento: SAÍDA
        }else{
            entrada.click(); // informa lançamento: ENTRADA
        }
    }
}


