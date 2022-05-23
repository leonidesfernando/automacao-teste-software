package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo;

public enum TipoLancamento {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private TipoLancamento(final String descricao){
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }
}
