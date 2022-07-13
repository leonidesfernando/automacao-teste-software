package br.com.home.lab.softwaretesting.automation.modelo;

import br.com.home.lab.softwaretesting.automation.modelo.converter.CategoriaDeserialize;
import br.com.home.lab.softwaretesting.automation.modelo.converter.MoneyDeserialize;
import br.com.home.lab.softwaretesting.automation.modelo.converter.TipoLancamentoDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(of = {"id"})
public class Lancamento {
    private final String DD_MM_YYYY = "dd/MM/yyyy";

    @Getter @Setter
    private long id;

    @Getter @Setter
    private String descricao;

    @JsonDeserialize(using = MoneyDeserialize.class)
    @Getter @Setter
    private BigDecimal valor;

    @JsonFormat(pattern = DD_MM_YYYY)
    @Getter @Setter
    private Date dataLancamento;

    @JsonDeserialize(using = TipoLancamentoDeserialize.class)
    @Getter @Setter
    private TipoLancamento tipoLancamento = TipoLancamento.SAIDA;

    @JsonDeserialize(using = CategoriaDeserialize.class)
    @Getter @Setter
    private Categoria categoria;
}