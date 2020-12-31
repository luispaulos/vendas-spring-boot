package org.spring.lp.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CredenciaisDTO {

    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;

    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;
}
