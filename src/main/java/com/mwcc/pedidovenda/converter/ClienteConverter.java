package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Cliente;
import com.mwcc.pedidovenda.repository.ClienteRepository;
import com.mwcc.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	@Inject
	private ClienteRepository clientes;

	public ClienteConverter() {
		clientes = CDIServiceLocator.getBean(ClienteRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente retorno = null;
		
		if(StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			retorno = clientes.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Cliente cliente = (Cliente)value;
			
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		return "";
	}

}
