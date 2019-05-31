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

import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.repository.ClienteRepository;
import com.mwcc.pedidovenda.repository.filter.ClienteFilter;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClienteFilter clienteFilter;
	private Cliente clienteSelecionado;

	private List<Cliente> clientes;

	@Inject
	private ClienteRepository clienteRepository;

	@PostConstruct
	public void init() {
		limpar();
	}

	public void limpar() {
		clienteFilter = new ClienteFilter();
		clientes = new ArrayList<>();
	}

	public void pesquisar() {
		if (StringUtils.isNotBlank(clienteFilter.getDocReceitaFederal())) {
			String str = clienteRepository.limpaCaracteresEspeciaisString(clienteFilter.getDocReceitaFederal());

			clienteFilter.setDocReceitaFederal(str);
		}

		clientes = clienteRepository.filtrados(clienteFilter);
	}

	public void excluir() {
		
		clienteRepository.remover(clienteSelecionado);
		clientes.remove(clienteSelecionado);
		
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ecluído com sucesso!",
				"Excluído com sucesso!");

		context.addMessage("msgInfo", msg);
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ClienteFilter getClienteFilter() {
		return clienteFilter;
	}

	public void setClienteFilter(ClienteFilter clienteFilter) {
		this.clienteFilter = clienteFilter;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}
