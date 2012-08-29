package core;

import junit.framework.TestCase;

/**
 * Caso de teste baseado no modelo descrito no capítulo 4 do texto Introdução à 
 * Dinâmica de Sistemas de autoria de Paulo R. C. Villela.
 *  
 * @author Alexandre
 */
public class ModeloPoupancaTest extends TestCase {
	
	/**
	 * Monta um modelo de poupança e verifica os resultados.
	 * 
	 */
	public void testSimulacao() throws Exception {
		Modelo model = new Modelo(1, 0, 10);
		Estoque poupanca = new Estoque("poupanca", 100, model);
		VariavelAuxiliar taxaRendimento = new VariavelAuxiliar("taxa_rendimento", "0.01", false, model);
		Fluxo rendimentos = new Fluxo("rendimentos", "taxa_rendimento * poupanca", null, poupanca, false, model);
		rendimentos.adicionarInfluencia(poupanca);
		rendimentos.adicionarInfluencia(taxaRendimento);
		
		//t = 0
		model.simular();
		//assertEquals(100.0, poupanca.getValorAtual(), 0.01);
		//t = 1
		model.simular();
		//assertEquals(101.0, poupanca.getValorAtual(), 0.01);
		//t = 2
		model.simular();
		//assertEquals(102.01, poupanca.getValorAtual(), 0.01);
		//t = 3
		model.simular();
		//assertEquals(103.03, poupanca.getValorAtual(), 0.01);
		//t = 4
		model.simular();
		//assertEquals(104.06, poupanca.getValorAtual(), 0.01);
		//t = 5
		model.simular();
		//assertEquals(105.1, poupanca.getValorAtual(), 0.01);
		//t = 6
		model.simular();
		//assertEquals(106.15, poupanca.getValorAtual(), 0.01);
		//t = 7
		model.simular();
		//assertEquals(107.21, poupanca.getValorAtual(), 0.01);
		//t = 8
		model.simular();
		//assertEquals(108.29, poupanca.getValorAtual(), 0.01);
		//t = 9
		model.simular();
		//assertEquals(109.37, poupanca.getValorAtual(), 0.01);
		//t = 10
		model.simular();
		//assertEquals(110.46, poupanca.getValorAtual(), 0.01);
		
		ValoresEsperados esperados = new ValoresEsperados(11);
		esperados.addComponente(poupanca, 100.0, 0);
		esperados.addComponente(poupanca, 101.0, 1);
		esperados.addComponente(poupanca, 102.01, 2);
		esperados.addComponente(poupanca, 103.03, 3);
		esperados.addComponente(poupanca, 104.06, 4);
		esperados.addComponente(poupanca, 105.1, 5);
		esperados.addComponente(poupanca, 106.15, 6);
		esperados.addComponente(poupanca, 107.21, 7);
		esperados.addComponente(poupanca, 108.29, 8);
		esperados.addComponente(poupanca, 109.37, 9);
		esperados.addComponente(poupanca, 110.46, 10);
		
		double delta = 0.01;
		esperados.testarResultados(delta);
		System.out.println("Poupança mean error: " + esperados.getErroMedio());
	}
	
}
