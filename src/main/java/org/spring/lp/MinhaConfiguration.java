package org.spring.lp;

import org.spring.lp.annotations.Development;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Development
public class MinhaConfiguration {

    @Bean
    public CommandLineRunner executar(){
        return args -> System.out.println("Rodando a Configuração de Desenvolvimento.");
    }

}
