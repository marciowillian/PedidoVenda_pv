package com.mwcc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mwcc.pedidovenda.model.Permissao;


public class BuscaPermissoes {

	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		List<Permissao> permissoes = new ArrayList<>();
		
		permissoes = em.createQuery("From Permissao", Permissao.class).getResultList();
		
		for(Permissao p : permissoes ) {
			System.out.println(p.getNome());
		}
		
		em.getTransaction().commit();
		em.close();
		
	}

}
