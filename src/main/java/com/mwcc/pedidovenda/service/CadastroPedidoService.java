package com.mwcc.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.model.StatusPedido;
import com.mwcc.pedidovenda.repository.PedidoRepository;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		if(pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatusPedido(StatusPedido.ORCAMENTO);
		}
		
		pedido = this.pedidoRepository.guardar(pedido);
		return pedido;
	}

}
