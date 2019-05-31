package com.mwcc.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.mwcc.pedidovenda.model.Produto;
import com.mwcc.pedidovenda.repository.ProdutoRepository;
import com.mwcc.pedidovenda.util.cdi.CDIServiceLocator;


@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	// @Inject
	private ProdutoRepository produtos;

	public ProdutoConverter() {
		produtos = CDIServiceLocator.getBean(ProdutoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto retorno = null;

		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);

			retorno = produtos.porId(id);

		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Produto produto = (Produto) value;
			return produto.getId() == null ? null : produto.getId().toString();
		}
		return "";
	}

}
