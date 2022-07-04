package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public abstract class GenericUI {

    @Getter
    private WebDriver webDriver;

    public GenericUI(WebDriver webDriver){
        this.webDriver = webDriver;
        init();
    }

    protected void init(){
        PageFactory.initElements(
                new AjaxElementLocatorFactory(webDriver, 20),
                this);
    }
}
