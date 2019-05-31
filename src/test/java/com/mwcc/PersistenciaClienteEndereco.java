package com.mwcc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.model.Endereco;
import com.mwcc.pedidovenda.model.TipoPessoa;

public class PersistenciaClienteEndereco {
	
	public static void main(String[]args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		
		Cliente c = new Cliente();
		c.setNome("Marcia Cristina Chaves Cardoso");
		c.setEmail("marcia_cristina@hotmail.comn");
		c.setDocumentoReceitaFederal("555.444.555.62");
		c.setTipo(TipoPessoa.FISICA);
		
		Endereco e = new Endereco();
		e.setLogradouro("Rua santo Adalberto, Residencial Granada");
		e.setNumero("25");
		e.setComplemento("Ao lado da AABEM");
		e.setCidade("São Luis");
		e.setUf("MA");
		e.setCep("65054115");
		e.setCliente(c);
		
		c.getEnderecos().add(e);
		
		em.merge(c);
		
		em.getTransaction().commit();
		em.close();
	}

}
