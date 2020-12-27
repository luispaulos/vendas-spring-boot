package org.spring.lp.domain.entity.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IClienteJPARepositorio extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);
}
