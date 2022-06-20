package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
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
}


