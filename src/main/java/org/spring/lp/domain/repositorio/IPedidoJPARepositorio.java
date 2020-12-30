package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IPedidoJPARepositorio extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Transactional
    void deleteByClienteId(Integer id);


    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    public Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
