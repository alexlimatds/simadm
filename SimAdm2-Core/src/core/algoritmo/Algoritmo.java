package core.algoritmo;

import java.util.Iterator;

import org.nfunk.jep.JEP;

import core.Estoque;
import core.Fluxo;
import core.InterpretadorException;
import core.Modelo;
import core.function.Min;

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
		parser.addFunction("min", new Min());
	}
	
	public abstract void calcularVariaveisEFluxos();
	
	/**
	 * Calcula o valor dos estoques no tempo atual de simulação. Note que 
	 * os valores dos estoques são atualizados nos próprios objetos.
	 */
	public void calcularEstoques(){
		for(Iterator it = modelo.getEstoques().iterator(); it.hasNext();){
			Estoque est = (Estoque)it.next();
			double inflows = 0, outflows = 0;
			for(Iterator fluxos = est.getFluxosDeEntrada().iterator(); fluxos.hasNext();){
				inflows = ((Fluxo)fluxos.next()).getValorAtual();
			}
			for(Iterator fluxos = est.getFluxosDeSaida().iterator(); fluxos.hasNext();){
				outflows = ((Fluxo)fluxos.next()).getValorAtual();
			}
			est.setValorAtual(est.getValorAtual() + inflows - outflows);
		}
	}
	
	public void validarExpressao(String expr) throws 
	InterpretadorException{
		parser.parseExpression(expr);
		if( parser.hasError() ){
			throw new InterpretadorException("Erro ao validar a equacao <"+ 
					expr +">: " + parser.getErrorInfo());
		}
	}
}
