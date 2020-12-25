package org.spring.lp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendasController {

    @Value("${application.name}")
    private String applicationName;

    @Cachorro
    private Animal animal;

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
}
