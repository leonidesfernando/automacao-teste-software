package br.com.home.lab.softwaretesting.automation.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Categoria {

    @JsonProperty("Alimentação")
    ALIMENTACAO("Alimentação"),
    @JsonProperty("Salário")
    SALARIO("Salário"),
    @JsonProperty("Lazer")
    LAZER("Lazer"),
    @JsonProperty("Telefone & Internet")
    TELEFONE_INTERNET("Telefone & Internet"),
    @JsonProperty("Carro")
    CARRO("Carro"),
    @JsonProperty("Empréstimo")
    EMPRESTIMO("Empréstimo"),
    @JsonProperty("Investimentos")
    INVESTIMENTOS("Investimentos"),
    @JsonProperty("Outros")
    OUTROS("Outros");


    @Getter
    private String nome;
}
