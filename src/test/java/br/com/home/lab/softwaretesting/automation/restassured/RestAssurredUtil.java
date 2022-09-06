package br.com.home.lab.softwaretesting.automation.restassured;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpMethod;
import org.testng.internal.collections.Pair;

import java.util.Map;

import static io.restassured.RestAssured.given;

@UtilityClass
public class RestAssurredUtil {


    public Response doGetWithPathParam(String sessionId, Pair<String, String> param, String endPoint){
        return doGetWithPathParam(given().sessionId(sessionId), param, endPoint);
    }

    public Response doGetWithPathParam(Pair<String, String> param, String endPoint){
        return doGetWithPathParam(given(), param, endPoint);
    }

    private Response doGetWithPathParam(RequestSpecification spec, Pair<String, String> param, String endPoint){
        return spec.pathParam(param.first(), param.second()).when().get(endPoint);
    }

    public Response doDeleteWithParam(String sessionId, Pair<String, String> param, String endPoint){
        return doDeleteWithParam(given().sessionId(sessionId), param, endPoint);
    }

    public Response doDeleteWithParam(Pair<String, String> param, String endPoint){
        return doDeleteWithParam(given(), param, endPoint);
    }

    private Response doDeleteWithParam(RequestSpecification spec, Pair<String, String> param, String endPoint){
        return spec.pathParam(param.first(), param.second())
                .when().delete(endPoint);
    }

    public Response doRequestWithBodyParam(String sessionId, HttpMethod method, String endPoint, String bodyParam){
        return doRequestWithBodyParam(given().sessionId(sessionId), method, endPoint, bodyParam);
    }
    public Response doRequestWithBodyParam(HttpMethod method, String endPoint, String bodyParam){
        return doRequestWithBodyParam(given(), method, endPoint, bodyParam);
    }

    private Response doRequestWithBodyParam(RequestSpecification spec, HttpMethod method, String endPoint, String bodyParam){
        return spec.config(RestAssuredConfig.config()
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
                .contentType("application/json; charset=utf-8")
                .when().body(bodyParam)
                .request(method.name(), endPoint);
    }

    public Response doRequestFormParam(HttpMethod method, String endPoint, Map<String, String> formParams){
        return doRequest(given().when(), method, endPoint, formParams);
    }

    private Response doRequest(RequestSpecification requestSpecification, HttpMethod method, String endpoint, Map<String, String> formParams){
        addFormParams(requestSpecification, formParams);
        return requestSpecification
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .request(method.name(), endpoint);
    }

    public Response doRequestFormParam(String sessionId, HttpMethod method, String endPoint, Map<String, String> formParams){
        return doRequest(given().sessionId(sessionId), method, endPoint, formParams);
    }

    public SessionFilter doFormLogin(User user, String formActionn){
        SessionFilter sessionFilter = new SessionFilter();
        given().auth()
                .form(user.username(), user.password(),
                        new FormAuthConfig(formActionn, "user", "password"))
                .filter(sessionFilter)
                .get();
        return sessionFilter;
    }

    private void addFormParams(RequestSpecification requestSpecification, Map<String, String> formParams){
        formParams.entrySet()
                .forEach(entry ->
                        requestSpecification
                                .formParam(entry.getKey(), entry.getValue()));
    }
}
