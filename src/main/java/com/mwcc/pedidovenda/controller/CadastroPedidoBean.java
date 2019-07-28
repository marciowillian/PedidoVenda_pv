package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

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
import com.mwcc.pedidovenda.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> vendedores;

	private String sku;
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

			this.pedido.adicionarItemVazio();

			this.recalcularPedido();
		}
	}

	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);

		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());

				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

				this.pedido.recalcularValorTotal();
			}
		}
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public List<Cliente> completarCliente(String nome) {
		return clienteRepository.porNome(nome);

	}

	public List<Produto> completarProduto(String nome) {
		return this.produtoRepository.porNome(nome);
	}

	public void atualizarQuantidade(ItemPedido item, int linha) {

		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}

		this.pedido.recalcularValorTotal();
	}

	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}

	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtoRepository.buscaPorSKU(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}

	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	public void salvar() {
		this.pedido.removerItemVazio();
		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally {
			this.pedido.adicionarItemVazio();
		}
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

	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
