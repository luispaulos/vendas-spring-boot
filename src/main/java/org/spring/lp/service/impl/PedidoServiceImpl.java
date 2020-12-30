package org.spring.lp.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.lp.domain.entity.domain.Cliente;
import org.spring.lp.domain.entity.domain.ItemPedido;
import org.spring.lp.domain.entity.domain.Pedido;
import org.spring.lp.domain.entity.domain.Produto;
import org.spring.lp.domain.repositorio.IClienteJPARepositorio;
import org.spring.lp.domain.repositorio.IItemPedidoJPARepositorio;
import org.spring.lp.domain.repositorio.IPedidoJPARepositorio;
import org.spring.lp.domain.repositorio.IProdutoJPARepositorio;
import org.spring.lp.exception.RegraNegocioException;
import org.spring.lp.rest.dto.ItemPedidoDTO;
import org.spring.lp.rest.dto.PedidoDTO;
import org.spring.lp.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //annotation do lombok que cria construtor com atributos marcados como final
public class PedidoServiceImpl implements PedidoService {

    //dependência injetada via construtor
    //atributos marcados como final são argumentos obrigatórios no construtor para o lombok
    private final IPedidoJPARepositorio pedidoJPARepositorio;
    private final IClienteJPARepositorio clienteJPARepositorio;
    private final IProdutoJPARepositorio produtoJPARepositorio;
    private final IItemPedidoJPARepositorio itemPedidoJPARepositorio;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteJPARepositorio.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Código do Cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setTotal(pedidoDTO.getTotal());

        List<ItemPedido> itens = converterItens(pedido, pedidoDTO.getItens());
        pedidoJPARepositorio.save(pedido);
        itemPedidoJPARepositorio.saveAll(itens);
        pedido.setItens(itens);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoJPARepositorio.findByIdFetchItens(id);
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }
        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtoJPARepositorio.findById(idProduto)
            .orElseThrow(() -> new RegraNegocioException("Código do Produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(dto.getQuantidade());
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
