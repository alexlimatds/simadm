package org.simadm2.model;

import java.util.ArrayList;
import java.util.List;

import core.Modelo;

public class Turma {

	//private Modelo modelo;
	private String descricao;
	private String curso;
	private String codigoAcesso;
	private List<Modelo> modelos = new ArrayList<Modelo>();;
	public Turma() {
	}
	
	public Turma(String descricao, String curso,
			String codigoAcesso) {
		//super();
		this.descricao = descricao;
		this.curso = curso;
		this.codigoAcesso = codigoAcesso;
	}
	
	//private List<Modelo> modelos = new ArrayList<Modelo>();
	

	
	
	// Gets Sets ==================================================
	
	/*public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
	public String toString(){
		return modelo + " -  " +descricao + "  -  "+ curso + " -  " + codigoAcesso + "\n";
	}*/
	
	

	public List<Modelo> getModelos() {
		return modelos;
	}

	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}

	public String getCodigoAcesso() {
		return codigoAcesso;
	}
	public void setCodigoAcesso(String codigoAcesso) {
		this.codigoAcesso = codigoAcesso;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	
	
	
}
