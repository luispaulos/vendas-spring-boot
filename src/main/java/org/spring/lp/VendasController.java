package org.spring.lp;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.entity.domain.repositorio.ClienteJDBCRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VendasController {

    @Value("${application.name}")
    private String applicationName;

    @Cachorro
    private Animal animal;

    @Bean
    public CommandLineRunner inserirClientes(@Autowired ClienteJDBCRepositorio repositorio){
        return args ->{
            repositorio.salvar(new Cliente("Luis Paulo"));
            repositorio.salvar(new Cliente("Aline"));

            List<Cliente> todos = repositorio.obterTodos();
            todos.forEach(System.out::println);
        };
    }

    @Bean(name = "executarAnimal")
    public CommandLineRunner executar(){
        return args -> this.animal.fazerBarulho();
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World Spring Boot Starter";
    }

    @GetMapping("/sistema")
    public String nomeAplicacao(){
        return applicationName;
    }

    @GetMapping("/clientes")
    public List<String> todosClientes(@Autowired ClienteJDBCRepositorio clienteRepositorio){
        List<Cliente> todos = clienteRepositorio.obterTodos();
        List<String> nomes = new ArrayList<>();
        todos.forEach(cliente -> nomes.add(cliente.getNome()));
        return nomes;
    }
}
