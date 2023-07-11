package br.com.home.lab.softwaretesting.automation.selenium.webdriver;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.action.ListaLancamentosAction;
import br.com.home.lab.softwaretesting.automation.selenium.webdriver.model.Entry;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Regression Tests Epic")
@Feature("CRUD entries by Selenium WebDriver")
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


    @Step("Initializing the context")
    @BeforeAll
    protected void setUp() {
        context.setContext(ENTRIES, new LinkedBlockingQueue<Entry>());
    }


    @Step("Performing log into with credentials from configurations")
    @Test
    @Order(1)
    void loginLancamentos() {
        super.login();
    }

    @Step("Registering entries by dynamic data")
    @Order(2)
    @RepeatedTest(3)
    void criaLancamento() {
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

    @Step("Searching by description from the context")
    @Test
    @Order(3)
    void buscaPorDescricao() {
        Entry entry = getEntryInContext();
        listaLancamentosAction.goHome();
        listaLancamentosAction.buscaPor(entry.description());
        listaLancamentosAction.buscaLancamentoPorPaginaDescricao(entry.description());
        listaLancamentosAction.checkEntryExists(entry.description(), entry.entryDate(), entry.type());
    }

    @Issue("It's not searching the item to be edited. It must be fixed")
    @Step("Editing an entry existing in the context")
    @Test
    @Order(4)
    void editaLancamento() {
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

    @Step("Removing the entry by description collected from the context")
    @Test
    @Order(5)
    void removeLancamento() {
        Entry entry = getEntryInContext();
        listaLancamentosAction.goHome();
        listaLancamentosAction.removeLancamento(entry.description());
    }

    @Step("Performing log out")
    @Test
    @Order(6)
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
