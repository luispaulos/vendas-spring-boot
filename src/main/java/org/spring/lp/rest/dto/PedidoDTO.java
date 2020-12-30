package org.spring.lp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @NotNull(message = "Campo Cliente é obrigatório.")
    private Integer cliente;
    @NotNull(message = "Campo Total é obrigatório.")
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;

}
