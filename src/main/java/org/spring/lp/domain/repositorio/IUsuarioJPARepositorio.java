package org.spring.lp.domain.repositorio;

import org.spring.lp.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioJPARepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
