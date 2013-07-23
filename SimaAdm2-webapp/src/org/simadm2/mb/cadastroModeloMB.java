package org.simadm2.mb;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.simadm2.model.ModeloRepositorio;
import org.simadm2.model.ModeloWeb;

import core.Modelo;


@ManagedBean
@SessionScoped
public class cadastroModeloMB {

	private double dt = 0.25;
	private int inicio;
	private int fim;
	private String descricao = "";
	private boolean disponivelAsTurmas;
	private boolean disponivelAOutrosProfessores;
	private String resumo = "";
	private String contexto = "";
	


	private Modelo modeloCad = new Modelo(dt, inicio, fim);
	ModeloWeb mw = new ModeloWeb(modeloCad, disponivelAsTurmas,
			 disponivelAOutrosProfessores, resumo, contexto);
	ModeloWeb modeloCopia = new ModeloWeb(modeloCad, disponivelAsTurmas, disponivelAOutrosProfessores, resumo, contexto);
	private List<Modelo> modelos = new ArrayList<Modelo>();
	private List<ModeloWeb> modweb = new ArrayList<ModeloWeb>();
	

	ModeloRepositorio mr = new ModeloRepositorio();
	
	
	public void cadastrarModelo() {
		try {
			modeloCad.setDescricao(descricao);
			mw.setContexto(contexto);
			mw.setDisponivelAOutrosProfessores(disponivelAOutrosProfessores);
			mw.setDisponivelAsTurmas(disponivelAsTurmas);
			mw.setModelo(modeloCad);
			mw.setResumo(resumo);
			// modeloCad = new Modelo(dt, inicio, fim);
			mr.addTodos(modeloCad);
			mr.addTodos2(mw);
			if(disponivelAOutrosProfessores == true){
				mr.addDisponivel(mw);
			}
			novoModelo();
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void novoModelo(){
		modeloCad = new Modelo(dt, fim, fim);
		mw = new ModeloWeb(modeloCad, disponivelAsTurmas, disponivelAOutrosProfessores, resumo, contexto);
	}
	
	public void novoModeloCopia(){
		modeloCad = new Modelo(dt, fim, fim);
		modeloCopia = new ModeloWeb(modeloCad, disponivelAsTurmas, disponivelAOutrosProfessores, resumo, contexto);
	}
	
	 public void copiarModelo(){
		 try {
			mr.addTodos2(modeloCopia);
			novoModeloCopia();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
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
	
	
	public boolean isDisponivelAsTurmas() {
		return disponivelAsTurmas;
	}


	public void setDisponivelAsTurmas(boolean disponivelAsTurmas) {
		this.disponivelAsTurmas = disponivelAsTurmas;
	}


	public boolean isDisponivelAOutrosProfessores() {
		return disponivelAOutrosProfessores;
	}


	public void setDisponivelAOutrosProfessores(boolean disponivelAOutrosProfessores) {
		this.disponivelAOutrosProfessores = disponivelAOutrosProfessores;
	}


	public String getResumo() {
		return resumo;
	}


	public void setResumo(String resumo) {
		this.resumo = resumo;
	}


	public String getContexto() {
		return contexto;
	}


	public void setContexto(String contexto) {
		this.contexto = contexto;
	}


	public List<ModeloWeb> getModweb() {
		return modweb;
	}


	public void setModweb(List<ModeloWeb> modweb) {
		this.modweb = modweb;
	}


	public ModeloRepositorio getMr() {
		return mr;
	}


	public void setMr(ModeloRepositorio mr) {
		this.mr = mr;
	}


	public ModeloWeb getMw() {
		return mw;
	}


	public void setMw(ModeloWeb mw) {
		this.mw = mw;
	}
	
	public ModeloWeb getModeloCopia() {
		return modeloCopia;
	}

	public void setModeloCopia(ModeloWeb modeloCopia) {
		this.modeloCopia = modeloCopia;
	}

	
}
