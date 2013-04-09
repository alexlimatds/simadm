package org.simadm2.model;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import core.Modelo;
@FacesConverter(value="ConverterModelo", forClass=Modelo.class)
public class ConverterModelo implements Converter {
		Modelo modelo;
		ModeloRepositorio rep;
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		if(arg2 != null) {
			modelo = null;
			return modelo;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		if(arg2 != null && arg2 instanceof Modelo) {
			return ((Modelo)arg2).getDescricao().toString();
		}
		return null;
	}

}
