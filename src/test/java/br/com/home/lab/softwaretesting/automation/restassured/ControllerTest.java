package br.com.home.lab.softwaretesting.automation.restassured;

import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.converter.MoneyToStringConverter;
import br.com.home.lab.softwaretesting.automation.modelo.record.BuscaForm;
import br.com.home.lab.softwaretesting.automation.modelo.record.ResultadoRecord;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;
import org.testng.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static br.com.home.lab.softwaretesting.automation.util.Constants.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ControllerTest {

    private static final String DESCRIPTION_TEST = "DESCRIPTION_TEST";
    private static final String ID_TO_USE = "ID_TO_USE";

    private static final ScenarioContextData context = new ScenarioContextData();

    @BeforeTest
    public void before() {
        RestAssured.baseURI = LoadConfigurationUtil.getOnlyUrl();
        RestAssured.port = LoadConfigurationUtil.getPort();
        login();
    }

    @SneakyThrows
    private void login(){
        User user = LoadConfigurationUtil.getUser();
        SessionFilter sessionFilter = RestAssurredUtil.doFormLogin(user, "/login");
        Assert.assertNotNull(sessionFilter);
        Assert.assertTrue(Strings.isNotNullAndNotEmpty(sessionFilter.getSessionId()));
        context.setContext(SESSION_ID, sessionFilter.getSessionId());
    }

    private String getSessionId(){
        return context.get(SESSION_ID);
    }

    @Test
    public void salvarTest() {
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
        formParams.put(DATA_LANCAMENTO, DataGen.strDateCurrentMonth());
        formParams.put(TIPO_LANCAMENTO, ((factor / 2) > 3) ? "RENDA" : "DESPESA");
        formParams.put(CATEGORIA, categorias[(int) factor].name());

        Response response = RestAssurredUtil.post(getSessionId(),
                "/salvar", formParams);
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
        assertEquals(response.statusCode(), 302);
        context.setContext(DESCRIPTION_TEST, descricao);
    }

    @SneakyThrows
    @Test(dependsOnMethods = "salvarTest")
    public void buscandoComPostTest() {
        String descricao = context.get(DESCRIPTION_TEST);
        BuscaForm buscaForm = new BuscaForm(descricao, true);
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(buscaForm);
        Response response = RestAssurredUtil.
                post(getSessionId(), "/buscaLancamentos", json);

        var lancamentos = RestAssurredUtil
                .extractDataFromBodyResponse(response, new TypeReference<ResultadoRecord>() {
                }).lancamentos();
        assertEquals(lancamentos.size(), 1);
        context.setContext(ID_TO_USE, lancamentos.get(0).id());
    }

    @Test(dependsOnMethods = {"salvarTest", "buscandoComPostTest"})
    public void editarTest() {
        Pair<String, String> param = new Pair<>("id", context.get(ID_TO_USE).toString());
        Response response = RestAssurredUtil.get(getSessionId(), param, "/editar/{id}");
        String html = response.body().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        String titulo = xmlPath.getString("html.body.div.div.div.h4");
        assertEquals(titulo, "Cadastro de Lançamento");
    }

    @Test(dependsOnMethods = {"buscandoComPostTest", "salvarTest", "editarTest"})
    public void removeTest() {
        Pair<String, String> param = new Pair<>("id", context.get(ID_TO_USE).toString());
        Response response = RestAssurredUtil.delete(getSessionId(), param, "/remover/{id}");
        assertEquals(response.getStatusCode(), 302);
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
    }
}
