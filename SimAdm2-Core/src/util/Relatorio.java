/*
 * Created on 16/03/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;

import core.ComponenteDeModelo;
import core.Modelo;

/**
 * Classe que tem como objetivo gerar um relatório com histórico dos valores 
 * de todos os componentes de um modelo.
 * 
 * @author Alexandre Gomes de Lima
 */
public class Relatorio {
	
	private Modelo model;
	private NumberFormat numberFmt;
	private int maiorNome = 10;
	private int tamanhoNumero = 15;
	
	/**
	 * Cria uma instância dessa classe.
	 * @param model	Modelo que se deseja obter o relatório.
	 */
	public Relatorio(Modelo model){
		this.model = model;
		numberFmt = DecimalFormat.getInstance();
		numberFmt.setMaximumFractionDigits(4);
	}
	
	/**
	 * Imprime o relatório na saída padrão.
	 *
	 */
	public void printReport(){
		Iterator it = model.getComponentes().values().iterator();
		while(it.hasNext()){
			ComponenteDeModelo comp = (ComponenteDeModelo)it.next();
			if(comp.getNome().length() > maiorNome){
				maiorNome = comp.getNome().length();
			}
		}
		//primeira linha do relatório 
		StringBuilder builder = new StringBuilder();
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
			double[] valores = comp.getHistorico().getValores();
			for(int i = 0; i < valores.length; i++){
				builder.append( formate2(numberFmt.format(valores[i])) + "|" );
			}
			builder.append("\r\n");
		}
		System.out.print(builder);
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
}
