package br.com.home.lab.softwaretesting.automation.cucumber.definitions;

import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.modelo.Lancamento;
import br.com.home.lab.softwaretesting.automation.modelo.vo.LancamentoVO;
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

import java.util.HashMap;
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
        List<Lancamento> lancamentos = context.get(LANCAMENTOS);
        Pair<String, String> param = new Pair("id", lancamentos.get(0).getId());
        Response response = RestAssurredUtil.doDeleteWithParam(getSessionId(), param, "/remover/{id}");
        assertEquals(response.getStatusCode(), 302);
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
    }

    @And("Editar o primeiro lancamento encontrado")
    public void editar_o_primeiro_lancamento_encontrado() {
        List<Lancamento> lancamentos = context.get(LANCAMENTOS);
        Pair<String,String> param = new Pair("id", lancamentos.get(0).getId());
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
    public LancamentoVO lancamentos(Map<String,String> row){
        return LancamentoVO.builder()
                .descricao(row.get(DESCRICAO))
                .valor(getValorByParam(row.get(VALOR)))
                .dataLancamento(getDataLancamentoByParam(row.get(DATA_LANCAMENTO)))
                .tipoLancamento(row.get(TIPO_LANCAMENTO))
                .categoria(row.get(CATEGORIA))
                .build();
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
    public void registrar_lancamento_com_dados_fornecidos(List<LancamentoVO> lancamentos) {

        for(LancamentoVO vo : lancamentos){
            Map<String, String> formParams = new HashMap<>();
            formParams.put(DESCRICAO, vo.getDescricao());
            formParams.put(VALOR,vo.getValor());
            formParams.put(DATA_LANCAMENTO,vo.getDataLancamento());
            formParams.put(TIPO_LANCAMENTO,vo.getTipoLancamento());
            formParams.put(CATEGORIA,vo.getCategoria());

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


    public List<Lancamento> extractListFromResponse(Response response) {
        return JsonPath.with( response.asInputStream())
                .getList("lancamentos", Lancamento.class);
    }
}
