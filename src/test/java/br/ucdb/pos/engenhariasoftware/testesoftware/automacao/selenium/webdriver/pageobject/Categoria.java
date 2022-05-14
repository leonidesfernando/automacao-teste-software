package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import lombok.Getter;

public enum Categoria {

    ALIMENTACAO("Alimentação"),
    SALARIO("Salário"),
    LAZER("Lazer"),
    TELEFONE_INTERNET("Telefone & Internet"),
    CARRO("Carro"),
    EMPRESTIMO("Empréstimo"),
    INVESTIMENTOS("Investimentos"),
    OUTROS("Outros");

    @Getter
    private String nome;

    private Categoria(String nome){
        this.nome = nome;
    }
}
