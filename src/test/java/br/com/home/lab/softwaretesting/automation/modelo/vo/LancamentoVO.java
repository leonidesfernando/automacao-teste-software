package br.com.home.lab.softwaretesting.automation.modelo.vo;


import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class LancamentoVO implements Serializable {
    private long id;
    private String descricao;
    private String valor;
    private String dataLancamento;
    private String tipoLancamento;
    private String categoria;
}

