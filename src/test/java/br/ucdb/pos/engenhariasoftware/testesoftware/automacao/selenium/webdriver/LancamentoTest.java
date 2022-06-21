package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action.LancamentoAction;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action.ListaLancamentosAction;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.util.DataGen;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import static org.testng.Assert.assertTrue;

public class LancamentoTest extends BaseSeleniumTest {

    @Test(dependsOnMethods = "access")
    public void criaLancamento(){
        ListaLancamentosAction listaLancamentosAction = new ListaLancamentosAction(webDriver);
        LancamentoAction lancamentoAction = listaLancamentosAction.novoLancamento();
        String description = getDescription();
        BigDecimal value = getValorLancamento();
        String date = DataGen.strDateCurrentMonth();
        lancamentoAction.cria(description, value,
                date, TipoLancamento.SAIDA, Categoria.LAZER);
        assertTrue(listaLancamentosAction.existeLancamento(description, value, date, TipoLancamento.SAIDA));
    }

    private String getDescription(){
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
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


