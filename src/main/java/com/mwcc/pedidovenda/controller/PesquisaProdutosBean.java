package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.repository.ProdutoRepository;
import com.mwcc.pedidovenda.repository.filter.ProdutoFilter;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;

	private List<Produto> produtos;
	
	private Produto produtoSelecionado;

	private ProdutoFilter filter;

	public PesquisaProdutosBean() {
		filter = new ProdutoFilter();
		
	}

	public void pesquisar() {
		produtos = produtoRepository.filtrados(filter);
	}

	public void excluir() {
		produtoRepository.remover(produtoSelecionado);
		produtos.remove(produtoSelecionado);
		
		/*FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() + " excluído com sucesso.");*/
		
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, produtoSelecionado.getSku() + " excluído com sucesso!",
				produtoSelecionado.getSku() + " excluído com sucesso!");

		context.addMessage("msgInfo", msg);
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public ProdutoFilter getFilter() {
		return filter;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	
}
