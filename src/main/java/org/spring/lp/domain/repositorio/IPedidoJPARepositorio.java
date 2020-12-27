package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPedidoJPARepositorio extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Transactional
    void deleteByClienteId(Integer id);
}
