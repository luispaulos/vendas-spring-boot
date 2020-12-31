package org.spring.lp.rest.controller;

import lombok.RequiredArgsConstructor;
import org.spring.lp.domain.entity.Usuario;
import org.spring.lp.exception.SenhaInvalidaException;
import org.spring.lp.rest.dto.CredenciaisDTO;
import org.spring.lp.rest.dto.TokenDTO;
import org.spring.lp.security.jwt.JwtService;
import org.spring.lp.service.impl.UserDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UserDetailServiceImpl service;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        return service.salvar(usuario);
    }

    @PostMapping
    @RequestMapping("/auth")
    public TokenDTO autenticar(@RequestBody @Valid CredenciaisDTO credenciaisDTO){
        try{
            UserDetails userDetails = service.autenticar(credenciaisDTO.getLogin(), credenciaisDTO.getSenha());
            Usuario usuario = Usuario.builder().login(userDetails.getUsername()).build();
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        }catch (SenhaInvalidaException | UsernameNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }

    }
}
