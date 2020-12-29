package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemPedidoJPARepositorio extends JpaRepository<ItemPedido, Integer> {
}
