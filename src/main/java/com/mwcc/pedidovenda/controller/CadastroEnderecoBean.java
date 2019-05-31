package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.mwcc.pedidovenda.model.Endereco;

@ManagedBean
@ViewScoped
public class CadastroEnderecoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Endereco> enderecos;
	
	private Endereco e;
	private Endereco e2;

	public CadastroEnderecoBean() {
		
	}
	
	@PostConstruct
	public void init() {
		enderecos = new ArrayList<>();
		
		e = new Endereco();
		e2 = new Endereco();
	}
	
	public List<Endereco> getEnderecosAdicionados(){
		
		e.setLogradouro("Rua das Pedras Grandes Azuis");
		e.setNumero("1234");
		e.setComplemento("ap 1022");
		e.setCep("38499-533");
		e.setCidade("Uberlândia");
		e.setUf("MG");
		
		e2.setLogradouro("Av. Romdon Pacheco");
		e2.setNumero("455");
		e2.setComplemento("sala 923");
		e2.setCep("38408-011");
		e2.setCidade("Uberlândia");
		e2.setUf("MG");
		
		enderecos.add(e);
		enderecos.add(e2);
		
		return enderecos;
	}
	

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco getE() {
		return e;
	}

	public void setE(Endereco e) {
		this.e = e;
	}

	public Endereco getE2() {
		return e2;
	}

	public void setE2(Endereco e2) {
		this.e2 = e2;
	}
	
	
}
