package org.spring.lp.service.impl;

import org.spring.lp.domain.repositorio.IPedidoJPARepositorio;
import org.spring.lp.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    //dependência injetada via construtor
    private IPedidoJPARepositorio repositorio;

    public PedidoServiceImpl(IPedidoJPARepositorio repositorio) {
        this.repositorio = repositorio;
    }
}
