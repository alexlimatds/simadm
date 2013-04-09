package org.simadm2.model;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import core.Modelo;
@FacesConverter(value="ModeloConverter", forClass=Modelo.class)
public class ModeloConverter implements Converter {
	  ArrayList<Modelo> todos = new ArrayList<Modelo>();

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		ModeloRepositorio modeloRepositorio = new ModeloRepositorio();
		todos = (ArrayList<Modelo>) modeloRepositorio.getTodos(); 
		return todos.contains(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		 ModeloRepositorio modelorep = (ModeloRepositorio) arg2;
		return String.valueOf(modelorep.getTodos());
	}

}
