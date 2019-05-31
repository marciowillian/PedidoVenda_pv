package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mwcc.pedidovenda.model.Usuario;
import com.mwcc.pedidovenda.repository.UsuarioReporitory;
import com.mwcc.pedidovenda.repository.filter.UsuarioFilter;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> usuarios;
	private Usuario usuario;
	private UsuarioFilter usuarioFilter;
	private Usuario usuarioSelecionado;

	@Inject
	private UsuarioReporitory usuarioReporitory;

	@PostConstruct
	public void inicializar() {
		if (usuario == null) {
			limpar();
		}
	}

	public void pesquisar() {
		usuario.setNome(usuarioFilter.getNome());
		usuarios = usuarioReporitory.pesquisa(usuario);

	}

	public void excluir() {
		usuarioReporitory.remover(usuarioSelecionado);
		usuarios.remove(usuarioSelecionado);

		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				usuarioSelecionado.getId() + " excluído com sucesso!",
				usuarioSelecionado.getId() + " excluído com sucesso!");

		context.addMessage("msgInfo", msg);
	}

	public void limpar() {
		usuario = new Usuario();
		usuarios = new ArrayList<>();
		usuarioFilter = new UsuarioFilter();
		usuarioSelecionado = new Usuario();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public UsuarioFilter getUsuarioFilter() {
		return usuarioFilter;
	}

	public void setUsuarioFilter(UsuarioFilter usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	
}
