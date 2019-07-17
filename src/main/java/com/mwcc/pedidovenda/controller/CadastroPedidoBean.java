package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.model.EnderecoEntrega;
import com.mwcc.pedidovenda.model.FormaPagamento;
import com.mwcc.pedidovenda.model.ItemPedido;
import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.model.Usuario;
import com.mwcc.pedidovenda.repository.ClienteRepository;
import com.mwcc.pedidovenda.repository.ProdutoRepository;
import com.mwcc.pedidovenda.repository.UsuarioReporitory;
import com.mwcc.pedidovenda.service.CadastroPedidoService;
import com.mwcc.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> vendedores;

	private Pedido pedido;
	private Produto produtoLinhaEditavel;

	@Inject
	private UsuarioReporitory usuarioReporitory;
	@Inject
	private ClienteRepository clienteRepository;
	@Inject
	private ProdutoRepository produtoRepository;
	@Inject
	private CadastroPedidoService cadastroPedidoService;

	public CadastroPedidoBean() {

	}

	@PostConstruct
	public void init() {
		limpar();
	}

	public void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}

	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			this.vendedores = usuarioReporitory.vendedores();

			//this.pedido.adicionarItemVazio();

			this.recalcularPedido();
		}
	}

	public void carregarProdutoLinhaEditavel() {
		ItemPedido itemPedido = this.pedido.getItens().get(0);
		
		if(this.produtoLinhaEditavel != null) {
			itemPedido.setProduto(this.produtoLinhaEditavel);
			itemPedido.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
			
			this.pedido.adicionarItemVazio();
			this.produtoLinhaEditavel = null;
			this.pedido.recalcularValorTotal();
		}
	}
	
	public List<Cliente> completarCliente(String nome) {
		return clienteRepository.porNome(nome);

	}
	
	public List<Produto> completarProduto(String nome){
		return this.produtoRepository.porNome(nome);
	}

	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	public void salvar() {
		this.pedido = this.cadastroPedidoService.salvar(this.pedido);

		FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
	}

	public boolean isEditando() {
		return pedido.getId() != null;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<Usuario> vendedores) {
		this.vendedores = vendedores;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

}
