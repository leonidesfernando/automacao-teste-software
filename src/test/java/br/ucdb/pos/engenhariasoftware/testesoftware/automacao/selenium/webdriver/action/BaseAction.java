package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.BasePage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

public abstract class BaseAction<T extends BasePage> {

    @Getter
    protected WebDriver webDriver;

    protected T page;

    public BaseAction(WebDriver webDriver, T page){
        this.webDriver = webDriver;
        this.page = page;
        this.page.get();
    }

    abstract public T getPage();
}
