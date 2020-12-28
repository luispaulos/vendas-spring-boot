package org.spring.lp.rest.controller;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.repositorio.IClienteJPARepositorio;
import org.spring.lp.domain.repositorio.IPedidoJPARepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/clientes"})
public class ClienteController {

    @Autowired
    private IClienteJPARepositorio clienteJPARepositorio;

    @Autowired
    private IPedidoJPARepositorio pedidoJPARepositorio;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable("id") Integer id){
        Optional<Cliente> oCliente = clienteJPARepositorio.findById(id);
        if(oCliente.isPresent()){
            return ResponseEntity.ok(oCliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Cliente> todosClientes(){
        return clienteJPARepositorio.findAll();
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity salvar(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clienteJPARepositorio.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deletar(@PathVariable Integer id){
        Optional<Cliente> opCliente = clienteJPARepositorio.findById(id);
        if(opCliente.isPresent()){
            pedidoJPARepositorio.deleteByClienteId(id);
            clienteJPARepositorio.delete(opCliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente){
        return clienteJPARepositorio.findById(id).map(clienteExistente ->
        {
            cliente.setId(clienteExistente.getId());
            clienteJPARepositorio.save(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(()-> ResponseEntity.notFound().build());
    }
}
