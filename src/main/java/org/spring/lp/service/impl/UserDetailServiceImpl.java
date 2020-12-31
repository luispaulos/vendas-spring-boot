package org.spring.lp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementação da interface UserDetailService do Spring responsável por retornar um Usuário válido
 * para o processo de autenticação realizado pela classe SecurityConfig
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    //injeta o bean BCryptPasswordEncoder definido na classe SecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método que deve retornar um  Usuário válido que implemente a interface UserDetails do Spring
     * de acordo com o 'username' passado como parâmetro
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals("lp")) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        //retorna uma instância de usuário em memória da classe User do Spring
        return User.builder().username("lp")
                .password(passwordEncoder.encode("123"))//deve passar uma senha criptografada com o Bean que implementa o PasswordEncoder
                .roles("USER", "ADMIN").build();
    }
}
