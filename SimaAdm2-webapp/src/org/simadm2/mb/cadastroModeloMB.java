package org.simadm2.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.simadm2.model.ModeloRepositorio;

import core.Modelo;


@ManagedBean
@SessionScoped
public class cadastroModeloMB {

	private double dt = 0.25;
	private int inicio;
	private int fim;
	private String descricao;
	private Modelo modeloCad = new Modelo(dt, inicio, fim);
	private List<Modelo> modelos = new ArrayList<Modelo>();
	
	ModeloRepositorio mr = new ModeloRepositorio();
	
	
	public void cadastrarModelo() {
		try {
			modeloCad.setDescricao(descricao);
			// modeloCad = new Modelo(dt, inicio, fim);
			mr.addTodos(modeloCad);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	
	//====================================

	public double getDt() {
		return dt;
	}


	public void setDt(double dt) {
		this.dt = dt;
	}


	public int getInicio() {
		return inicio;
	}


	public void setInicio(int inicio) {
		this.inicio = inicio;
	}


	public int getFim() {
		return fim;
	}


	public void setFim(int fim) {
		this.fim = fim;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Modelo getModeloCad() {
		return modeloCad;
	}


	public void setModeloCad(Modelo modeloCad) {
		this.modeloCad = modeloCad;
	}


	public List<Modelo> getModelos() {
		return modelos;
	}


	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}
	
	
}
