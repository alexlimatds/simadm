package core.algoritmo;

import java.util.Iterator;

import org.nfunk.jep.JEP;

import core.ComponenteDeModelo;
import core.ComponenteInfluenciavel;
import core.Estoque;
import core.Fluxo;
import core.InterpretadorException;
import core.Modelo;
import core.VariavelAuxiliar;
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
	
	/**
	 * Calcula o valor de todas as veriáveis auxiliares e fluxos do modelo.
	 */
	public void calcularVariaveisEFluxos(){
		for(Iterator it = modelo.getListaDeAvaliacao().iterator(); it.hasNext();){
			ComponenteInfluenciavel comp = (ComponenteInfluenciavel)it.next();
			if( comp instanceof VariavelAuxiliar ){
				calcularVariavel((VariavelAuxiliar)comp);
			}
			else{
				calcularFluxo((Fluxo)comp);
			}
		}
	}
	
	/**
	 * Calcula o valor de um fluxo no tempo atual da simulação.
	 * 
	 * @param f	Fluxo que se deseja calcular o valor.
	 */
	protected abstract void calcularFluxo(Fluxo f);
	
	/**
	 * Calcula o valor de uma variável auxiliar no instante atual da simulação. 
	 * Note que o valor da variável é atualizado no objeto recebido 
	 * como parâmetro.
	 * @param var	A variável que se deseja calcular o valor.
	 */
	private void calcularVariavel(VariavelAuxiliar var){
		ComponenteDeModelo c;
		for(Iterator comps = var.getInfluencias().values().iterator(); comps.hasNext();){
			c = (ComponenteDeModelo)comps.next();
			parser.addVariable(c.getNome(), c.getValorAtual());
		}
		//TODO acrescentar validação da expressão
		parser.parseExpression(var.getExpressao());
		var.setValorAtual( parser.getValue(), this );
	}
	
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
