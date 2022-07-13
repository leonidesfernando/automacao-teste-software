package br.com.home.lab.softwaretesting.automation.selenium.webdriver.pageobject;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage{

    @FindBy(id = "tableChart")
    private WebElement tableChart;

    @FindBy(id = "pieChart")
    private WebElement pieChart;

    @Getter @FindBy(css = "a[title='Listagem']")
    private WebElement btnList;

    public DashboardPage(WebDriver webDriver) {
        super(webDriver);
    }

    protected boolean isReady(){
        return pieChart.isDisplayed()
                && tableChart.isDisplayed()
                && btnList.isDisplayed();
    }
}