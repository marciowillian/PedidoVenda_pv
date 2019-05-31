package com.mwcc.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Query;

import com.mwcc.pedidovenda.model.Permissao;

public class PermissaoRepository implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public List<Permissao> buscarTodos(){
		return manager.createQuery("From Permissao", Permissao.class).getResultList();
	}
	
	/*public List<Permissao> permissoesCadastradas(Long id){
		
		Query q = manager.createNativeQuery("SELECT * FROM Grupo where ");
		List<Permissao> lista = q.list();
		
		return q.getRes;
	}*/
}
