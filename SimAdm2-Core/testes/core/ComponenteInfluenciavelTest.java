package core;

import junit.framework.TestCase;

/**
 * 
 * @author Alexandre
 */
public class ComponenteInfluenciavelTest extends TestCase{
	
	public void testAdicionarInfluencia() throws ModeloException, NomeDuplicadoException, InterpretadorException{
		Modelo modelo = new Modelo(1, 1, 10);
		ComponenteInfluenciavel var1 = new VariavelAuxiliar("var_1", "0", true, modelo);
		modelo.adicionarComponente(var1);
		ComponenteInfluenciavel var2 = new VariavelAuxiliar("var_2", "2", false, modelo);
		modelo.adicionarComponente(var2);
		
		var2.adicionarInfluencia(var1); //não deve gerar exceção
		
		try{
			var1.adicionarInfluencia(var2);
			fail("Deveria ter gerado Exceção");
		}
		catch(IllegalArgumentException ex){}
	}
}
