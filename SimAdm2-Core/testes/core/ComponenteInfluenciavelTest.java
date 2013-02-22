package core;

import core.algoritmo.MetodoDeEuler;
import util.Relatorio;
import junit.framework.TestCase;

/**
 * 
 * @author Alexandre
 */
public class ComponenteInfluenciavelTest extends TestCase{
	
	//verifica se é gerada exceção ao se tentar alterar a expressão de um componente
	//não alterável
	public void testSetExpressao1() throws NomeDuplicadoException, InterpretadorException{
		Modelo modelo = new Modelo(1, 1, 10);
		ComponenteInfluenciavel c = new VariavelAuxiliar("c", "100.0", false, modelo);
		try{
			c.setExpressao("200.0");
			fail("Deveria ter gerado exceção.");
		}
		catch(UnsupportedOperationException e){}
	}
	
	//verifica se o usuário consegue alterar a expressão do componente durante a simulação
	public void testSetExpressao2() throws NomeDuplicadoException, InterpretadorException{
		Modelo modelo = new Modelo(1, 1, 10);
		Estoque e = new Estoque("e", 1000, modelo);
		VariavelAuxiliar v = new VariavelAuxiliar("v", "1.0", false, modelo);
		modelo.adicionarComponente(v);
		//lembre que Fluxo é ComponenteInfluenciavel
		Fluxo f = new Fluxo("f", "1.0", e, null, true, modelo);
		f.adicionarInfluencia(v);
		modelo.adicionarComponente(f);
		
		//t=0
		modelo.simular();
		//t=1
		f.setExpressao("100.0");
		modelo.simular();
		assertEquals(100.0, f.getValorAtual(), 0.001);
		//t=2
		f.setExpressao("235.4 + v");
		modelo.simular();
		assertEquals(235.4 + v.getValorAtual(), f.getValorAtual(), 0.001);
		//t=3
		f.setExpressao("sqrt(400)");
		modelo.simular();
		assertEquals(20, f.getValorAtual(), 0.001);
		
		Relatorio report = new Relatorio(modelo);
		report.printReport();
	}
	
	//verifica se é gerada uma exceção quando um objeto indevido chama o método setValorAtual
	public void testSetValorAtual() throws NomeDuplicadoException, InterpretadorException{
		Modelo modelo = new Modelo(1, 1, 10);
		ComponenteInfluenciavel v = new VariavelAuxiliar("v", "100.0", true, modelo);
		modelo.adicionarComponente(v);
		
		//Realiza um ciclo de simulação para que o método setValorAtual seja chamado pelo 
		//objeto de algoritmo de integração. Esta chamada não deve gerar execeção.
		modelo.simular();
		
		//Chama o método setValorAtual para verificar se a execeção é gerada. Note que somente o 
		//objeto algoritmo de simulação do modelo está autorizado a realizar a chamada.
		try{
			v.setValorAtual(100.0, null);
			fail("Deveria ter gerado exceção");
		}catch(IllegalArgumentException ex){}
		
		//Testa com uma instância de algoritmo que não é a associada ao modelo.
		try{
			MetodoDeEuler euler = new MetodoDeEuler(null);
			v.setValorAtual(100.0, euler);
			fail("Deveria ter gerado exceção");
		}catch(IllegalArgumentException ex){}
	}
}
