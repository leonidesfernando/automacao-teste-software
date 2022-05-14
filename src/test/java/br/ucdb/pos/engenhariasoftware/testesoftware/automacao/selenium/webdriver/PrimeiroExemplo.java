package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumBootstrap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PrimeiroExemplo {

    public static void main(String[] args) {


        // abre o firefox
        WebDriver driver = SeleniumBootstrap.setupChrome();

        // acessa o site do Selenium
        driver.get("http://www.seleniumhq.org");

        //obtem e clica no link da página de download
        driver.findElement(By.id("menu_download")).click();

        // fecha todas as janelas do navegador e finaliza a sessão do WebDriver
        driver.quit();
    }
}


