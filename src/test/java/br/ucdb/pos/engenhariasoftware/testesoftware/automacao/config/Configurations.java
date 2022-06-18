package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.config;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.Browser;
import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:configuration.properties"
})
public interface Configurations extends Config {

    String url();
    boolean headless();

    Browser browser();
}
