package com.mwcc.pedidovenda.model;

public enum FormaPagamento {
	DINHEIRO("Dinheiro"),
	CARTAO_CREDITO("Cartão de Crédito"),
	CARTAO_DEBITO("Cartão de Débito"),
	CHEQUE("Cheque"),
	BOLETO_BANCARIO("Boleto"),
	DEPOSITO_BANCARIO("Depósito Bancário");

	private String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
