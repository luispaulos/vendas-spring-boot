package org.spring.lp.rest.controller;

import io.swagger.annotations.*;
import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.repositorio.IClienteJPARepositorio;
import org.spring.lp.domain.repositorio.IPedidoJPARepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/clientes"})
@Api("API do Cliente")
public class ClienteController {

    @Autowired
    private IClienteJPARepositorio clienteJPARepositorio;

    @Autowired
    private IPedidoJPARepositorio pedidoJPARepositorio;

    @GetMapping("/{id}")
    @ApiOperation("Obtém o Cliente pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado com o ID informado")
    })
    public Cliente getClienteById(@PathVariable("id") @ApiParam("ID do Cliente") Integer id){
        return clienteJPARepositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping("/all")
    public List<Cliente> todosClientes(){
        return clienteJPARepositorio.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salva um novo Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo"),
            @ApiResponse(code = 400, message = "Erro de validação nos dados do Cliente")
    })
    public Cliente salvar(@RequestBody @Valid @ApiParam("Cliente com nome e cpf") Cliente cliente){
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
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){
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
