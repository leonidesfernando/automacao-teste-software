package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public abstract class BasePage {

    protected WebDriver webDriver;
    public BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(
                new AjaxElementLocatorFactory(webDriver, 20),
                this);
    }
}
