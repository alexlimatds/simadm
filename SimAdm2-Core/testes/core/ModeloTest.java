package core;

import core.algoritmo.RungeKutta4;
import junit.framework.TestCase;

public class ModeloTest extends TestCase {
	
	/**
	 * Testa o método de execução de simulação verificando os valores 
	 * calculados para os fluxos e os estoques. O modelo de exemplo foi 
	 * retirado do apêndice A do livro Business Dynamics de J. Sterman.
	 * O algoritmo de integração utilizado é o Método de Euler
	 * 
	 * @throws Exception
	 */
	public void testSimule() throws Exception {
		Modelo model = new Modelo(0.25, 0, 1);
		Estoque e1 = new Estoque("e1", 1000000, model);
		VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", false, model);
		VariavelAuxiliar al = new VariavelAuxiliar("al", "80", false, model);
		Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
		f1.adicionarInfluencia(e1);
		f1.adicionarInfluencia(fbr);
		Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
		f2.adicionarInfluencia(e1);
		f2.adicionarInfluencia(al);
		
		//t = 0
		model.simular();
		//t = 0.25
		model.simular();
		//t = 0.5
		model.simular();
		//t = 0.75
		model.simular();
		//t = 0.1
		model.simular();
		
		ValoresEsperados esperados = new ValoresEsperados(5);
		esperados.addComponente(e1, 1000000.0, 0);
		esperados.addComponente(e1, 1006875.0, 1);
		esperados.addComponente(e1, 1013797.27, 2);
		esperados.addComponente(e1, 1020767.12, 3);
		esperados.addComponente(e1, 1027784.9, 4);
		
		esperados.addComponente(f1, 10000.0, 0);
		esperados.addComponente(f1, 10068.75, 1);
		esperados.addComponente(f1, 10137.97, 2);
		esperados.addComponente(f1, 10207.67, 3);
		esperados.addComponente(f1, 10277.84, 4);
		
		esperados.addComponente(f2, 3125.0, 0);
		esperados.addComponente(f2, 3146.48, 1);
		esperados.addComponente(f2, 3168.11, 2);
		esperados.addComponente(f2, 3189.89, 3);
		esperados.addComponente(f2, 3211.82, 4);
		
		double delta = 0.009;
		esperados.testarResultados(delta);
		System.out.println("Population model (Euler) mean error: " + esperados.getErroMedio());
		
		/*assertEquals(1000000, e1.getHistorico().getValor(0), delta);
		assertEquals(10000, f1.getHistorico().getValor(0), delta);
		assertEquals(3125, f2.getHistorico().getValor(0), delta);
		
		assertEquals(1006875, e1.getHistorico().getValor(1), delta);
		assertEquals(10068.75, f1.getHistorico().getValor(1), delta);
		assertEquals(3146.48, f2.getHistorico().getValor(1), delta);
		
		assertEquals(1013797.27, e1.getHistorico().getValor(2), delta);
		assertEquals(10137.97, f1.getHistorico().getValor(2), delta);
		assertEquals(3168.11, f2.getHistorico().getValor(2), delta);
		
		assertEquals(1020767.12, e1.getHistorico().getValor(3), delta);
		assertEquals(10207.67, f1.getHistorico().getValor(3), delta);
		assertEquals(3189.89, f2.getHistorico().getValor(3), delta);
		
		assertEquals(1027784.9, e1.getHistorico().getValor(4), delta);
		assertEquals(10277.84, f1.getHistorico().getValor(4), delta);
		assertEquals(3211.82, f2.getHistorico().getValor(4), delta);*/
	}
	
	/**
	 * Testa o método de execução de simulação verificando os valores 
	 * calculados para os fluxos e os estoques. O modelo de exemplo foi 
	 * retirado do apêndice A do livro Business Dynamics de J. Sterman.
	 * O algoritmo de integração utilizado é o Runge-Kutta de quarta ordem.
	 * 
	 * @throws Exception
	 */
	public void testSimuleRungeKutta4() throws Exception {
		Modelo model = new Modelo(0.25, 0, 1);
		model.setAlgoritmoDeIntegracao(new RungeKutta4(model));
		Estoque e1 = new Estoque("e1", 1000000, model);
		VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", false, model);
		VariavelAuxiliar al = new VariavelAuxiliar("al", "80", false, model);
		Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
		f1.adicionarInfluencia(e1);
		f1.adicionarInfluencia(fbr);
		Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
		f2.adicionarInfluencia(e1);
		f2.adicionarInfluencia(al);
		
		//t = 0
		model.simular();
		//t = 0.25
		model.simular();
		//t = 0.5
		model.simular();
		//t = 0.75
		model.simular();
		//t = 0.1
		model.simular();
		
		ValoresEsperados esperados = new ValoresEsperados(5);
		esperados.addComponente(e1, 1000000.0, 0);
		esperados.addComponente(e1, 1006898.687, 1);
		esperados.addComponente(e1, 1013844.966, 2);
		esperados.addComponente(e1, 1020839.165, 3);
		esperados.addComponente(e1, 1027881.615, 4);
		
		esperados.addComponente(f1, 10034.453, 0);
		esperados.addComponente(f1, 10103.678, 1);
		esperados.addComponente(f1, 10173.380, 2);
		esperados.addComponente(f1, 10243.563, 3);
		esperados.addComponente(f1, 10314.230, 4);
		
		esperados.addComponente(f2, 3135.766, 0);
		esperados.addComponente(f2, 3157.399, 1);
		esperados.addComponente(f2, 3179.181, 2);
		esperados.addComponente(f2, 3201.113, 3);
		esperados.addComponente(f2, 3223.197, 4);
		
		double delta = 0.001;
		esperados.testarResultados(delta);
		System.out.println("Population model (Runge-Kutta4) mean error: " + esperados.getErroMedio());
		
		/*assertEquals(1000000, e1.getHistorico().getValor(0), delta);
		assertEquals(10034.453, f1.getHistorico().getValor(0), delta);
		assertEquals(3135.766, f2.getHistorico().getValor(0), delta);
		
		assertEquals(1006898.687, e1.getHistorico().getValor(1), delta);
		assertEquals(10103.678, f1.getHistorico().getValor(1), delta);
		assertEquals(3157.399, f2.getHistorico().getValor(1), delta);
		
		assertEquals(1013844.966, e1.getHistorico().getValor(2), delta);
		assertEquals(10173.380, f1.getHistorico().getValor(2), delta);
		assertEquals(3179.181, f2.getHistorico().getValor(2), delta);
		
		assertEquals(1020839.165, e1.getHistorico().getValor(3), delta);
		assertEquals(10243.563, f1.getHistorico().getValor(3), delta);
		assertEquals(3201.113, f2.getHistorico().getValor(3), delta);
		
		assertEquals(1027881.615, e1.getHistorico().getValor(4), delta);
		assertEquals(10314.230, f1.getHistorico().getValor(4), delta);
		assertEquals(3223.197, f2.getHistorico().getValor(4), delta);*/
	}
	
	public void testAdicionarComponente() throws InterpretadorException{
		Modelo modelo = new Modelo(1, 1, 10);
		Estoque e = null;
		try{
			e = new Estoque("comp1", 100.0, modelo);
		}catch(Exception ex){
			fail("Gerou exceção indevida: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		//adiciona componente com mesmo nome -> deve gerar execeção
		try{
			new Fluxo("comp1", "30.0", e, null, false, modelo);
			fail("Deveria ter gerado exceção");
		}catch(NomeDuplicadoException ex){}
		
	}
}
