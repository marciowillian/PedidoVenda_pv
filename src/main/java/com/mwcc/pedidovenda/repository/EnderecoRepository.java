package com.mwcc.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mwcc.pedidovenda.model.Endereco;

public class EnderecoRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public List<Endereco> buscarTodos(){
		return manager.createQuery("From Endereco", Endereco.class).getResultList();
	}

	public Endereco porId(Long id) {
		return manager.find(Endereco.class, id);
	}
}
