package org.spring.lp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe responsável por realizar a Autenticação e Autorização no spring security
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Método responsável por realizar a Autenticação e colocar os usuários dentro do contexto do Spring Security.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Configuração de usuário em memória para teste inicial
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("lp")
                .password(passwordEncoder().encode("123"))
                .roles("USER");
    }

    /**
     * Método responsável por realizar a Autorização
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//desabilita o mecanismo de proteção csrf de confiança do servidor para web-browsers visto que se trata de uma API Rest
                .authorizeRequests() //habilita a autorização das requisições
                .antMatchers("/clientes/**") //para as seguintes URLs
                .hasAnyRole("USER", "ADMIN") // configura Roles específicas para a URL
                .antMatchers("/produtos/**")
                .hasRole("ADMIN")
                .antMatchers("/pedidos/**")
                .hasAnyRole("USER", "ADMIN")
                //.hasAuthority("MANTER_USUARIO") - configura Authorities específicas para a URL
                //.permitAll() //permite requisições sem necessidade de autenticação
                .and() //retorna ao objeto rais (http)
                .formLogin(); //cria um formulário de login padrão
        //.formLogin("/meu-login.jsp") -- configura um formulário de login personalizado
    }
}
