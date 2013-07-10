package org.simadm2.mb;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;


import org.apache.catalina.ha.backend.CollectedInfo;
import org.simadm2.model.ModeloRepositorio;
import org.simadm2.model.Turma;
import org.simadm2.model.TurmaRepositorio;


import util.Relatorio;
import core.ComponenteDeModelo;
import core.Estoque;
import core.Fluxo;
import core.HistoricoComponente;
import core.InterpretadorException;
import core.Modelo;
import core.NomeDuplicadoException;
import core.VariavelAuxiliar;
import core.algoritmo.RungeKutta4;

@ManagedBean
@SessionScoped
public class ModeloSelecionadoBean  implements Serializable{
	private Modelo model;
	private NumberFormat numberFmt;
	private double[] valores;
	private String varfbr;
	private String varAl;
	private int cont = 2;
	private List<VariavelAuxiliar> decisoes = new ArrayList<VariavelAuxiliar>();
	private ModeloRepositorio repositorioModelo = new ModeloRepositorio();
	private List<Modelo> modelos = new ArrayList<Modelo>();
	private String codAcesso;
	private Turma turm;
	private String cod;
	private String erro;
	
	@PostConstruct
	public void init() {
		try{
			numberFmt = DecimalFormat.getInstance();
			numberFmt.setMaximumFractionDigits(4);
			erro = "";
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public List<Modelo> getModelos() {
		return modelos;
	}

	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}

	public Modelo getModel() {
		return model;
	}
	
	public void setModel(Modelo modelo){
		this.model = modelo;
		decisoes.clear();
		for (ComponenteDeModelo comp : model.getComponentes().values()) {
			if (comp instanceof VariavelAuxiliar) {
				VariavelAuxiliar v = (VariavelAuxiliar) comp;
				if (v.isAlteravel() && v.getNome() != "tempo") {
					decisoes.add(v);
				}
			}
		}
	}
	
	public void reiniciarModelo(){
		model = repositorioModelo.getModeloExemplo();
		modelos.add(model);
	}
	
	 public List<VariavelAuxiliar> getComponentes(){
		 /*ArrayList<ComponenteDeModelo> lista = new ArrayList<ComponenteDeModelo>();
		 for(ComponenteDeModelo comp : model.getComponentes().values()){
			 if(comp instanceof VariavelAuxiliar){
				 VariavelAuxiliar v = (VariavelAuxiliar) comp;
				 if(v.isAlteravel() && v.getNome() != "tempo"){
					 lista.add(comp);
				 }
			 }
			 
		 }
		 return lista;*/
		 return decisoes;
	 }
	public void simular() throws InterpretadorException, NomeDuplicadoException{
		/*Estoque e1 = new Estoque("e1", 1000000, model);
		VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", true, model);
		VariavelAuxiliar al = new VariavelAuxiliar("al", "80", true, model);
		Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
		f1.adicionarInfluencia(e1);
		f1.adicionarInfluencia(fbr);
		Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
		f2.adicionarInfluencia(e1);
		f2.adicionarInfluencia(al);
		*/
		
		model.simular();
		System.out.println("---------------");
		//Relatorio report = new Relatorio(model);
		//printReport();
	}
	public void simular2() throws InterpretadorException{
		if(cont <= 10){
			model.simular();
		}
		cont +=1;
		printReport();
	}
	
	
	public String getPag(){
		if(codAcesso.equals(cod)){
			cod = "";
			erro = "";
		return "ExemploModelo.xhtml";
		}
		else{
			cod = "";
			erro = "ERRO: A senha está incorreta";
			return "Turmas.xhtml";
		}

	}
	
	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}

	public List<HistoricoComponente> getHistoricos(){
		List<HistoricoComponente> lista = new ArrayList<HistoricoComponente>();
		for(ComponenteDeModelo comp : model.getComponentes().values()){
			lista.add(comp.getHistorico());
		}
		
		return lista;
	}
	
	/**
	 * Retorna os cabe�alhos das colunas de hist�rico dos ciclos.
	 * @return
	 */
	public List<String> getColunas(){
		ArrayList<String> colunas = new ArrayList<String>();
		for(int i = 0; i < model.getQtdCiclos(); i++){
			colunas.add(String.valueOf(i + 1));
		}
		
		return colunas;
	}
	
	public double[] getValor(){
		ComponenteDeModelo comp = null;
		double[] valores = comp.getHistorico().getValores();
		
		return valores;
	}
	
	

	
	//=============================================================================================
	

	/*public String getVarfbr() {
		return varfbr;
	}

	public void setVarfbr(String varfbr) {
		this.varfbr = varfbr;
	}

	public String getVarAl() {
		return varAl;
	}

	public void setVarAl(String varAl) {
		this.varAl = varAl;
	}*/




	private int maiorNome = 10;
	private int tamanhoNumero = 15;
	private StringBuilder builder = new StringBuilder();
	
	
	
	public StringBuilder printReport(){
		Iterator it = model.getComponentes().values().iterator();
		while(it.hasNext()){
			ComponenteDeModelo comp = (ComponenteDeModelo)it.next();
			if(comp.getNome().length() > maiorNome){
				maiorNome = comp.getNome().length();
			}
		}
		//primeira linha do relat�rio 
		//StringBuilder builder = new StringBuilder();
		builder.append(formate("componente") + "|");
		for(int i = 0; i < model.getQtdCiclos(); i++){
			builder.append(formate2("ciclo " + i) + "|");
		}
		builder.append("\r\n");
		
		//linha separadora
		int index = 0, last = 0;
		while(last != -1){
			last = builder.toString().indexOf('|', index);
			for(int i = index; i < last; i++){
				builder.append('-');
			}
			if(last != -1){
				builder.append('+');
			}
			index = last + 1;
		}
		builder.append("\r\n");
		
		//linhas restantes
		it = model.getComponentes().values().iterator();
		while(it.hasNext()){
			ComponenteDeModelo comp = (ComponenteDeModelo)it.next();
			builder.append(formate(comp.getNome()) + "|");
			valores = comp.getHistorico().getValores();
			for(int i = 0; i < valores.length; i++){
				builder.append( formate2(numberFmt.format(valores[i])) + "|" );
			}
			builder.append("\r\n");
		}
		System.out.print(builder);
		return builder;
	}
	
	public double[] getValores() {
		return valores;
	}

	public void setValores(double[] valores) {
		this.valores = valores;
	}

	private String formate(String word){
		int i = maiorNome - word.length();
		StringBuilder  b = new StringBuilder(word);
		for(int j = 1; j <= i; j++){
			b.append(" ");
		}
		return b.toString();
	}
	
	private String formate2(String word){
		int i = tamanhoNumero - word.length();
		StringBuilder  b = new StringBuilder(word);
		for(int j = 1; j <= i; j++){
			b.append(" ");
		}
		return b.toString();
	}

	public String getCodAcesso() {
		return codAcesso;
	}

	public void setCodAcesso(String codAcesso) {
		this.codAcesso = codAcesso;
	}

	public Turma getTurm() {
		return turm;
	}

	public void setTurm(Turma turm) {
		this.turm = turm;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
	
	
}
