package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.Entry;
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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LancamentoTest extends BaseSeleniumTest {

    private ListaLancamentosAction listaLancamentosAction;
    public static final String ENTRIES = "entries";

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

    @BeforeClass
    protected void setUp() {
        context.setContext(ENTRIES, new LinkedBlockingQueue<Entry>());
    }

    @Test
    public void loginLancamentos() {
        super.login();
    }

    @Test
    @Order(2)
    public void criaLancamento() {
        for (int i = 0; i < 3; i++) {
            String description = getDescription();
            BigDecimal value = getValorLancamento();
            String date = DataGen.strDateCurrentMonth();
            Categoria categoria = getCategoria();
            TipoLancamento tipoLancamento = getTipoLancamento(categoria);
            listaLancamentosAction = new ListaLancamentosAction(getWebDriver());
            listaLancamentosAction.novoLancamento()
                    .and()
                    .salvaLancamento(description, value,
                            date, tipoLancamento, categoria);

            assertTrue(listaLancamentosAction.existeLancamento(description, date, tipoLancamento));
            setEntryInContext(new Entry(description, date, tipoLancamento));
        }
    }

    @Test
    @Order(3)
    public void buscaPorDescricao() {
        Entry entry = getEntryInContext();
        listaLancamentosAction.goHome();
        listaLancamentosAction.buscaPor(entry.description());
        listaLancamentosAction.buscaLancamentoPorPaginaDescricao(entry.description());
        listaLancamentosAction.checkEntryExists(entry.description(), entry.entryDate(), entry.type());
    }

    @Test
    @Order(4)
    public void editaLancamento() {
        String sufixoEdicao = " EDITADO Selenium";
        Entry entry = getEntryInContext();
        final String newDescription = entry.description() + sufixoEdicao;
        listaLancamentosAction.goHome();
        listaLancamentosAction.abreLancamentoParaEdicao()
                .and()
                .setDescricao(newDescription)
                .then()
                .salvaLancamento();

        assertTrue(listaLancamentosAction.existeLancamentoPorDescricao(newDescription),
                "Deveria existir o lancamento que foi editado " + (newDescription));
    }

    @Test
    @Order(5)
    public void removeLancamento() {
        Entry entry = getEntryInContext();
        listaLancamentosAction.goHome();
        listaLancamentosAction.removeLancamento(entry.description());
    }

    @Test
    @Order(6)
    public void logout() {
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

    private <T> T getAny(List<T> list) {
        int n = list.size();
        int index = DataGen.number(n - 1);
        return list.get(index);
    }

    private Entry getEntryInContext() {
        Entry entry = getEntries().poll();
        Objects.requireNonNull(entry);
        return entry;
    }

    private void setEntryInContext(Entry entry) {
        Objects.requireNonNull(entry);
        getEntries().add(entry);
    }

    private Queue<Entry> getEntries() {
        return context.get(ENTRIES);
    }
}
