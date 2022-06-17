package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.cucumber.definitions;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.cucumber.ScenarioContextData;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Lancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.vo.LancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.restassured.RestAssurredUtil;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.internal.collections.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.ucdb.pos.engenhariasoftware.testesoftware.automacao.util.Constants.*;
import static org.testng.Assert.assertEquals;

public class LancamentoControllerStepDefinitions {

    private ScenarioContextData context;

    public LancamentoControllerStepDefinitions(ScenarioContextData context){
        this.context = context;
    }

    @And("Remover o primeiro lancamento encontrado")
    public void removerOPrimeiroLancamentoEncontrado() {
        List<Lancamento> lancamentos = context.get(LANCAMENTOS);
        Pair<String, String> param = new Pair("id", lancamentos.get(0).getId());
        Response response = RestAssurredUtil.doDeleteWithParam(param, "/remover/{id}");
        assertEquals(response.getStatusCode(), 302);
    }

    @And("Editar o primeiro lancamento encontrado")
    public void editar_o_primeiro_lancamento_encontrado() {
        List<Lancamento> lancamentos = context.get(LANCAMENTOS);
        Pair<String,String> param = new Pair("id", lancamentos.get(0).getId());
        Response response = RestAssurredUtil.doGetWithPathParam(param, "/editar/{id}");
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

            Response response = RestAssurredUtil.doRequestFormParam(HttpMethod.POST,
                    "/salvar", formParams);
            assertEquals(response.statusCode(), 302);
        }
    }

    private Response buscarPor(String item){
        Response response = RestAssurredUtil.
                doRequestWithBodyParam(HttpMethod.POST, "/buscaLancamentos", item);
        context.setContext(LANCAMENTOS, extractListFromResponse(response));
        return response;
    }

    public List<Lancamento> extractListFromResponse(Response response) {
        return JsonPath.with( response.asInputStream())
                .getList("lancamentos", Lancamento.class);
    }
}
