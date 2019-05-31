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

import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.repository.filter.PedidoFilter;
import com.mwcc.pedidovenda.service.NegocioException;
import com.mwcc.pedidovenda.util.jsf.FacesUtil;

public class PedidoRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Pedido guardar(Pedido pedido) {
		
		try {
			this.manager.merge(pedido);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Não foi possivel salvar o pedido.");
		}
		
		return pedido;
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filter) {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Pedido.class)
				// Fazemos uma associação com (join) com cliente e nomeamos com "c"
				.createAlias("cliente", "c")
				// Fazemos uma associação com (join) com vendedor e nomeamos com "v"
				.createAlias("vendedor", "v");
		if (filter.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a filter.numeroDe
			criteria.add(Restrictions.ge("id", filter.getNumeroDe()));
		}

		if (filter.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equals) a filter.numeroDe
			criteria.add(Restrictions.le("id", filter.getNumeroAte()));
		}

		if (filter.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filter.getDataCriacaoDe()));
		}

		if (filter.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filter.getDataCriacaoAte()));
		}

		if (StringUtils.isNotBlank(filter.getNomeCliente())) {
			criteria.add(Restrictions.ilike("c.nome", filter.getNomeCliente(), MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filter.getNomeVendedor())) {
			criteria.add(Restrictions.ilike("v.nome", filter.getNomeVendedor(), MatchMode.ANYWHERE));
		}

		if (filter.getStatuses() != null && filter.getStatuses().length > 0) {
			// Adicionamos uma restrição "in", passando um array de constantes da enum
			// StatusPedido
			criteria.add(Restrictions.in("statusPedido", filter.getStatuses()));
		}
		return criteria.addOrder(Order.asc("id")).list();
	}
}
