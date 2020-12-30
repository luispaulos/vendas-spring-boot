package org.spring.lp.rest.controller;

import org.spring.lp.domain.entity.ItemPedido;
import org.spring.lp.domain.entity.Pedido;
import org.spring.lp.rest.dto.InformacaoItemPedidoDTO;
import org.spring.lp.rest.dto.InformacaoPedidoDTO;
import org.spring.lp.rest.dto.PedidoDTO;
import org.spring.lp.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    //dependência injetada via construtor
    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer salvar(@RequestBody PedidoDTO pedidoDTO){
        Pedido pedido = service.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacaoPedidoDTO getById(@PathVariable Integer id){
        return service.obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacaoPedidoDTO converter(Pedido p) {
        return InformacaoPedidoDTO.builder()
                .codigo(p.getId())
                .cpf(p.getCliente().getCpf())
                .data(p.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .nomeCliente(p.getCliente().getNome())
                .total(p.getTotal())
                .status(p.getStatus().name())
                .itens(converterItens(p.getItens())).build();
    }

    private List<InformacaoItemPedidoDTO> converterItens(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(item ->
            InformacaoItemPedidoDTO.builder()
                    .descricaoProduto(item.getProduto().getDescricao())
                    .precoUnitario(item.getProduto().getPreco())
                    .quantidade(item.getQuantidade()).build()
        ).collect(Collectors.toList());
    }
}
