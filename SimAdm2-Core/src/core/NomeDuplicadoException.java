package core;

public class NomeDuplicadoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NomeDuplicadoException(){
		super("Já existe um componente com o nome fornecido.");
	}
}
