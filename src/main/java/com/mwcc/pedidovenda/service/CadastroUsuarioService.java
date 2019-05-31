package com.mwcc.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.mwcc.pedidovenda.model.Usuario;
import com.mwcc.pedidovenda.repository.UsuarioReporitory;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioReporitory usuariosRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuariosRepository.guardar(usuario);
	}

}
