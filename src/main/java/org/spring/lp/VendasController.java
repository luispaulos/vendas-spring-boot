package org.spring.lp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendasController {

    @Autowired
    @Qualifier(value = "applicationName")
    public String applicationName;

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World Spring Boot Starter";
    }

    @GetMapping("/sistema")
    public String nomeAplicacao(){
        return applicationName;
    }
}
