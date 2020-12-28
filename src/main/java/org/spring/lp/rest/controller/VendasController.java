package org.spring.lp.rest.controller;

import org.spring.lp.Animal;
import org.spring.lp.annotations.Cachorro;
import org.spring.lp.domain.entity.Cliente;
import org.spring.lp.domain.entity.Pedido;
import org.spring.lp.domain.repositorio.ClienteJDBCRepositorio;
import org.spring.lp.domain.repositorio.ClienteJPARepositorio;
import org.spring.lp.domain.repositorio.IClienteJPARepositorio;
import org.spring.lp.domain.repositorio.IPedidoJPARepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class VendasController {

    @Value("${application.name}")
    private String applicationName;

    @Cachorro
    private Animal animal;

    @Autowired
    ClienteJDBCRepositorio clienteRepositorio;

    //@Bean
    public CommandLineRunner inserirClienteEPedido(@Autowired IClienteJPARepositorio clienteJPARepositorio,
                                                   IPedidoJPARepositorio pedidoJPARepositorio){
        return args -> {
            Cliente fulano = new Cliente("Fulano");
            clienteJPARepositorio.save(fulano);

            Pedido p = new Pedido();
            p.setDataPedido(LocalDate.now());
            p.setCliente(fulano);
            p.setTotal(BigDecimal.valueOf(120));

            pedidoJPARepositorio.save(p);

            Cliente c = clienteJPARepositorio.findClienteFetchPedidos(fulano.getId());
            System.out.println("Cliente: " + c.getNome());
            System.out.println("Pedidos:");
            c.getPedidos().forEach(System.out::println);

            System.out.println("Pedidos com findByCliente");
            pedidoJPARepositorio.findByCliente(fulano).forEach(System.out::println);
        };
    }

    //@Bean
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
            System.out.println("Buscando cliente pelo nome findBy");
            repositorio.findByNomeLike("%Aline%").forEach(System.out::println);

            System.out.println("Buscando cliente pelo nome @Query Antes de excluir Por Nome");
            repositorio.encontrarPorNome("%Luis%").forEach(System.out::println);
            System.out.println("Buscando cliente pelo nome @Query Nativo Antes de excluir Por Nome");
            repositorio.encontrarPorNomeNativo("%Luis%").forEach(System.out::println);

            repositorio.deletePorNome("Luis Paulo JPA I atualizado");

            System.out.println("Buscando cliente pelo nome @Query Depois de excluir Por Nome");
            repositorio.encontrarPorNome("%Luis%").forEach(System.out::println);


            System.out.println("Existe a Cliente Aline antes da exclusão? " + repositorio.existsByNome("Aline JPA I atualizado"));
            System.out.println("Excluindo todos os clientes");
            todos.forEach(c -> repositorio.delete(c));
            System.out.println("Existe a Cliente Aline depois da exclusão? " + repositorio.existsByNome("Aline JPA I atualizado"));
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

    //@Bean(name = "executarAnimal")
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

}
