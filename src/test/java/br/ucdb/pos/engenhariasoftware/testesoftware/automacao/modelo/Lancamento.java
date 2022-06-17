package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.converter.CategoriaDeserialize;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.converter.MoneyDeserialize;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.converter.TipoLancamentoDeserialize;
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