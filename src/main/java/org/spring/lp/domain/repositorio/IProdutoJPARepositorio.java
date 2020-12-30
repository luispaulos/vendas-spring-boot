package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutoJPARepositorio extends JpaRepository<Produto, Integer> {
}
