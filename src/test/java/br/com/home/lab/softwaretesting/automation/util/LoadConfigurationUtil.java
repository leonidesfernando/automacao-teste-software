package br.com.home.lab.softwaretesting.automation.util;

import br.com.home.lab.softwaretesting.automation.config.Configurations;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import lombok.experimental.UtilityClass;
import org.aeonbits.owner.ConfigFactory;
import org.testng.util.Strings;


@UtilityClass
public class LoadConfigurationUtil {

    private static final String URL = "url";
    private static final Configurations config = ConfigFactory.create(Configurations.class);

    public String getUrl() {
        String urlByParameter = System.getProperty(URL);
        return Strings.isNotNullAndNotEmpty(urlByParameter) ? urlByParameter : config.url();
    }

    public String getOnlyUrl() {
        String urlAndPort[] = getUrl().split(":");
        return String.format("%s:%s", urlAndPort[0], urlAndPort[1]);
    }

    public int getPort() {
        String urlAndPort[] = getUrl().split(":");
        return urlAndPort.length > 2 ?
                Integer.parseInt(urlAndPort[2].replace("/", ""))
                : 80;
    }

    public User getUser() {
        return new User(config.username(), config.password());
    }
}
