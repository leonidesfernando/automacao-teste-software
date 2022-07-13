package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.helper.SeleniumBootstrap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PrimeiroExemplo {

    public static void main(String[] args) {


        // abre o firefox
        WebDriver driver = SeleniumBootstrap.setupExistingBrowser();

        // acessa o site do Selenium
        driver.get("https://www.selenium.dev");

        //obtem e clica no link da página de download
        driver.findElement(By.linkText("Downloads")).click();

        // fecha todas as janelas do navegador e finaliza a sessão do WebDriver
        driver.quit();
    }
}


