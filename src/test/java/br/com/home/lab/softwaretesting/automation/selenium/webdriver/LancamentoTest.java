package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LancamentoTest extends BaseSeleniumTest {

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
                Collections.singletonList(TipoLancamento.DESPESA));
    }

    public LancamentoTest() {
        super();
    }

    //TODO: @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    // https://www.baeldung.com/junit-5-repeated-test

    @Test
    @Order(1)
    void loginLancamentos() {
        super.login();
    }

    @Test
    @Order(2)
    void criaLancamento() {
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

        context.setContext(DESCRICAO, description);
        assertTrue(listaLancamentosAction.existeLancamento(description, date, tipoLancamento));
    }

    @Test
    @Order(3)
    void editaLancamento() {
        String sufixoEdicao = " EDITADO Selenium";
        String descricao = context.get(DESCRICAO);
        listaLancamentosAction.abreLancamentoParaEdicao()
                .and()
                .setDescricao(descricao + sufixoEdicao)
                .then()
                .salvaLancamento();

        assertTrue(listaLancamentosAction.existeLancamentoPorDescricao(descricao + sufixoEdicao),
                "Deveria existir o lancamento que foi editado " + (descricao + sufixoEdicao));
        context.setContext(DESCRICAO, descricao + sufixoEdicao);
    }

    @Test
    @Order(4)
    void removeLancamento() {
        String descricao = context.get(DESCRICAO);
        listaLancamentosAction.removeLancamento(descricao);
    }

    @Test
    @Order(5)
    void logout() {
        super.doLogout();
    }

    private String getDescription() {
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
        int index = DataGen.number(n - 1);
        return list.get(index);
    }
}


