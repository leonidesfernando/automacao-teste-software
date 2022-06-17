package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoLancamento {

    @JsonProperty("Entrada")
    ENTRADA("Entrada"),
    @JsonProperty("SAIDA")
    SAIDA("Saída");

    @Getter
    private String descricao;
}
