package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Endereco;
import com.mwcc.pedidovenda.repository.EnderecoRepository;
import com.mwcc.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Endereco.class)
public class EnderecoConverter implements Converter{

	@Inject
	private EnderecoRepository enderecos;
	
	public EnderecoConverter() {
		enderecos = CDIServiceLocator.getBean(EnderecoRepository.class);
	}
	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Endereco retorno = null;
		
		if(StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			
			retorno = enderecos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Endereco endereco = (Endereco)value;
			
			return endereco.getId() == null ? null : endereco.getId().toString();
		}
		return "";
	}

}
