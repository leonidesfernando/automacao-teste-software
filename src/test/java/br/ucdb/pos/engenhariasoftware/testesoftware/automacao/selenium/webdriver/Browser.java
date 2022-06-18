package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.helper.SeleniumBootstrap;
import org.openqa.selenium.WebDriver;

public enum Browser {
    CHROME{
        @Override
        WebDriver loadBrowser() {
            return SeleniumBootstrap.setupChrome();
        }
    },
    EDGE {
        @Override
        WebDriver loadBrowser() {
            return SeleniumBootstrap.setupEdge();
        }
    },
    FIREFOX {
        @Override
        WebDriver loadBrowser() {
            return SeleniumBootstrap.setupFirefox();
        }
    };

    abstract WebDriver loadBrowser();
}
