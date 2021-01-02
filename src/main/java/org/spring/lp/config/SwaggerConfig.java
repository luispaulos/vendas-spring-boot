package org.spring.lp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false) //desabilita mensagens de respostas padrão na documentação
                .select().apis(RequestHandlerSelectors.basePackage("org.spring.lp.rest.controller")) //escaneia os pacotes em busca de definição de APIs
                .paths(PathSelectors.any()).build()
                .securityContexts(Arrays.asList(securityContext())) //configuração para enviar o token via swagger
                .securitySchemes(Arrays.asList(apiKey()))  //configuração para enviar o token via swagger
                .apiInfo(apiInfo());

    }

    /**
     * Método que retorna um objeto ApiInfo com informações da API
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("VENDAS API")
                .description("API do Sistema de Vendas do LP")
                .version("1.0")
                .contact(contact()).build();
    }

    /**
     * Método que retorna um objeto Contact com informações de contato do Desenvolvedor
     *
     * @return
     */
    private Contact contact() {
        return new Contact("Luis Paulo", "https://github.com/luispaulos/vendas-spring-boot", "luispaulo10@gmail.com");
    }

    /**
     * Configuração para enviar o token via swagger
     *
     * @return
     */
    public ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    /**
     * Configuração para enviar o token via swagger
     *
     * @return
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    /**
     * Configuração para enviar o token via swagger
     *
     * @return
     */
    public List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("glocal", "accessEveryThing");
        AuthorizationScope[] scopes = new AuthorizationScope[]{authorizationScope};
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(new SecurityReference("JWT", scopes));
        return auths;
    }
}
