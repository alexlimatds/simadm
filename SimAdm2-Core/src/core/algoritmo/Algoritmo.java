package core.algoritmo;

import org.nfunk.jep.JEP;

import core.InterpretadorException;
import core.Modelo;

/**
 * Interface do core.algoritmo que realiza o cálculo 
 * do valor de um fluxo. 
 * 
 * @author Alexandre
 *
 */
public abstract class Algoritmo{
	
	protected Modelo modelo;
	protected JEP parser;
	
	public Algoritmo(Modelo modelo){
		this.modelo = modelo;
		parser = new JEP();
		parser.addStandardConstants();
		parser.addStandardFunctions();
	}
	
	public abstract void calcularVariaveisEFluxos();
	
	public abstract void calcularEstoques();
	
	public void validarExpressao(String expr) throws 
	InterpretadorException{
		parser.parseExpression(expr);
		if( parser.hasError() ){
			throw new InterpretadorException("Erro ao validar a equacao <"+ 
					expr +">: " + parser.getErrorInfo());
		}
	}
}
