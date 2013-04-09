package org.simadm2.model;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import core.InterpretadorException;
import core.Modelo;
import core.ModeloException;
import core.NomeDuplicadoException;

@ManagedBean(name="turma")
@SessionScoped
public class TurmaRepositorio {
	
	private List<Modelo> modelos = new ArrayList<Modelo>();
	private String descricao;
	private String curso;
	private String codigoAcesso;
	
	
	private List<Turma> turmas = new ArrayList<Turma>();

	
	/*
	public TurmaRepositorio() {
		ModeloRepositorio modelo = new ModeloRepositorio();
		modelos.add(modelo.getModeloExemplo());
		Turma turma = new Turma(modelos, "turma1", "TADS", "123");
		Turma turma2 = new Turma(modelos, "turma2", "REDES", "123");
		turmas.add(turma);
		turmas.add(turma2);	
	}*/
	
	public void addTurma(Turma t){
		turmas.add(t);
	}
	
	public List<Modelo> getModelos() {
		return modelos;
	}



	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
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



	public String getCodigoAcesso() {
		return codigoAcesso;
	}



	public void setCodigoAcesso(String codigoAcesso) {
		this.codigoAcesso = codigoAcesso;
	}



	



	public List<Turma> getTurmas() {
		return turmas;
	}



	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}



	

}
