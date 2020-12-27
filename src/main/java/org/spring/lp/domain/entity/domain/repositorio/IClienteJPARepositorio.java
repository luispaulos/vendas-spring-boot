package org.spring.lp.domain.entity.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IClienteJPARepositorio extends JpaRepository<Cliente, Integer> {

    //utiliza a convenção findBy criando um Query Method montando a seguinte consulta:
    //select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    boolean existsByNome(String nome);
}
