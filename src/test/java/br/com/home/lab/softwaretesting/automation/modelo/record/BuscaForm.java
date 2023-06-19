package br.com.home.lab.softwaretesting.automation.modelo.record;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record BuscaForm(String itemBusca, boolean searchOnlyCurrentMonth) {
}
