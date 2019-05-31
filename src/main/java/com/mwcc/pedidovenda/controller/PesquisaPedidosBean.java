package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.model.StatusPedido;
import com.mwcc.pedidovenda.repository.PedidoRepository;
import com.mwcc.pedidovenda.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PedidoFilter filter;
	private List<Pedido> pedidosFiltrados;
	
	@Inject
	private PedidoRepository pedidoRepository;

	public PesquisaPedidosBean() {
		filter = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}

	public void pesquisar() {
		pedidosFiltrados = pedidoRepository.filtrados(filter);
	}

	public PedidoFilter getFilter() {
		return filter;
	}

	public void setFilter(PedidoFilter filter) {
		this.filter = filter;
	}
	
	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}

	public void setPedidosFiltrados(List<Pedido> pedidosFiltrados) {
		this.pedidosFiltrados = pedidosFiltrados;
	}
	
	
}
