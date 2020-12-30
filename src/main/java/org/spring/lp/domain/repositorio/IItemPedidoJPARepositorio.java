package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemPedidoJPARepositorio extends JpaRepository<ItemPedido, Integer> {
}
