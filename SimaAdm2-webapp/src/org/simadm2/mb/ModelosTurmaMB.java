package org.simadm2.mb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.simadm2.model.ModeloRepositorio;
import org.simadm2.model.Turma;

import core.Modelo;


@ManagedBean
@SessionScoped
public class ModelosTurmaMB {

	private List<Modelo> modelos = new ArrayList<Modelo>();
	private List<Turma> turma = new ArrayList<Turma>();
	private ModeloRepositorio repositorioModelo = new ModeloRepositorio();
	private Modelo model;
	private Modelo modelo;


	
	
	@PostConstruct
	public void init()  {
			model = repositorioModelo.getModeloExemplo();
			modelo = repositorioModelo.getModeloFinanceiro(); 
			modelos.add(model);	
			modelos.add(modelo);
	}
	



	public List<Turma> getTurma() {
		return turma;
	}




	public void setTurma(List<Turma> turma) {
		this.turma = turma;
	}




	public List<Modelo> getModelos() {
		return modelos;
	}



	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}
		
}
