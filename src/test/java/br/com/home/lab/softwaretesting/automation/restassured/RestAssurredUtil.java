package br.com.home.lab.softwaretesting.automation.restassured;

import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@UtilityClass
public class RestAssurredUtil {

    public Response get(String sessionId, List<Pair<String, String>> params, String endPoint) {
        RequestSpecification specification = given()
                .sessionId(sessionId);
        params.forEach(p -> specification.pathParam(p.first(), p.second()));
        return specification.get(endPoint);
    }

    public Response get(String sessionId, Pair<String, String> param, String endPoint) {
        return pathParam(sessionId, param)
                .get(endPoint);
    }

    public Response delete(String sessionId, Pair<String, String> idParam, String endpoint) {
        return pathParam(sessionId, idParam)
                .delete(endpoint);
    }

    private RequestSpecification pathParam(String sessionId, Pair<String, String> idParam) {
        return given()
                .sessionId(sessionId)
                .pathParam(idParam.getLeft(), idParam.getRight());
    }

    public Response post(String sessionId, String endPoint, String bodyParam) {
        return postWithBodyParam(sessionId, endPoint, bodyParam);
    }

    private Response postWithBodyParam(String sessionId, String endPoint, String bodyParam) {
        return given().sessionId(sessionId)
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
                .contentType("application/json; charset=utf-8")
                .when().body(bodyParam)
                .request(Method.POST, endPoint);
    }

    private Response doRequest(RequestSpecification requestSpecification, HttpMethod method, String endpoint, Map<String, String> formParams) {
        addFormParams(requestSpecification, formParams);
        return requestSpecification
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .request(method.name(), endpoint);
    }

    public Response post(String sessionId, String endPoint, Map<String, String> formParams) {
        return doRequestFormParam(sessionId, HttpMethod.POST, endPoint, formParams);
    }

    private Response doRequestFormParam(String sessionId, HttpMethod method, String endPoint, Map<String, String> formParams) {
        return doRequest(given().sessionId(sessionId), method, endPoint, formParams);
    }

    public SessionFilter doFormLogin(User user, String formActionn) {
        SessionFilter sessionFilter = new SessionFilter();
        given().auth()
                .form(user.username(), user.password(),
                        new FormAuthConfig(formActionn, "user", "password"))
                .filter(sessionFilter)
                .get();
        return sessionFilter;
    }

    private void addFormParams(RequestSpecification requestSpecification, Map<String, String> formParams) {
        formParams.entrySet()
                .forEach(entry ->
                        requestSpecification
                                .formParam(entry.getKey(), entry.getValue()));
    }

    public <T> T extractDataFromBodyResponse(Response response, TypeReference<T> type) {
        var jsonNode = response.body().as(JsonNode.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(jsonNode, type);
    }
}
