package core.algoritmo;

import core.InterpretadorException;
import core.Modelo;
import core.NomeDuplicadoException;
import core.VariavelAuxiliar;
import junit.framework.TestCase;

public class AlgoritmoTest extends TestCase{
	
	//verifica se é gerada exceção o caso de uma expressão inválida
	public void testCalcularVariavel1() throws NomeDuplicadoException, InterpretadorException{
		Modelo modelo = new Modelo(1, 1, 4);
		new VariavelAuxiliar("var", "{{", false, modelo);
		try{
			modelo.simular();
			fail("Deveria ter gerado exceção");
		}
		catch(RuntimeException ex){
			if(ex.getCause().getClass() != InterpretadorException.class){
				fail("Não gerou erro de interpretação de expressão.");
			}
		}
	}
}
