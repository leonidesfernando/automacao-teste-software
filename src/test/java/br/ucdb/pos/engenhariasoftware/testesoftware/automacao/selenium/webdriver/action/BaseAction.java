package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.BasePage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

@AllArgsConstructor
public abstract class BaseAction<T extends BasePage> {

    @Getter
    protected WebDriver webDriver;

    protected T page;

    abstract public T getPage();
}
