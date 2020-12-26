package org.spring.lp.domain.entity.domain.repositorio;

import org.spring.lp.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ClienteJPARepositorio {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public List<Cliente> obterTodos(){
        return entityManager.createQuery("from Cliente").getResultList();
    }
}
