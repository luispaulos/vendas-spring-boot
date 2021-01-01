package org.spring.lp.rest.controller;

import org.spring.lp.exception.PedidoNaoEncontradoException;
import org.spring.lp.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegraNegocioException(RegraNegocioException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleMethodArgNotValidException(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
    }

    @ExceptionHandler(AuthorizationServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAuthorizationServiceException(AuthorizationServiceException ex){
        return ex.getMessage();
    }
}
