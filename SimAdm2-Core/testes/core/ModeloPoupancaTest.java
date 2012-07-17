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
		assertEquals(100.0, poupanca.getValorAtual(), 0.01);
		//t = 1
		model.simular();
		assertEquals(101.0, poupanca.getValorAtual(), 0.01);
		//t = 2
		model.simular();
		assertEquals(102.01, poupanca.getValorAtual(), 0.01);
		//t = 3
		model.simular();
		assertEquals(103.03, poupanca.getValorAtual(), 0.01);
		//t = 4
		model.simular();
		assertEquals(104.06, poupanca.getValorAtual(), 0.01);
		//t = 5
		model.simular();
		assertEquals(105.1, poupanca.getValorAtual(), 0.01);
		//t = 6
		model.simular();
		assertEquals(106.15, poupanca.getValorAtual(), 0.01);
		//t = 7
		model.simular();
		assertEquals(107.21, poupanca.getValorAtual(), 0.01);
		//t = 8
		model.simular();
		assertEquals(108.29, poupanca.getValorAtual(), 0.01);
		//t = 9
		model.simular();
		assertEquals(109.37, poupanca.getValorAtual(), 0.01);
		//t = 10
		model.simular();
		assertEquals(110.46, poupanca.getValorAtual(), 0.01);
	}
	
}
