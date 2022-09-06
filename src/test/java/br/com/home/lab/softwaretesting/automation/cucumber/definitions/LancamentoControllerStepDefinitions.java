package br.com.home.lab.softwaretesting.automation.cucumber.definitions;

import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.modelo.record.LancamentoRecord;
import br.com.home.lab.softwaretesting.automation.restassured.RestAssurredUtil;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.Assert;
import org.testng.internal.collections.Pair;
import org.testng.util.Strings;

import java.util.List;
import java.util.Map;

import static br.com.home.lab.softwaretesting.automation.util.Constants.*;
import static org.testng.Assert.*;

public class LancamentoControllerStepDefinitions {

    private static final ScenarioContextData context = new ScenarioContextData();

    private static final User user = LoadConfigurationUtil.getUser();

    @Given("Usuario e senha existente nas configuracoes")
    public void usuarioSenhaExistenteConfiguracoes() {
        assertNotNull(user);
    }
    @Then("Deve logar e acessar a home")
    public void deveLogarAcessarHome() {
        SessionFilter sessionFilter = RestAssurredUtil.doFormLogin(user, "/login");
        Assert.assertNotNull(sessionFilter);
        Assert.assertTrue(Strings.isNotNullAndNotEmpty(sessionFilter.getSessionId()));
        context.setContext(SESSION_ID, sessionFilter.getSessionId());
    }

    private String getSessionId(){
        return context.get(SESSION_ID);
    }

    @And("Remover o primeiro lancamento encontrado")
    public void removerOPrimeiroLancamentoEncontrado() {
        List<LancamentoRecord> lancamentos = context.get(LANCAMENTOS);
        Pair<String, String> param = new Pair("id", lancamentos.get(0).id());
        Response response = RestAssurredUtil.doDeleteWithParam(getSessionId(), param, "/remover/{id}");
        assertEquals(response.getStatusCode(), 302);
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
    }

    @And("Editar o primeiro lancamento encontrado")
    public void editar_o_primeiro_lancamento_encontrado() {
        List<LancamentoRecord> lancamentos = context.get(LANCAMENTOS);
        Pair<String, String> param = new Pair("id", lancamentos.get(0).id());
        Response response = RestAssurredUtil.doGetWithPathParam(getSessionId(), param, "/editar/{id}");
        String html = response.body().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        String titulo = xmlPath.getString("html.body.div.div.div.h4");
        assertEquals(titulo, "Cadastro de Lançamento");
    }

    @Given("Buscar um lancamento por categoria {string}")
    public void buscar_um_lancamento_por_categoria(String categoria) {
        buscarPor(categoria);
    }

    @DataTableType
    public LancamentoRecord lancamentos(Map<String, String> row) {
        return new LancamentoRecord(
                0L,
                row.get(DESCRICAO),
                getValorByParam(row.get(VALOR)),
                getDataLancamentoByParam(row.get(DATA_LANCAMENTO)),
                row.get(TIPO_LANCAMENTO),
                row.get(CATEGORIA)
        );
    }

    private String getValorByParam(String param){
        try{
            return LancamentoDataTableValues.from(param).value();
        }catch (IllegalArgumentException e){
            return param;
        }
    }

    private String getDataLancamentoByParam(String param){
        try {
            return LancamentoDataTableValues.from(param).value();
        }catch (IllegalArgumentException e){
            return param;
        }
    }

    @Given("Registrar lancamento com dados fornecidos")
    public void registrar_lancamento_com_dados_fornecidos(List<LancamentoRecord> lancamentos) {

        for (LancamentoRecord record : lancamentos) {
            Map<String, String> formParams = Map.ofEntries(
                    Map.entry(DESCRICAO, record.descricao()),
                    Map.entry(VALOR, record.valor()),
                    Map.entry(DATA_LANCAMENTO, record.dataLancamento()),
                    Map.entry(TIPO_LANCAMENTO, record.tipoLancamento()),
                    Map.entry(CATEGORIA, record.categoria())
            );

            Response response = RestAssurredUtil.doRequestFormParam(getSessionId(), HttpMethod.POST,
                    "/salvar", formParams);
            assertTrue(response.getHeader("Location").contains("/lancamentos/"));
            assertEquals(response.statusCode(), 302);
        }
    }

    private Response buscarPor(String item){
        Response response = RestAssurredUtil.
                doRequestWithBodyParam(getSessionId(), HttpMethod.POST, "/buscaLancamentos", item);
        context.setContext(LANCAMENTOS, extractListFromResponse(response));
        return response;
    }


    public List<LancamentoRecord> extractListFromResponse(Response response) {
        return JsonPath.with(response.asInputStream())
                .getList("lancamentos", LancamentoRecord.class);
    }
}
