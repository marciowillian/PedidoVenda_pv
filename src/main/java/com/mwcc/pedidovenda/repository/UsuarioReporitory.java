package com.mwcc.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mwcc.pedidovenda.model.Usuario;
import com.mwcc.pedidovenda.repository.filter.UsuarioFilter;
import com.mwcc.pedidovenda.service.NegocioException;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class UsuarioReporitory implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Usuario> buscarTodos() {
		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}
	
	public List<Usuario> vendedores(){
		return manager.createQuery("From Usuario", Usuario.class).getResultList();
	}

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisa(Usuario usuario){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		if(StringUtils.isNotBlank(usuario.getNome())) {
			criteria.add(Restrictions.ilike("nome", usuario.getNome(), MatchMode.ANYWHERE));
		}
		
		
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído.");
		}
	}
}
