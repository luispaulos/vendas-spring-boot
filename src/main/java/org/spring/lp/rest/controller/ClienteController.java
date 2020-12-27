package org.spring.lp.rest.controller;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.repositorio.IClienteJPARepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClienteController {

    @Autowired
    private IClienteJPARepositorio clienteJPARepositorio;

    @GetMapping("/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable("id") Integer id){
        Optional<Cliente> oCliente = clienteJPARepositorio.findById(id);
        if(oCliente.isPresent()){
            return ResponseEntity.ok(oCliente.get());
        }
        return ResponseEntity.notFound().build();
    }

}
