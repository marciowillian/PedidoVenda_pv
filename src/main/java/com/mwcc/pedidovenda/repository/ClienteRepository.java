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

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.model.Usuario;
import com.mwcc.pedidovenda.repository.filter.ClienteFilter;
import com.mwcc.pedidovenda.service.NegocioException;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class ClienteRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}

	@Transactional
	public void remover(Cliente cliente) {
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído.");
		}
	}

	public Cliente porDocReceita(String docReceita) {

		try {
			return manager.createQuery("From Cliente Where doc_receita_federal = :doc", Cliente.class)
					.setParameter("doc", docReceita).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Cliente> porNome(String nome) {
		/*return this.manager.createQuery("From Cliente" + "where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();*/
		
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);
		
		if(StringUtils.isNotBlank(nome)) {
			criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);
	}

	public Cliente limpaCaracteresEspeciais(Cliente cliente) {

		String docReceita = cliente.getDocumentoReceitaFederal();
		docReceita = docReceita.toString().replaceAll("[^\\d]", "");
		cliente.setDocumentoReceitaFederal(docReceita);
		return cliente;
	}

	public String limpaCaracteresEspeciaisString(String string) {

		string = string.toString().replaceAll("[^\\d]", "");

		return string;
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter clienteFilter) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);

		if (StringUtils.isNotBlank(clienteFilter.getNome())) {
			criteria.add(Restrictions.ilike("nome", clienteFilter.getNome(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(clienteFilter.getDocReceitaFederal())) {
			criteria.add(Restrictions.eq("documentoReceitaFederal", clienteFilter.getDocReceitaFederal()));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

}
