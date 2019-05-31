package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Grupo;
import com.mwcc.pedidovenda.repository.GrupoRepository;
import com.mwcc.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(value = "GrupoConverter")
public class GrupoConverter implements Converter {

	// @Inject
	private GrupoRepository grupos;

	public GrupoConverter() {
		grupos = CDIServiceLocator.getBean(GrupoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Grupo retorno = null;

		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);

			retorno = grupos.porId(id);

		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {

			Grupo grupo = (Grupo) value;
			return grupo.getId() == null ? null : grupo.getId().toString();

		}
		return "";
	}

}
