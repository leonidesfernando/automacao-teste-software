package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action.LancamentoAction;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action.ListaLancamentosAction;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.util.DataGen;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import static org.testng.Assert.assertTrue;

public class LancamentoTest extends BaseSeleniumTest {

    private ListaLancamentosAction listaLancamentosAction;
    public static final String DESCRICAO = "descricao";

    @Test(dependsOnMethods = "access")
    public void criaLancamento(ITestContext context){
        listaLancamentosAction = new ListaLancamentosAction(webDriver);
        LancamentoAction lancamentoAction = listaLancamentosAction.novoLancamento();
        String description = getDescription();
        BigDecimal value = getValorLancamento();
        String date = DataGen.strDateCurrentMonth();
        lancamentoAction.salvaLancamento(description, value,
                date, TipoLancamento.SAIDA, Categoria.LAZER);
        context.setAttribute(DESCRICAO, description);
        assertTrue(listaLancamentosAction.existeLancamento(description, date, TipoLancamento.SAIDA));
    }

    @Test(dependsOnMethods = "criaLancamento")
    public void editaLancamento(ITestContext context){
        String sufixoEdicao = " EDITADO Selenium";
        String descricao = getContextAttribute(DESCRICAO, context);
        listaLancamentosAction.abreLancamentoParaEdicao()
                .setDescricao(descricao + sufixoEdicao)
                .salvaLancamento();
        assertTrue(listaLancamentosAction.existeLancamentoPorDescricao(descricao + sufixoEdicao),
                "Deveria existir o lancamento que foi editado " + (descricao+sufixoEdicao));
        context.setAttribute(DESCRICAO, descricao + sufixoEdicao);
    }

    @Test(dependsOnMethods = "editaLancamento")
    public void removeLancamento(ITestContext context){
        String descricao = getContextAttribute(DESCRICAO, context);
        listaLancamentosAction.removeLancamento(descricao);
    }

    private String getDescription(){
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy-ss");
        StringJoiner descricaoLancamento = new StringJoiner(" ")
                .add("Lançando saída automatizada")
                .add(DataGen.productName())
                .add(dataHora.format(formatoLancamento));
        return descricaoLancamento.toString();
    }

    private BigDecimal getValorLancamento() {

        return BigDecimal.valueOf(DataGen.moneyValue())
                .setScale(2, RoundingMode.HALF_UP);
    }
}


