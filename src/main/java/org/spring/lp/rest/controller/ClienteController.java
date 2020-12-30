package org.spring.lp.rest.controller;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.repositorio.IClienteJPARepositorio;
import org.spring.lp.domain.repositorio.IPedidoJPARepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping({"/clientes"})
public class ClienteController {

    @Autowired
    private IClienteJPARepositorio clienteJPARepositorio;

    @Autowired
    private IPedidoJPARepositorio pedidoJPARepositorio;

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable("id") Integer id){
        return clienteJPARepositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping("/all")
    public List<Cliente> todosClientes(){
        return clienteJPARepositorio.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody Cliente cliente){
        return clienteJPARepositorio.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id){
        clienteJPARepositorio.findById(id).map(c -> {
            pedidoJPARepositorio.deleteByClienteId(id);
            clienteJPARepositorio.delete(c);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente){
        clienteJPARepositorio.findById(id).map(clienteExistente ->
        {
            cliente.setId(clienteExistente.getId());
            clienteJPARepositorio.save(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteJPARepositorio.findAll(example);
    }
}
