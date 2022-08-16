package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;

public class LancamentoTest extends BaseSeleniumTest {

    private ListaLancamentosAction listaLancamentosAction;
    public static final String DESCRICAO = "descricao";

    private static final List<Categoria> categrias = Arrays.asList(Categoria.values());
    private static final Map<List<Categoria>, List<TipoLancamento>> tiposLancamento;
    static {

        tiposLancamento = new HashMap<>();
        tiposLancamento.put(Arrays.asList(Categoria.INVESTIMENTOS, Categoria.OUTROS),
                Collections.singletonList(TipoLancamento.TRANSF));

        tiposLancamento.put(Arrays.asList(Categoria.SALARIO, Categoria.OUTROS),
                Collections.singletonList(TipoLancamento.RENDA));

        tiposLancamento.put(Stream.of(Categoria.values())
                        .filter(c -> c != Categoria.INVESTIMENTOS && c != Categoria.SALARIO)
                        .collect(Collectors.toList()),
                Arrays.asList(TipoLancamento.DESPESA));
    }

    @Test(dependsOnMethods = "access")
    public void criaLancamento(ITestContext context){
        String description = getDescription();
        BigDecimal value = getValorLancamento();
        String date = DataGen.strDateCurrentMonth();
        Categoria categoria = getCategoria();
        TipoLancamento tipoLancamento = getTipoLancamento(categoria);
        listaLancamentosAction = new ListaLancamentosAction(webDriver);
        listaLancamentosAction.novoLancamento()
                .and()
                .salvaLancamento(description, value,
                        date, tipoLancamento, categoria);

        context.setAttribute(DESCRICAO, description);
        assertTrue(listaLancamentosAction.existeLancamento(description, date, tipoLancamento));
    }

    @Test(dependsOnMethods = "criaLancamento")
    public void editaLancamento(ITestContext context){
        String sufixoEdicao = " EDITADO Selenium";
        String descricao = getContextAttribute(DESCRICAO, context);
        listaLancamentosAction.abreLancamentoParaEdicao()
                .and()
                .setDescricao(descricao + sufixoEdicao)
                .then()
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

    private TipoLancamento getTipoLancamento(Categoria categoria){
        for(Map.Entry<List<Categoria>, List<TipoLancamento>> entry : tiposLancamento.entrySet()){
            if(entry.getKey().contains(categoria)){
                return getAny(entry.getValue());
            }
        }
        throw new IllegalStateException("Does not exists 'TipoLancamento' for this category " + categoria);
    }

    private Categoria getCategoria(){
        return getAny(categrias);
    }

    private <T> T getAny(List<T> list){
        int n = list.size();
        int index = DataGen.number(n);
        if(index == n){
            index--;
        }
        return list.get(index);
    }
}


