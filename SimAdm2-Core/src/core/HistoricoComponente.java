package core;

import java.io.Serializable;

/**
 * Uma instância desta classe armazena o histórico, ciclo a ciclo, dos valores  
 * assumidos por um componente de modelo. Conseqüentemente, um objeto desta 
 * classe estará associado a um componente de modelo.
 * 
 * @author Alexandre Gomes de Lima
 */
public class HistoricoComponente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//componente de modelo ao qual a instância estará associada
	private ComponenteDeModelo componente;
	//armazena os valores assumidos pelo componente de modelo
	private double[] valores;
	
	/**
	 * Cria uma instância desta classe.
	 * @param comp	O componente de modelo ao qual esta classe estará associada.
	 */
	public HistoricoComponente(ComponenteDeModelo comp){
		componente = comp;
		valores = new double[comp.getModelo().getQtdCiclos()];
	}
	
	public double[] getValores(){
		return valores;
	}
	
	public String getNomeComponente(){
		return componente.getNome();
	}
	
	public void setValor(int ciclo, double valor){
		valores[ciclo] = valor;
	}
	
	public double getValor(int interacao){
		return valores[interacao];
	}
	/**
	 * @return Returns the componente.
	 */
	public ComponenteDeModelo getComponente() {
		return componente;
	}
}
