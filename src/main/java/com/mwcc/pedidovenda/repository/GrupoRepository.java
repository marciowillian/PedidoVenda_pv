package com.mwcc.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mwcc.pedidovenda.model.Grupo;

public class GrupoRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public List<Grupo> buscarTodos(){
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
	
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}
}
