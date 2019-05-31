package com.mwcc.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.model.Endereco;
import com.mwcc.pedidovenda.model.TipoPessoa;
import com.mwcc.pedidovenda.repository.ClienteRepository;
import com.mwcc.pedidovenda.service.CadastroClienteService;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Endereco endereco;
	private String mascara;
	
	List<Endereco> enderecos;

	// private List<Endereco> enderecos;

	@Inject
	private CadastroClienteService cadastroClienteService;

	@Inject
	private ClienteRepository clienteRepository;

	public void inicializar() {
		if (this.cliente == null) {
			limpar();
		} 
	}

	public void salvar() {
		
		cliente = cadastroClienteService.salvar(cliente);
		limpar();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso."));

	}
	public void incluirEndereco() {
		enderecos = new ArrayList<>();
		enderecos.add(endereco);
		cliente.setEnderecos(enderecos);
	}

	public void excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluído com sucesso!",
				"Excluído com sucesso!");

		context.addMessage("msgInfo", msg);
	}

	public boolean isFisica() {

		TipoPessoa tipoPessoa = TipoPessoa.FISICA;

		if (cliente.getTipo() != null && cliente.getTipo() != tipoPessoa) {
			return false;
		} else {
			return true;
		}

	}

	public boolean isJuridica() {

		TipoPessoa tipoPessoa = TipoPessoa.JURIDICA;

		if (cliente.getTipo() != null && cliente.getTipo() == tipoPessoa) {
			return true;
		} else {
			return false;
		}

	}

	public void mudarMascara() {
		TipoPessoa tipoPessoa = TipoPessoa.FISICA;

		if (cliente == null) {
			limpar();
		} else {
			inicializar();
			if (cliente.getTipo() != null && cliente.getTipo().equals(tipoPessoa)) {
				mascara = "999.999.999-99";
			} else {
				if (cliente.getTipo() != null && !cliente.getTipo().equals(tipoPessoa)) {
					mascara = "99.999.999/9999-99";
				}
			}
		}

	}

	public void limpar() {
		cliente = new Cliente();
		endereco = new Endereco();
		mascara = "999.999.999-99";
		isFisica();
		enderecos = new ArrayList<>();
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		if(this.cliente != null) {
			mudarMascara();
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public boolean isEditando() {
		return cliente.getId() != null;
	}
}
