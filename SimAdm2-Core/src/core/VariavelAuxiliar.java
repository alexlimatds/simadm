package core;


public class VariavelAuxiliar extends ComponenteInfluenciavel {
	
	public VariavelAuxiliar(String nome, String expressao, 
			boolean alteravel, Modelo modelo) throws NomeDuplicadoException, 
			InterpretadorException{
		super(nome, expressao, alteravel, null, modelo);
	}

}
