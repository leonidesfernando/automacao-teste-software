package br.com.home.lab.softwaretesting.automation.cucumber.definitions;

import br.com.home.lab.softwaretesting.automation.cucumber.ScenarioContextData;
import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.modelo.record.BuscaForm;
import br.com.home.lab.softwaretesting.automation.modelo.record.LancamentoRecord;
import br.com.home.lab.softwaretesting.automation.modelo.record.ResultadoRecord;
import br.com.home.lab.softwaretesting.automation.restassured.RestAssurredUtil;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.User;
import br.com.home.lab.softwaretesting.automation.util.LoadConfigurationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import static br.com.home.lab.softwaretesting.automation.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class LancamentoControllerStepDefinitions {

    private final Semaphore semaphore = new Semaphore(1);
    private static final ScenarioContextData context = new ScenarioContextData();

    private static final User user = LoadConfigurationUtil.getUser();

    @Given("Usuario e senha existente nas configuracoes")
    public void usuarioSenhaExistenteConfiguracoes() {
        assertNotNull(user);
    }
    @Then("Deve logar e acessar a home")
    public void deveLogarAcessarHome() {
        SessionFilter sessionFilter = RestAssurredUtil.doFormLogin(user, "/login");
        assertNotNull(sessionFilter);
        assertTrue(StringUtils.isNotBlank(sessionFilter.getSessionId()));
        context.setContext(SESSION_ID, sessionFilter.getSessionId());
    }

    private String getSessionId(){
        return context.get(SESSION_ID);
    }

    @And("Remover o primeiro lancamento encontrado")
    public void removerOPrimeiroLancamentoEncontrado() {
        Pair<String, String> param = Pair.of("id", String.valueOf(getFirstEntryFromContextWithId().id()));
        Response response = RestAssurredUtil.delete(getSessionId(), param, "/remover/{id}");
        assertEquals(302, response.getStatusCode());
        assertTrue(response.getHeader("Location").contains("/lancamentos/"));
    }

    @And("Editar o primeiro lancamento encontrado")
    public void editar_o_primeiro_lancamento_encontrado() {
        Pair<String, String> param = Pair.of("id", String.valueOf(getFirstEntryFromContextWithId().id()));
        Response response = RestAssurredUtil.get(getSessionId(), param, "/editar/{id}");
        String html = response.body().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        String titulo = xmlPath.getString("html.body.div.div.div.h4");
        assertEquals("Cadastro de Lançamento", titulo, "body response: " + html);
    }

    @Given("Buscar um lancamento por categoria {string}")
    public void buscar_um_lancamento_por_categoria(String categoria) throws JsonProcessingException {
        buscarPor(categoria);
    }

    @DataTableType
    public LancamentoRecord lancamentos(Map<String, String> row) {
        return new LancamentoRecord(
                0L,
                row.get(DESCRICAO),
                getValorByParam(row.get(VALOR)),
                getDataLancamentoByParam(row.get(DATA_LANCAMENTO)),
                TipoLancamento.valueOf(row.get(TIPO_LANCAMENTO)),
                Categoria.valueOf(row.get(CATEGORIA))
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
                    Map.entry(TIPO_LANCAMENTO, record.tipoLancamento().name()),
                    Map.entry(CATEGORIA, record.categoria().name())
            );

            Response response = RestAssurredUtil.post(getSessionId(),
                    "/salvar", formParams);
            assertTrue(response.getHeader("Location").contains("/lancamentos/"));
            assertEquals(302, response.statusCode());
            addEntryToContext(record);
        }
    }

    private Response buscarPor(String item) throws JsonProcessingException {
        BuscaForm buscaForm = new BuscaForm(item, true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(buscaForm);
        Response response = RestAssurredUtil.
                post(getSessionId(), "/buscaLancamentos", json);
        List<LancamentoRecord> list = extractListFromResponse(response);
        list.forEach(this::addEntryToContext);
        return response;
    }

    private synchronized void addEntryToContext(LancamentoRecord lancamento) {

        List<LancamentoRecord> lancamentos;
        if (!context.exists(LANCAMENTOS)) {
            context.setContext(LANCAMENTOS, new ArrayList<LancamentoRecord>());
        }
        lancamentos = context.get(LANCAMENTOS);

        Optional<LancamentoRecord> opt = lancamentos.stream()
                .filter(l -> l.valor().equals(lancamento.valor()))
                .filter(l -> l.tipoLancamento().equals(lancamento.tipoLancamento()))
                .findFirst();
        opt.ifPresent(lancamentos::remove);
        lancamentos.add(lancamento);

    }

    private List<LancamentoRecord> extractListFromResponse(Response response) {
        return RestAssurredUtil.extractDataFromBodyResponse(response, new TypeReference<ResultadoRecord>() {
        }).lancamentos();
    }

    private synchronized LancamentoRecord getFirstEntryFromContextWithId() {

        List<LancamentoRecord> lancamentos = context.get(LANCAMENTOS);
        Optional<LancamentoRecord> lancamentoRecordOpt = lancamentos.stream().filter(l -> l.id() != 0L).findFirst();
        LancamentoRecord lancamentoRecord = lancamentoRecordOpt.orElseThrow(() -> new IllegalStateException("An entry should be found:"));
        lancamentos.remove(lancamentoRecord);
        semaphore.release();
        return lancamentoRecord;
    }
}
