package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


public class LancamentoPage extends BasePage {

    @Getter @FindBy(id = "tipoLancamento1")
    private WebElement entrada;

    @Getter @FindBy(id = "tipoLancamento2")
    private WebElement saida;

    @Getter @FindBy(id = "descricao")
    private WebElement descricao;

    @Getter @FindBy(name = "dataLancamento")
    private WebElement dataLancamento;

    @Getter @FindBy(id = "valor")
    private WebElement valor;

    @FindBy(id = "categoria")
    private WebElement categoriaWebElement;

    @Getter @FindBy(id = "btnSalvar")
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
}


