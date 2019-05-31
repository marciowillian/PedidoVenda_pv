package com.mwcc.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.repository.ProdutoRepository;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class CadastroProdutoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtos;

	@Transactional
	public Produto salvar(Produto produto) {
		Produto produtoExistente = produtos.buscaPorSKU(produto.getSku());

		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o SKU informado.");
		}

		return produtos.guardar(produto);
	}

}
