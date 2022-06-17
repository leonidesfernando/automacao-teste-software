package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.components;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class GenericUI {

    @Getter
    private WebDriver webDriver;

    public GenericUI(WebDriver webDriver){
        this.webDriver = webDriver;
        init();
    }

    protected void init(){
        PageFactory.initElements(webDriver, this);
    }
}
