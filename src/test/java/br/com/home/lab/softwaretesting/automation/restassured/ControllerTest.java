package br.com.home.lab.softwaretesting.automation.restassured;

import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.converter.MoneyToStringConverter;
import br.com.home.lab.softwaretesting.automation.modelo.record.LancamentoRecord;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static br.com.home.lab.softwaretesting.automation.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {

    private static final String DESCRIPTION_TEST = "DESCRIPTION_TEST";
    private static final String ID_TO_USE = "ID_TO_USE";

    private static final ScenarioContextData context = new ScenarioContextData();

    @BeforeAll
    protected static void before() {
        RestAssured.baseURI = LoadConfigurationUtil.getOnlyUrl();
        RestAssured.port = LoadConfigurationUtil.getPort();
        login();
    }

    @SneakyThrows
    private static void login() {
        User user = LoadConfigurationUtil.getUser();
        SessionFilter sessionFilter = RestAssurredUtil.doFormLogin(user, "/login");
        assertNotNull(sessionFilter);
        assertTrue(StringUtils.isNotBlank(sessionFilter.getSessionId()));
        context.setContext(SESSION_ID, sessionFilter.getSessionId());
    }

    private String getSessionId(){
        return context.get(SESSION_ID);
    }

    @Test
    @Order(1)
    void salvarTest() {
        String descricao = new StringJoiner(" ")
                .add("Assured Rest")
                .add(DataGen.productName())
                .add(LocalDateTime.now().toString()).toString();

        Categoria[] categorias = Categoria.values();
        long factor = (System.currentTimeMillis() / 1000) % categorias.length;
        Map<String, String> formParams = new HashMap<>();
        MoneyToStringConverter converter = new MoneyToStringConverter();
        formParams.put(DESCRICAO, descricao);
        formParams.put(VALOR, converter.convert(BigDecimal.valueOf(DataGen.moneyValue())));
        formParams.put(DATA_LANCAMENTO, DataGen.strDate());
        formParams.put(TIPO_LANCAMENTO, (factor / 2) > 3 ? "RENDA" : "DESPESA");
        formParams.put(CATEGORIA, categorias[(int) factor].name());

        Response response = RestAssurredUtil.doRequestFormParam(getSessionId(), HttpMethod.POST,
                "/salvar", formParams);
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
        assertEquals(302, response.statusCode());
        context.setContext(DESCRIPTION_TEST, descricao);
    }

    //TODO: @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    // https://www.baeldung.com/junit-5-repeated-test
    @SneakyThrows
    @Test
    @Order(2)
    void buscandoComPostTest() {
        String descricao = context.get(DESCRIPTION_TEST);
        Response response = RestAssurredUtil.
                doRequestWithBodyParam(getSessionId(), HttpMethod.POST, "/buscaLancamentos", descricao);
        List<LancamentoRecord> lancamentos = JsonPath.with(response.asInputStream())
                .getList("lancamentos", LancamentoRecord.class);

        assertEquals(1, lancamentos.size());
        context.setContext(ID_TO_USE, lancamentos.get(0).id());
    }


    @SuppressWarnings("rawtypes")
    @Test
    @Order(3)
    void editarTest() {
        Pair<String, String> param = Pair.of("id", context.get(ID_TO_USE).toString());
        Response response = RestAssurredUtil.doGetWithPathParam(getSessionId(), param, "/editar/{id}");
        String html = response.body().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        String titulo = xmlPath.getString("html.body.div.div.div.h4");
        assertEquals("Cadastro de Lançamento", titulo);
    }

    @SuppressWarnings("rawtypes")
    @Test
    @Order(4)
    void removeTest() {
        Pair<String, String> param = Pair.of("id", context.get(ID_TO_USE).toString());
        Response response = RestAssurredUtil.doDeleteWithParam(getSessionId(), param, "/remover/{id}");
        assertEquals(302, response.getStatusCode());
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
    }
}
