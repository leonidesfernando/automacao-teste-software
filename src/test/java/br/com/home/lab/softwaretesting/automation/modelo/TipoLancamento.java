package br.com.home.lab.softwaretesting.automation.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TipoLancamento {

    @JsonProperty("Renda")
    RENDA("Renda"),
    @JsonProperty("Despesa")
    DESPESA("Despesa"),

    @JsonProperty("Transf")
    TRANSF("Transf");


    @Getter
    private String descricao;
}
