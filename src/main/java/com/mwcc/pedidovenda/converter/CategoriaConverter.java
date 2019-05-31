package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mwcc.pedidovenda.model.Categoria;
import com.mwcc.pedidovenda.repository.CategoriaRepository;
import com.mwcc.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	// @Inject
	private CategoriaRepository categorias;

	public CategoriaConverter() {
		categorias = CDIServiceLocator.getBean(CategoriaRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;

		if (value != null) {
			Long id = new Long(value);

			retorno = categorias.porId(id);

		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			//return ((Categoria) value).getId().toString();
			Categoria categoria = (Categoria) value;
			return categoria.getId() == null ? "" : categoria.getId().toString();
		}
		return "";
	}

}
