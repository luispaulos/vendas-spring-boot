package org.spring.lp.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AtualizacaoStatusPedidoDTO {

    @NotEmpty(message = "Campo Status é obrigatório.")
    private String status;
}
