package core.algoritmo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import core.ComponenteDeModelo;
import core.Estoque;
import core.Fluxo;
import core.InterpretadorException;
import core.Modelo;

/**
 * Classe que calcula o valor dos fluxos e 
 * estoques com base no método de Runge-Kutta 
 * de quarta ordem.
 * 
 * @author Alexandre
 */
public class RungeKutta4 extends Algoritmo {
	
	private Map mapaFatores;
	
	/**
	 * Cria uma instância desta classe.
	 * 
	 * @param modelo
	 */
	public RungeKutta4(Modelo modelo){
		super(modelo);
		//o mapa abaixo armazena os fatores K1, K2, K3 e K4 de cada estoque
		mapaFatores = new HashMap();
	}
	
	/**
	 * Calcula os fatores (K1, K2, K3 e K4) de todos os estoques que 
	 * possuem influência sobre determinado fluxo.
	 * @param f	O fluxo que se deseja calcular os fatores.
	 */
	private void calcularFatores(Fluxo f){
		Estoque stk;
		Object obj;
		for(Iterator it = f.getInfluencias().values().iterator(); it.hasNext();){
			obj = it.next();
			if( obj instanceof Estoque ){
				stk = (Estoque)obj;
				//calcula K1 e guarda valor no mapa
				double[] fatores = (double[])mapaFatores.get(stk.getNome());
				fatores[0] = calcularK1(stk);
			}
		}
		for(Iterator it = f.getInfluencias().values().iterator(); it.hasNext();){
			obj = it.next();
			if( obj instanceof Estoque ){
				stk = (Estoque)obj;
				//calcula K2 e guarda valor no mapa
				double[] fatores = (double[])mapaFatores.get(stk.getNome());
				fatores[1] = calcularK2(stk);
			}
		}
		for(Iterator it = f.getInfluencias().values().iterator(); it.hasNext();){
			obj = it.next();
			if( obj instanceof Estoque ){
				stk = (Estoque)obj;
				//calcula K3 e guarda valor no mapa
				double[] fatores = (double[])mapaFatores.get(stk.getNome());
				fatores[2] = calcularK3(stk);
			}
		}
		for(Iterator it = f.getInfluencias().values().iterator(); it.hasNext();){
			obj = it.next();
			if( obj instanceof Estoque ){
				stk = (Estoque)obj;
				//calcula K4 e guarda valor no mapa
				double[] fatores = (double[])mapaFatores.get(stk.getNome());
				fatores[3] = calcularK4(stk);
			}
		}
	}
	
	/* TODO : os métodos calcularK1, calcularK2, calcularK3 e calcularK4 possuem
	 * código semelhante que poderiam ser reaproveitados em funções.
	 */ 
	
