package br.com.home.lab.softwaretesting.automation.modelo.record;

public record LancamentoRecord(
        long id,
        String descricao,
        String valor,
        String dataLancamento,
        String tipoLancamento,
        String categoria) {
}
