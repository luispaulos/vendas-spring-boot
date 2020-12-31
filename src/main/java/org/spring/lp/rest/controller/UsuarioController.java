package org.spring.lp.rest.controller;

import lombok.RequiredArgsConstructor;
import org.spring.lp.domain.entity.Usuario;
import org.spring.lp.service.impl.UserDetailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UserDetailServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        return service.salvar(usuario);
    }
}
