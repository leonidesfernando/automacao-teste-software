package br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject;

import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


public class LancamentoPage extends BasePage {

    @CacheLookup @FindBy(id = "tipoLancamento1")
    private WebElement renda;

    @CacheLookup @FindBy(id = "tipoLancamento2")
    private WebElement despesa;

    @CacheLookup @FindBy(id = "tipoLancamento3")
    private WebElement transf;

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
        return getWebDriver().findElement(By.cssSelector("div.alert.alert-danger"));
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento){
        switch (tipoLancamento){
            case DESPESA -> despesa.click();
            case RENDA -> renda.click();
            case TRANSF -> transf.click();
        }
    }

    @Override
    protected boolean isReady() {
        return renda.isEnabled() &&
                despesa.isEnabled() &&
                transf.isEnabled() &&
                btnSalvar.isEnabled();
    }
}
