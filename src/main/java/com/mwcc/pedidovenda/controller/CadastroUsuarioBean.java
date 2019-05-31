package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import com.mwcc.pedidovenda.model.Grupo;
import com.mwcc.pedidovenda.model.Permissao;
import com.mwcc.pedidovenda.model.Usuario;
import com.mwcc.pedidovenda.repository.GrupoRepository;
import com.mwcc.pedidovenda.service.CadastroUsuarioService;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private List<Grupo> gruposSelecionados;
	private DualListModel<Grupo> grupos;
	private List<Grupo> lista;
	private List<Permissao> permissoes;
	List<Grupo> gruposAdd;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	@Inject
	private GrupoRepository grupoRepository;

	public void inicializar() {
		/*if (FacesUtil.isNotPostback()) {
			System.out.println("Entrei no inicializar");
			
		}*/
		
		if (usuario == null) {
			limpar();
		} else {
			lista = grupoRepository.buscarTodos();
			lista.removeAll(usuario.getGrupos());

			grupos = new DualListModel<>(lista, new ArrayList<>(usuario.getGrupos()));
		}

	}

	public void limpar() {
		usuario = new Usuario();

		List<Grupo> vazia = new ArrayList<>();
		grupos = new DualListModel<>(grupoRepository.buscarTodos(), vazia);
	}

	public void salvar() {

		usuario.setGrupos(grupos.getTarget());
		cadastroUsuarioService.salvar(usuario);
		limpar();

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso."));
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!",
				"Excluído com sucesso!");

		context.addMessage("msgInfo", msg);
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getGruposSelecionados() {
		return gruposSelecionados;
	}

	public void setGruposSelecionados(List<Grupo> gruposSelecionados) {
		this.gruposSelecionados = gruposSelecionados;
	}

	public DualListModel<Grupo> getGrupos() {
		if (grupos == null) {
			grupos = new DualListModel<>();
		}
		return grupos;
	}

	public void setGrupos(DualListModel<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Grupo> getLista() {
		return lista;
	}

	public void setLista(List<Grupo> lista) {
		this.lista = lista;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Grupo> getGruposAdd() {
		return gruposAdd;
	}

	public void setGruposAdd(List<Grupo> gruposAdd) {
		this.gruposAdd = gruposAdd;
	}

	public boolean isEditando() {
		return usuario.getId() != null;
	}

	public boolean isNaoEditando() {
		if (isEditando()) {
			return false;
		} else {
			return true;
		}
	}

}
