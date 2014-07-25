package org.simadm2.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.simadm2.model.ModeloRepositorio;
import org.simadm2.model.Turma;
import org.simadm2.model.TurmaRepositorio;

import core.Modelo;

@ManagedBean
@SessionScoped
public class TurmasMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Turma> turmas = new ArrayList<Turma>();
	private String nomeModelo;
	private Modelo modeloSelecionado;
	private Map<String, Modelo> mapaModelos = new HashMap<String, Modelo>();
	private TurmaRepositorio repositorioTurma = new TurmaRepositorio();
	
	@PostConstruct
	public void init() {
		turmas.addAll(repositorioTurma.getTurmas());
		ModeloRepositorio repositorioModelo = new ModeloRepositorio();
		for (Modelo m : repositorioModelo.getTodos()) {
			mapaModelos.put(m.getDescricao(), m);
		}
	}
	
	public List<Turma> getTurmas() {
		return repositorioTurma.getTurmas();
	}

	public String getNomeModelo() {
		return nomeModelo;
	}
	public void setNomeModelo(String nomeModelo) {
		this.nomeModelo = nomeModelo;
		setM();
	}

	public void setM() {
		modeloSelecionado = mapaModelos.get(nomeModelo);
		System.out.println("SELECIONADO: " + modeloSelecionado);
	}
	
	public Modelo getModeloSelecionado() {
		return modeloSelecionado;
	}
	public void setModeloSelecionado(Modelo modeloSelecionado) {
		this.modeloSelecionado = modeloSelecionado;
	}

}
