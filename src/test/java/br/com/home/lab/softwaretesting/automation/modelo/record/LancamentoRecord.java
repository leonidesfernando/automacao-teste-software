package br.com.home.lab.softwaretesting.automation.modelo.record;

import br.com.home.lab.softwaretesting.automation.modelo.Categoria;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;

public record LancamentoRecord(
        long id,
        String descricao,
        String valor,
        String dataLancamento,
        TipoLancamento tipoLancamento,
        Categoria categoria) {
}
