package org.spring.lp.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.spring.lp.VendasApplication;
import org.spring.lp.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    //obtem propriedade do arquivo application.properties
    @Value("${security.jwt.expiracao}")
    private String expiracao;

    //obtem propriedade do arquivo application.properties
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    /**
     * Método responsável por gerar um token a partir dos dados do Usuário
     * @param usuario
     * @return
     */
    public String gerarToken(Usuario usuario){
        //obtem o tempo em minutos definido no arquivo de propriedades
        long expTime = Long.valueOf(expiracao);
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(expTime);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date dateTime = Date.from(instant);

        return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(dateTime)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService bean = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("lp").build();
        String token = bean.gerarToken(usuario);
        System.out.println(token);
    }
}
