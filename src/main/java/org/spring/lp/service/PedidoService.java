package org.spring.lp.service;

import org.spring.lp.domain.entity.Pedido;
import org.spring.lp.domain.enums.StatusPedido;
import org.spring.lp.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido status);
}
