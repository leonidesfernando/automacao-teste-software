package br.com.home.lab.softwaretesting.automation.modelo;

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
