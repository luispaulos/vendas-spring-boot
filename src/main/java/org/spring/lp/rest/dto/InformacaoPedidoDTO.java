package org.spring.lp.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data //gera getts, setts, toString, Equals e HashCode
@AllArgsConstructor //gera um construtor com todos os atributos como argumento
@NoArgsConstructor //gera um construtor sem argumentos
@Builder //cria um método builder() utilizando o design pattern Builder
public class InformacaoPedidoDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String data;
    private String status;
    private List<InformacaoItemPedidoDTO> itens;

}
