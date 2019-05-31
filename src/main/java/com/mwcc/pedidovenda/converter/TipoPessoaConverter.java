package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mwcc.pedidovenda.model.TipoPessoa;

@FacesConverter(forClass = TipoPessoa.class)
public class TipoPessoaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value != null) {
			return TipoPessoa.valueOf(value);
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if(value != null && value instanceof TipoPessoa) {
			return ((TipoPessoa)value).name();
		}
		
		return null;
	}

}
