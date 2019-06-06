package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Pedido;
import com.mwcc.pedidovenda.repository.PedidoRepository;
import com.mwcc.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter{
	
	private PedidoRepository pedidos;
	
	public PedidoConverter() {
		pedidos = CDIServiceLocator.getBean(PedidoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pedido retorno = null;
		
		if(StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			
			retorno = pedidos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Pedido pedido = (Pedido)value;
			return pedido.getId() == null ? null :  pedido.getId().toString();
		}
		return "";
	}

}
