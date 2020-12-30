package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IClienteJPARepositorio extends JpaRepository<Cliente, Integer> {

    //utiliza a convenção findBy criando um Query Method montando a seguinte consulta:
    //select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    //utilizando annotation @Query para fazer consulta
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = "select * from cliente c where c.nome like :nome", nativeQuery = true)
    List<Cliente> encontrarPorNomeNativo(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query(value = "delete from Cliente c where c.nome = :nome")
    @Modifying
    @Transactional
    void deletePorNome(@Param("nome") String nome);

    @Query(value = "select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
