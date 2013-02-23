package core.algoritmo;

import java.util.Iterator;

import core.ComponenteDeModelo;
import core.ComponenteInfluenciavel;
import core.Fluxo;
import core.Modelo;

/**
 * Classe que calcula o valor de um fluxo com base no 
 * algoritmo de Euler.
 * 
 * @author Alexandre
 */
public class MetodoDeEuler extends Algoritmo {
	
	/**
	 * Cria uma inst�ncia desta classe.
	 * 
	 * @param modelo
	 */
	public MetodoDeEuler(Modelo modelo){
		super(modelo);
	}
	
	/**
	 * Calcula o valor dos fluxos e das vari�veis auxiliares no tempo atual da simula��o.
	 */
	public void calcularVariaveisEFluxos() {
		for(Iterator it = modelo.getListaDeAvaliacao().iterator(); it.hasNext();){
			ComponenteInfluenciavel comp = (ComponenteInfluenciavel)it.next();
			//adicionando as vari�veis no parser
			Iterator vars = comp.getInfluencias().values().iterator();
			ComponenteDeModelo c;
			while(vars.hasNext()){
				c = (ComponenteDeModelo)vars.next();
				parser.addVariable(c.getNome(), c.getValorAtual());
			}
			//avaliando a express�o
			//TODO capturar e lan�ar erro de avalia��o da express�o
			parser.parseExpression(comp.getExpressao());
			//obtendo o resultado e atualizando o valor do componente
			if( comp instanceof Fluxo ){
				comp.setValorAtual( parser.getValue() * modelo.getDt(), this );
			}
			else{
				comp.setValorAtual( parser.getValue(), this );
			}
		}
	}
}
