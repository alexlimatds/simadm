package core;

public class ModeloException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ModeloException(String str){
		super(str);
	}
	
	public ModeloException(Throwable th) {
		super(th);
	}
}
