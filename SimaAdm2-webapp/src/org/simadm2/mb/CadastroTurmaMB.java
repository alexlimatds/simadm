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
public class CadastroTurmaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Modelo> modelos = new ArrayList<Modelo>();
	private Turma turma = new Turma();
	private TurmaRepositorio repositorioTurma = new TurmaRepositorio();
	private List<Turma> turmas = new ArrayList<Turma>();
	private List<String> modelosSelecionados = new ArrayList<String>();
	private Map<String, Modelo> mapaModelos = new HashMap<String, Modelo>();
	private Modelo modeloSelecionado;
	

	public Modelo getModeloSelecionado() {
		return modeloSelecionado;
	}
	public void setModeloSelecionado(Modelo modeloSelecionado) {
		this.modeloSelecionado = modeloSelecionado;
	}
	@PostConstruct
	public void init()  {
		ModeloRepositorio repositorioModelo = new ModeloRepositorio();
		//modelos.addAll(repositorioModelo.getTodos());
		modelos.add(repositorioModelo.getModeloExemplo());
		modelos.add(repositorioModelo.getModeloFinanceiro());
		turmas.addAll(repositorioTurma.getTurmas());
		Modelo m = repositorioModelo.getModeloFinanceiro();
		mapaModelos.put(m.getDescricao(), m);
		m = repositorioModelo.getModeloExemplo();
		mapaModelos.put(m.getDescricao(), m);
	}
	
	private String nomeModelo;
	public String getNomeModelo() {
		return nomeModelo;
	}
	public void setNomeModelo(String nomeModelo) {
		System.out.println("SET NOME MODELO: " + nomeModelo);
		this.nomeModelo = nomeModelo;
		setM();
	}

	public List<Turma> getTurmas() {
		return turmas;
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setM(){
		modeloSelecionado = mapaModelos.get(nomeModelo);
		System.out.println("SELECIONADO: " + modeloSelecionado);
	}
	public void novaTurma(){
		turma = new Turma();
	}
	
	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	
	public List<String> getModelosSelecionados() {
		return modelosSelecionados;
	}

	public void setModelosSelecionados(List<String> modelosSelecionados) {
		this.modelosSelecionados = modelosSelecionados;
	}
	
	public List<Modelo> getModelos() {
		return modelos;
	}
	
	public void selecionarModel(Modelo model){
		System.out.println("###############"+model.getDescricao());
		//turma.setModelo(model);	
	}
	
	public void addTurma(){
		for(String nome: modelosSelecionados){
			turma.getModelos().add(mapaModelos.get(nome));
			
		}
		repositorioTurma.addTurma(turma);
		turmas.clear();
		turmas.addAll(repositorioTurma.getTurmas());
		//reseta campos
		//turma = new Turma();
		//return  "cadastrarTurmas.xhtml";
	}
}
