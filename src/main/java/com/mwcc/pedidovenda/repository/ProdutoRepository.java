package com.mwcc.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.repository.filter.ProdutoFilter;
import com.mwcc.pedidovenda.service.NegocioException;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class ProdutoRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto guardar(Produto produto) {

		return manager.merge(produto);

	}
	
	@Transactional
	public void remover(Produto produto) {
		try {
			produto = porId(produto.getId());
			manager.remove(produto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído.");
		}
	}

	public List<Produto> buscarTodos() {

		return manager.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto buscaPorSKU(String sku) {
		try {
			return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
					.setParameter("sku", sku.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> filtrados(ProdutoFilter filter){
		//Session session = (Session) manager.getDelegate();
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);
		
		if(StringUtils.isNotBlank(filter.getSku())) {
		criteria.add(Restrictions.eq("sku", filter.getSku())); 
		}
		
		if(StringUtils.isNotBlank(filter.getNome())) {
			criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Produto porId(Long id) {
			
		return manager.find(Produto.class, id);
	}

}
