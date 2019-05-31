package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.mwcc.pedidovenda.model.Categoria;
import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.repository.CategoriaRepository;
import com.mwcc.pedidovenda.service.CadastroProdutoService;
import com.mwcc.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaRepository categorias;

	@Inject
	private CadastroProdutoService cadastroProdutoService;

	private Produto produto;

	private Categoria categoriaPai;

	private List<Categoria> categoriasRaizes;
	private List<Categoria> subcategorias;

	public CadastroProdutoBean() {
		limpar();
	}

	public void salvar() {
		this.produto = cadastroProdutoService.salvar(this.produto);
		limpar();

		FacesUtil.addInfoMessage("Produto salvo com suceso!");
	}

	public void inicializar(ComponentSystemEvent event) {
		if (FacesUtil.isNotPostback()) {
			categoriasRaizes = categorias.raizes();

			if (this.categoriaPai != null) {
				carregarSubcategorias();
			}
		}

	}

	public void carregarSubcategorias() {
		subcategorias = categorias.subcategorias(categoriaPai);
	}

	private void limpar() {
		this.produto = new Produto();
		this.categoriaPai = null;
		this.subcategorias = new ArrayList<>();
		//this.categoriasRaizes = new ArrayList<>();
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {

		this.produto = produto;
		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}

	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	public List<Categoria> getSubcategorias() {
		return subcategorias;
	}

	@NotNull
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public boolean isEditando(){
		return this.produto.getId() != null;
	}
}
