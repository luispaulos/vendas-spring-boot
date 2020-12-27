package org.spring.lp.domain.repositorio;

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
    public Cliente atualizar(Cliente cliente){
        return entityManager.merge(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos(){
        return entityManager.createQuery("from Cliente").getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
        return  entityManager.createQuery("select c from Cliente c where c.nome like :nome", Cliente.class)
                .setParameter("nome", "%" + nome + "%").getResultList();
    }

    @Transactional
    public void deletar(Cliente cliente){
        if(!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }
}
