package br.com.home.lab.softwaretesting.automation.cucumber.definitions;

import br.com.home.lab.softwaretesting.automation.modelo.record.LoginCredentialRecord;
import br.com.home.lab.softwaretesting.automation.restassured.RestAssurredUtil;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.restassured.filter.session.SessionFilter;

import java.util.List;
import java.util.Map;

import static br.com.home.lab.softwaretesting.automation.cucumber.definitions.LoginDataTableValues.VALID_PASSWORD;
import static br.com.home.lab.softwaretesting.automation.cucumber.definitions.LoginDataTableValues.VALID_USER;

public class LoginControllerStepDefinitions {

    private static final User user = LoadConfigurationUtil.getUser();

    @Given("The follow credentials then")
    public void the_follow_credentials_then(List<LoginCredentialRecord> credentials){

        for (LoginCredentialRecord credential : credentials) {
            var user = getUserByCredential(credential);
            SessionFilter sessionFilter = RestAssurredUtil.doFormLogin(user, "/login");

            System.out.println("Credential: " + credential);
            System.out.println("has session? " + sessionFilter.hasSessionId());
            System.out.println("session: " + sessionFilter.getSessionId());

        }
    }

    @DataTableType
    public LoginCredentialRecord credentials(Map<String, String> row){
        return new LoginCredentialRecord(
                LoginDataTableValues.from(row.get("User")),
                LoginDataTableValues.from(row.get("Password"))
        );
    }

    private User getUserByCredential(LoginCredentialRecord credential){
        return new User(
                getUserByType(credential.typeUser()),
                getPasswordByType(credential.typePassword())
        );
    }

    private String getUserByType(LoginDataTableValues typeUser){
        if(typeUser == VALID_USER)
            return user.username();
        return user.username() + DataGen.number(5);
    }

    private String getPasswordByType(LoginDataTableValues typePassword){
        if(typePassword == VALID_PASSWORD){
            return user.password();
        }
        return DataGen.productName();
    }
}
