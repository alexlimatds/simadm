package core;

public class InterpretadorException extends Exception {
	public InterpretadorException(String str){
		super(str);
	}
	
	public InterpretadorException(Throwable thr){
		super(thr);
	}
	
	public InterpretadorException(String str, Throwable thr){
		super(str, thr);
	}
}
