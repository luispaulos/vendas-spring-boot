package org.spring.lp.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha inválida!");
    }
}
