package br.com.home.lab.softwaretesting.automation.restassured;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.Lancamento;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static br.com.home.lab.softwaretesting.automation.util.Constants.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class ControllerTest {

    private static final String DESCRIPTION_TEST = "DESCRIPTION_TEST";
    private static final String ID_TO_USE = "ID_TO_USE";

    @Test
    public void salvarTest(ITestContext context) {
        String descricao = new StringJoiner(" ")
                .add("Assured Rest")
                .add(DataGen.productName())
                .add(LocalDateTime.now().toString()).toString();

        Categoria[] categorias = Categoria.values();
        long factor = (System.currentTimeMillis()/1000) % categorias.length;
        Map<String, String> formParams = new HashMap<>();
        formParams.put(DESCRICAO, descricao);
        formParams.put(VALOR, String.valueOf(DataGen.moneyValue()));
        formParams.put(DATA_LANCAMENTO, DataGen.strDate());
        formParams.put(TIPO_LANCAMENTO, (factor/2) > 3 ? "RENDA" : "DESPESA");
        formParams.put(CATEGORIA, categorias[(int)factor].name());

        Response response = RestAssurredUtil.doRequestFormParam(HttpMethod.POST,
                "/salvar", formParams);
        assertEquals(response.statusCode(), 302);
        context.setAttribute(DESCRIPTION_TEST, descricao);
    }

    @SneakyThrows
    @Test(dependsOnMethods = "salvarTest")
    public void buscandoComPostTest(ITestContext context) {
        String descricao = context.getAttribute(DESCRIPTION_TEST).toString();
        Response response = RestAssurredUtil.
                doRequestWithBodyParam(HttpMethod.POST, "/buscaLancamentos", descricao);
        List<Lancamento> lancamentos =  JsonPath.with( response.asInputStream())
                .getList("lancamentos", Lancamento.class);

        assertEquals(lancamentos.size(), 1);
        context.setAttribute(ID_TO_USE, lancamentos.get(0).getId());
    }

    @Test(dependsOnMethods = {"salvarTest", "buscandoComPostTest"})
    public void editarTest(ITestContext context) {
        int id = Integer.parseInt(context.getAttribute(ID_TO_USE).toString());
        Pair<String,String> param = new Pair("id", id);
        Response response = RestAssurredUtil.doGetWithPathParam(param, "/editar/{id}");
        String html = response.body().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        String titulo = xmlPath.getString("html.body.div.div.div.h4");
        assertEquals(titulo, "Cadastro de Lançamento");
    }

    @Test(dependsOnMethods = {"buscandoComPostTest", "salvarTest", "editarTest"})
    public void removeTest(ITestContext context) {
        int id = Integer.parseInt(context.getAttribute(ID_TO_USE).toString());
        Pair<String, String> param = new Pair("id", id);
        Response response = RestAssurredUtil.doDeleteWithParam(param, "/remover/{id}");
        assertEquals(response.getStatusCode(), 302);
    }
}
