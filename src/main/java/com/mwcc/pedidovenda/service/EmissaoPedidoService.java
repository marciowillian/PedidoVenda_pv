package com.mwcc.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.model.StatusPedido;
import com.mwcc.pedidovenda.repository.PedidoRepository;

public class EmissaoPedidoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EstoqueService estoqueService;
	@Inject
	private PedidoRepository pedidoRepository;
	@Inject
	private CadastroPedidoService cadastroPedidoService;

	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);
		
		if(pedido.isNaoEmissivel()) {
			throw new NegocioException("Pedido nãp pode ser emitido com status "
		+ pedido.getStatusPedido().getDescricao() + ".");
		}
		
		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatusPedido(StatusPedido.EMITIDO);
		pedido = this.pedidoRepository.guardar(pedido);
		return pedido;
	}

}
