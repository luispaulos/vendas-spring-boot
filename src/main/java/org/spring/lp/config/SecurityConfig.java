package org.spring.lp.config;

import org.spring.lp.security.jwt.JwtAuthFilter;
import org.spring.lp.security.jwt.JwtService;
import org.spring.lp.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Classe responsável por realizar a Autenticação e Autorização no spring security
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //injetando a classe responsável por retornar um usuário da aplicação
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, userDetailService);
    }

    //cria um Bean que implementa PasswordEncoder responsável pela critografia da senha dos usuários
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
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("lp")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER");

        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
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
                .antMatchers(HttpMethod.POST, "/usuarios/**")
                .permitAll()
                .anyRequest().authenticated()
                //.hasAuthority("MANTER_USUARIO") - configura Authorities específicas para a URL
                //.permitAll() //permite requisições sem necessidade de autenticação
                .and() //retorna ao objeto rais (http)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //define que a aplicação não manterá usuário logado, ou seja, uma sessão statfull. A sessão do usuário será Stateless a cada requisição, ou seja, não manterá estado.
                //.formLogin(); //cria um formulário de login padrão
                //.httpBasic(); //cria a autenticação Basic Authentication enviando as credenciais pelo header da requisição
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); //configura para que o JwtAuthFilter seja executado antes do filtro padrão do Spring chamado UsernamePasswordAuthenticationFilter
        //.formLogin("/meu-login.jsp") -- configura um formulário de login personalizado
    }

    /**
     * Método onde pode ser configurado url's para serem ignoradas pelo filtro de Segurança do Spring
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }
}
