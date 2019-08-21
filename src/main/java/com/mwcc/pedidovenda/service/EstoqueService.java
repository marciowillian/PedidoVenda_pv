package com.mwcc.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mwcc.pedidovenda.model.ItemPedido;
import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.repository.PedidoRepository;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class EstoqueService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidoRepository.porId(pedido.getId());
		
		for(ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
	}

}
