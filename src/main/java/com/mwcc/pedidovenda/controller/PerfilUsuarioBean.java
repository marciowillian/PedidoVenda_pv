package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.mwcc.pedidovenda.model.Perfil;
import com.mwcc.pedidovenda.repository.GrupoRepository;

@ManagedBean
@ViewScoped
public class PerfilUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<Perfil> perfis;
	
	@Inject
	private GrupoRepository grupoRepository;
	

	public PerfilUsuarioBean() {
		super();
	}

	@PostConstruct
	public void init() {
		perfis = new ArrayList<>();

	}

	public List<Perfil> perfisAdicionados() {
		
		
		return perfis;
	}


	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

}
