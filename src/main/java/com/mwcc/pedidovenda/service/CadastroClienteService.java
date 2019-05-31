package com.mwcc.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.repository.ClienteRepository;
import com.mwcc.pedidovenda.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository clienteRepository;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = new Cliente();

		if (StringUtils.isNotBlank(cliente.getDocumentoReceitaFederal())) {
			cliente = clienteRepository.limpaCaracteresEspeciais(cliente);
		}

		if (cliente != null) {
			clienteExistente = clienteRepository.porDocReceita(cliente.getDocumentoReceitaFederal());
		}

		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente com o CPF/CNPJ informado.");
		} else {
			clienteExistente = new Cliente();
		}
		return clienteRepository.guardar(cliente);
	}
}
