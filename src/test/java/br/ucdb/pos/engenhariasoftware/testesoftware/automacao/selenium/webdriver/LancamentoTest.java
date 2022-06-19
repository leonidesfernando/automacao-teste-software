package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.action.ListaLancamentosAction;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.LancamentoPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject.ListaLancamentosPage;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.util.DataGen;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertTrue;

public class LancamentoTest extends BaseSeleniumTest {

    private ListaLancamentosAction listaLancamentosAction;

    public LancamentoTest(WebDriver webDriver) {
        super(webDriver);
        listaLancamentosAction = new ListaLancamentosAction(webDriver);
    }

    @Test
    public void criaLancamento(){
        ListaLancamentosPage listaLancamentosPage = new ListaLancamentosPage(webDriver);
        LancamentoPage lancamentoPage = listaLancamentosPage.acessa()
            .novoLancamento();

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatoLancamento = DateTimeFormatter.ofPattern("dd.MM.yy");
        final String descricaoLancamento = "Lançando saída automatizada "
                + DataGen.productName()
                + dataHora.format(formatoLancamento);
        final BigDecimal valor = getValorLancamento();
        lancamentoPage.cria(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA, Categoria.LAZER);

        assertTrue(listaLancamentosPage.existeLancamento(descricaoLancamento, valor, dataHora, TipoLancamento.SAIDA));
    }

    private BigDecimal getValorLancamento() {

        /*boolean  aplicaVariante = (System.currentTimeMillis() % 3) == 0;
        int fator = 10;
        long mim = 30;
        long max = 900;
        if(aplicaVariante){
            mim /= fator;
            max /= fator;
        }
        return new BigDecimal(( 1 + (Math.random() * (max - mim)))).setScale(2, RoundingMode.HALF_DOWN); */
        return BigDecimal.valueOf(DataGen.moneyValue())
                .setScale(2, RoundingMode.HALF_UP);
    }
    
}


