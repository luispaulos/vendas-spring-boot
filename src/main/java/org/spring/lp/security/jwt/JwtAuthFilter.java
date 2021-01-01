package org.spring.lp.security.jwt;

import org.spring.lp.service.impl.UserDetailServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classe de filtro responsável por interceptar as requisições e validar o token
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserDetailServiceImpl userService;

    public JwtAuthFilter(JwtService jwtService, UserDetailServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //obtem o header Authorization da request
        String authorization = httpServletRequest.getHeader("Authorization");

        //verifica se o header authorization começa com Bearer (padrão de autenticação para token jwt bearer token')
        if (authorization != null && authorization.startsWith("Bearer")) {
            //recupera apenas o token jwt da String
            String token = authorization.split(" ")[1];
            //verifica se o token é valido pelo serviço
            boolean isValid = jwtService.validarToken(token);
            if (isValid) {
                String loginUsuario = jwtService.obterLoginUsuario(token);
                //recupera o usuário e suas credenciais a partir do username obtido do token
                UserDetails userDetails = userService.loadUserByUsername(loginUsuario);
                //cria um objeto para autenticação do usuário
                UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //informa que a autenticação do usuário é uma autenticação web
                userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //coloca o objeto de autenticação com o usuário e suas informações no contexto de segurança do Sring Security
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
