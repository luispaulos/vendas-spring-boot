package org.spring.lp;

import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.entity.domain.repositorio.ClienteJDBCRepositorio;
import org.spring.lp.domain.entity.domain.repositorio.ClienteJPARepositorio;
import org.spring.lp.domain.entity.domain.repositorio.IClienteJPARepositorio;
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
    public CommandLineRunner inserirClientesIJPA(@Autowired IClienteJPARepositorio repositorio){
        return args -> {
            repositorio.save(new Cliente("Luis Paulo JPA"));
            repositorio.save(new Cliente("Aline JPA"));

            System.out.println("Listando todos os clientes");
            List<Cliente> todos = repositorio.findAll();
            todos.forEach(System.out::println);

            System.out.println("atualizando todos os clientes");
            todos.forEach(c -> {
                c.setNome(c.getNome().concat(" I atualizado"));
                repositorio .save(c);
            });
            System.out.println("Listando após atualização");
            todos = repositorio.findAll();
            todos.forEach(System.out::println);
            System.out.println("Buscando cliente pelo nome");
            List<Cliente> clientes = repositorio.findByNomeLike("%JPA I%");
            clientes.forEach(System.out::println);
            System.out.println("Excluindo todos os clientes");
            todos.forEach(c -> repositorio.delete(c));
            System.out.println("Listando após exclusão");
            todos = repositorio.findAll();
            todos.forEach(System.out::println);
        };
    }

    //@Bean
    public CommandLineRunner inserirClientesJPA(@Autowired ClienteJPARepositorio repositorio){
        return args -> {
            repositorio.salvar(new Cliente("Luis Paulo JPA"));
            repositorio.salvar(new Cliente("Aline JPA"));

            System.out.println("Listando todos os clientes");
            List<Cliente> todos = repositorio.obterTodos();
            todos.forEach(System.out::println);

            System.out.println("atualizando todos os clientes");
            todos.forEach(c -> {
                c.setNome(c.getNome().concat(" atualizado"));
                repositorio.atualizar(c);
            });
            System.out.println("Listando após atualização");
            todos = repositorio.obterTodos();
            todos.forEach(System.out::println);
            System.out.println("Buscando cliente pelo nome");
            List<Cliente> clientes = repositorio.buscarPorNome("Luis");
            clientes.forEach(System.out::println);
            System.out.println("Excluindo todos os clientes");
            todos.forEach(c -> repositorio.deletar(c));
            System.out.println("Listando após exclusão");
            todos = repositorio.obterTodos();
            todos.forEach(System.out::println);
        };
    }

    //@Bean
    public CommandLineRunner inserirClientesJDBC(@Autowired ClienteJDBCRepositorio repositorio){
        return args ->{
            repositorio.salvar(new Cliente("Luis Paulo"));
            repositorio.salvar(new Cliente("Aline"));

            System.out.println("Listando todos os clientes");
            List<Cliente> todos = repositorio.obterTodos();
            todos.forEach(System.out::println);

            System.out.println("atualizando todos os clientes");
            todos.forEach(c -> {
                c.setNome(c.getNome().concat(" atualizado"));
                repositorio.atualizar(c);
            });
            System.out.println("Listando após atualização");
            todos = repositorio.obterTodos();
            todos.forEach(System.out::println);
            System.out.println("Buscando cliente pelo nome");
            List<Cliente> clientes = repositorio.buscarPorNome("Luis");
            clientes.forEach(System.out::println);
            System.out.println("Excluindo todos os clientes");
            todos.forEach(c -> repositorio.deletar(c));
            System.out.println("Listando após exclusão");
            todos = repositorio.obterTodos();
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
