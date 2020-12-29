package org.spring.lp.service;

import org.spring.lp.domain.entity.Pedido;
import org.spring.lp.rest.dto.PedidoDTO;

public interface PedidoService {

    public Pedido salvar(PedidoDTO pedidoDTO);
}
