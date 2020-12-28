package org.spring.lp.rest.controller;

import org.spring.lp.service.PedidoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    //dependência injetada via construtor
    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }
}
