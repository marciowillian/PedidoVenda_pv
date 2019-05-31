package com.mwcc.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.repository.ProdutoRepository;

public class PesquisaProdutoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoRepository produtos;
	
	
	
	/*public List<Produto> produtosSalvos(){
		return produtos.buscarTodos();
	}*/

}