	/*
	 * Calcula o fator K1 de um estoque no tempo de simulação atual
	 */
	private double calcularK1(Estoque e){
		// k1 = (soma inflows - somaoutflows) *dt
		double inflows = 0, outflows = 0;
		ComponenteDeModelo c;
		Fluxo f;
		//inflows
		for(Iterator it = e.getFluxosDeEntrada().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				parser.addVariable(c.getNome(), c.getValorAtual());
			}
			parser.parseExpression(f.getExpressao());
			inflows = inflows + parser.getValue();
		}
		//outflows
		for(Iterator it = e.getFluxosDeSaida().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				parser.addVariable(c.getNome(), c.getValorAtual());
			}
			parser.parseExpression(f.getExpressao());
			outflows = outflows + parser.getValue();
		}
		return (inflows - outflows) * modelo.getDt();
	}
	
	/*
	 * Calcula o fator K2 de um estoque no tempo de simulação atual
	 */
	private double calcularK2(Estoque e){
		// k2 = (somainflows(S+k1/2) - somaoutflows(S+k1/2)) *dt
		double inflows = 0, outflows = 0;
		ComponenteDeModelo c;
		Fluxo f;
		//inflows
		for(Iterator it = e.getFluxosDeEntrada().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				double complemento = 0;
				if( c instanceof Estoque ){
					double[] fatores = (double[])mapaFatores.get(e.getNome());
					complemento = fatores[0] / 2;
				}
				parser.addVariable(c.getNome(), c.getValorAtual() + complemento);
			}
			parser.parseExpression(f.getExpressao());
			inflows = inflows + parser.getValue();
		}
		//outflows
		for(Iterator it = e.getFluxosDeSaida().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				double complemento = 0;
				if( c instanceof Estoque ){
					double[] fatores = (double[])mapaFatores.get(e.getNome());
					complemento = fatores[0] / 2;
				}
				parser.addVariable(c.getNome(), c.getValorAtual() + complemento);
			}
			parser.parseExpression(f.getExpressao());
			outflows = outflows + parser.getValue();
		}
		return (inflows - outflows) * modelo.getDt();
	}
	
	/*
	 * Calcula o fator K3 de um estoque no tempo de simulação atual
	 */
	private double calcularK3(Estoque e){
		// k3 = (somainflows(S+k2/2) - somaoutflows(S+k2/2)) *dt
		double inflows = 0, outflows = 0;
		ComponenteDeModelo c;
		Fluxo f;
		//inflows
		for(Iterator it = e.getFluxosDeEntrada().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				double complemento = 0;
				if( c instanceof Estoque ){
					double[] fatores = (double[])mapaFatores.get(e.getNome());
					complemento = fatores[1] / 2;
				}
				parser.addVariable(c.getNome(), c.getValorAtual() + complemento);
			}
			parser.parseExpression(f.getExpressao());
			inflows = inflows + parser.getValue();
		}
		//outflows
		for(Iterator it = e.getFluxosDeSaida().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				double complemento = 0;
				if( c instanceof Estoque ){
					double[] fatores = (double[])mapaFatores.get(e.getNome());
					complemento = fatores[1] / 2;
				}
				parser.addVariable(c.getNome(), c.getValorAtual() + complemento);
			}
			parser.parseExpression(f.getExpressao());
			outflows = outflows + parser.getValue();
		}
		return (inflows - outflows) * modelo.getDt();
	}
	
	/*
	 * Calcula o fator K4 de um estoque no tempo de simulação atual
	 */
	private double calcularK4(Estoque e){
		// k4 = (somainflows(S+k3) - somaoutflows(S+k3)) *dt
		double inflows = 0, outflows = 0;
		ComponenteDeModelo c;
		Fluxo f;
		//inflows
		for(Iterator it = e.getFluxosDeEntrada().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				double complemento = 0;
				if( c instanceof Estoque ){
					double[] fatores = (double[])mapaFatores.get(e.getNome());
					complemento = fatores[2];
				}
				parser.addVariable(c.getNome(), c.getValorAtual() + complemento);
			}
			parser.parseExpression(f.getExpressao());
			inflows = inflows + parser.getValue();
		}
		//outflows
		for(Iterator it = e.getFluxosDeSaida().iterator(); it.hasNext();){
			f = (Fluxo)it.next();
			for(Iterator comps = f.getInfluencias().values().iterator(); comps.hasNext();){
				c = (ComponenteDeModelo)comps.next();
				double complemento = 0;
				if( c instanceof Estoque ){
					double[] fatores = (double[])mapaFatores.get(e.getNome());
					complemento = fatores[2];
				}
				parser.addVariable(c.getNome(), c.getValorAtual() + complemento);
			}
			parser.parseExpression(f.getExpressao());
			outflows = outflows + parser.getValue();
		}
		return (inflows - outflows) * modelo.getDt();
	}
	
	/**
	 * Calcula o valor de um fluxo no tempo atual da simulação.
	 * 
	 * @param f	Fluxo que se deseja calcular o valor.
	 */
	@Override
	public void calcularFluxo(Fluxo f) {
		if( modelo.getCicloAtual() == 0 ){
			for(Iterator it = modelo.getEstoques().iterator(); it.hasNext();){
				mapaFatores.put(((Estoque)it.next()).getNome(), new double[4]);
			}
		}
		calcularFatores(f);
		double valor;
		valor = (avaliarFluxo(f, 0, 1) + 
				 2 * avaliarFluxo(f, 1, 2) + 
				 2 * avaliarFluxo(f, 2, 2) +
				 avaliarFluxo(f, 3, 1)) * modelo.getDt() / 6;
		f.setValorAtual(valor, this);
	}
	
	/**
	 * Obtém o resultado da expressão de um fluxo. Note que o valor determinado 
	 * não é multiplicado por DT.
	 * @param f        Fluxo que se deseja avaliar.
	 * @param fator    Fator a ser adicionado aos estoques que influenciam o fluxo.
	 * @param divisor  Valor pelo qual o fator será dividido antes de ser somado ao estoque. 
	 * 1 para K1, 2 para K2, 3 para K3, 4 para K4 e 0 (zero) para nenhum.
	 * @return	O resultado da avaliação da expressão.
	 */
	private double avaliarFluxo(Fluxo f, int fator, double divisor){
		ComponenteDeModelo comp;
		double complemento;
		for(Iterator it = f.getInfluencias().values().iterator(); it.hasNext();){
			comp = (ComponenteDeModelo)it.next();
			complemento = 0;
			if(comp instanceof Estoque && fator != 0){
				complemento = getFator((Estoque)comp, fator);
			}
			parser.addVariable(comp.getNome(), comp.getValorAtual() + complemento / divisor);
		}
		parser.parseExpression(f.getExpressao());
		if(parser.hasError()){
			InterpretadorException ex = new InterpretadorException(
					"Erro ao avaliar fluxo " + f.getNome() + ": " + parser.getErrorInfo());
			throw new RuntimeException(ex);
		}
		return parser.getValue();
	}
	
	/**
	 * Retorna um fator (K1, K2, K3, K4) de um estoque.
	 * @param e	Estoque que se deseja obter o fator.
	 * @param fator	O código do fator que se deseja obter. 1 para K1, 
	 * 2 para K2, 3 para K3 e 4 para K4.
	 * @return	O valor da fator desejado.
	 */
	private double getFator(Estoque e, int fator){
		double[] fatores = (double[])mapaFatores.get(e.getNome());
		if(fator == 1){
			return fatores[0];
		}
		if(fator == 2){
			return fatores[1];
		}
		if(fator == 3){
			return fatores[2];
		}
		if(fator == 4){
			return fatores[3];
		}
		return 0;
	}
	
}
