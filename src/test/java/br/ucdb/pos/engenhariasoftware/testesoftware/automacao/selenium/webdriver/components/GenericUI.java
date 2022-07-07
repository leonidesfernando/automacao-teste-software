package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class GenericUI extends LoadableComponent {

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


    /**
     * <p>The default behavior the GenericUI component is do nothing to load, its will loaded when the page is loaded.
     * <b>But if will be necessary you can implement this method(load),
     * but be aware what you are doing</b></p>
     * <p>Each GenericUI instance must implement the
     * <code>LoadableComponemt::isLoaded</code> method</p>
     */
    @Override
    protected void load() {}
}
