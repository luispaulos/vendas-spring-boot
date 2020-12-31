package org.spring.lp.service.impl;

import org.spring.lp.domain.entity.Usuario;
import org.spring.lp.domain.repositorio.IUsuarioJPARepositorio;
import org.spring.lp.exception.SenhaInvalidaException;
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

    @Autowired
    private IUsuarioJPARepositorio usuarioJPARepositorio;

    public Usuario salvar(Usuario usuario){
        //criptografa a senha utilizando o Bean passoword encoder definido na classe SecurityConfig
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioJPARepositorio.save(usuario);
    }

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
        //busca o usuário no banco de dados pelo username
        Usuario usuario = usuarioJPARepositorio.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        //configura as Roles do usuário
        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        //retorna uma instância da implementação de UserDetails do Spring com os dados do usuário obtido do banco de dados
        //a senha já está criptografada no banco de dados com o mesmo algoritmo do PasswordEncoder (BCryptPasswordEncoder)
        return User.builder().username(usuario.getLogin()).password(usuario.getSenha()).roles(roles).build();

//        if (!username.equals("lp")) {
//            throw new UsernameNotFoundException("Usuário não encontrado.");
//        }
//        //retorna uma instância de usuário em memória da classe User do Spring
//        return User.builder().username("lp")
//                .password(passwordEncoder.encode("123"))//deve passar uma senha criptografada com o Bean que implementa o PasswordEncoder
//                .roles("USER", "ADMIN").build();
    }

    public UserDetails autenticar(String username, String password){
        //carrega o userDetails com os seus dados do banco de dados
        UserDetails userDetails = loadUserByUsername(username);
        //valida a senha com o PasswordEncoder injetado para o Bean definido na classe SecurityConfig
        boolean senhasBatem = passwordEncoder.matches(password, userDetails.getPassword());
        if(senhasBatem){
            return userDetails;
        }
        throw new SenhaInvalidaException();
    }
}
