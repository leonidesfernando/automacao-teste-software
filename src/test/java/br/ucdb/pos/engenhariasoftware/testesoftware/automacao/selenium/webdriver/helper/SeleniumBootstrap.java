package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumBootstrap {

    public static WebDriver setupChrome(){
        WebDriverManager driverManager = WebDriverManager.chromedriver();
        driverManager.setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("disable-extensions");
        chromeOptions.addArguments("--no-sandbox");

        return new ChromeDriver(chromeOptions);
    }
}
