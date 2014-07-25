package org.simadm2.model;

import core.Modelo;

public class ModeloWeb {

	private Modelo modelo;
	private boolean disponivelAsTurmas;
	private boolean disponivelAOutrosProfessores;
	private String resumo;
	private String contexto;
	
	

	public ModeloWeb(Modelo modelo, boolean disponivelAsTurmas,
			boolean disponivelAOutrosProfessores, String resumo, String contexto) {
		super();
		this.modelo = modelo;
		this.disponivelAsTurmas = disponivelAsTurmas;
		this.disponivelAOutrosProfessores = disponivelAOutrosProfessores;
		this.resumo = resumo;
		this.contexto = contexto;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
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

	public void setDisponivelAOutrosProfessores(
			boolean disponivelAOutrosProfessores) {
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

}
