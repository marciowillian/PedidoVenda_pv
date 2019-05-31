package com.mwcc.pedidovenda.repository.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String docReceitaFederal;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocReceitaFederal() {
		return docReceitaFederal;
	}

	public void setDocReceitaFederal(String docReceitaFederal) {
		this.docReceitaFederal = docReceitaFederal;
	}

}
