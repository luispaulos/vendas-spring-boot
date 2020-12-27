package org.spring.lp.domain.entity.domain.repositorio;

import org.spring.lp.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoJPARepositorio extends JpaRepository<Pedido, Integer> {
}
