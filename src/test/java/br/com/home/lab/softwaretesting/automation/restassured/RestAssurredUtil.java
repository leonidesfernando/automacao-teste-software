package br.com.home.lab.softwaretesting.automation.restassured;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpMethod;
import org.testng.internal.collections.Pair;

import java.util.Map;

import static io.restassured.RestAssured.given;

@UtilityClass
public class RestAssurredUtil {

    public Response doGetWithPathParam(Pair<String, String> param, String endPoint){
        return given()
                .pathParam(param.first(), param.second()).when().get(endPoint);
    }

    public Response doDeleteWithParam(Pair<String, String> param, String endPoint){
        return given().pathParam(param.first(), param.second())
                .when().delete(endPoint);
    }

    public Response doRequestWithBodyParam(HttpMethod method, String endPoint, String bodyParam){
        RequestSpecification requestSpecification = given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
                .contentType("application/json; charset=utf-8")
                .when();
        return requestSpecification.body(bodyParam)
                .request(method.name(), endPoint);
    }

    public Response doRequestFormParam(HttpMethod method, String endPoint, Map<String, String> formParams){
        RequestSpecification requestSpecification = given().when();
        addFormParams(requestSpecification, formParams);
        requestSpecification.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        return requestSpecification.request(method.name(), endPoint);
    }

    private void addFormParams(RequestSpecification requestSpecification, Map<String, String> formParams){
        formParams.entrySet()
                .forEach(entry ->
                        requestSpecification
                                .formParam(entry.getKey(), entry.getValue()));
    }
}
