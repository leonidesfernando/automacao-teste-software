package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class BasePage extends LoadableComponent<BasePage> {

    protected WebDriver webDriver;
    public BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(
                new AjaxElementLocatorFactory(webDriver, 20),
                this);
    }

    /**
     * <p>The default behavior the page do nothing to load, its will loaded by the <i>BaseSeleniumTest</i>,
     * on <code>BaseSeleniumTest::access</code> method. <b>But if will be necessary you can implement this method(load),
     * but be aware what you are doing</b></p>
     * <p>Each BasePage instance must implement the
     * <code>LoadableComponemt::isLoaded</code> method</p>
     */
    @Override
    protected void load() {}
}
