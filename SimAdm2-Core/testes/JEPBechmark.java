import org.nfunk.jep.JEP;

public class JEPBechmark {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int lacos = 10000;
		long inicio, fim;
		JEP parser = new JEP();
		parser.addStandardFunctions();
		//avaliação para a expressão x + y
		System.out.println("Avaliacao da expressao x + y");
		parser.addVariable("x", 5);
		parser.addVariable("y", 3);
		parser.parseExpression("x + y");
		inicio = System.currentTimeMillis();
		for(int i = 0; i < lacos; i++){
			parser.addVariable("x", 5);
			parser.addVariable("y", 3);
			parser.getValue();
		}
		fim = System.currentTimeMillis();
		System.out.println("Varios parsers: " + (fim - inicio) +" ms");
		inicio = System.currentTimeMillis();
		for(int i = 0; i < lacos; i++){
			parser.addVariable("x", 5);
			parser.addVariable("y", 3);
			parser.parseExpression("x + y");
			parser.getValue();
		}
		fim = System.currentTimeMillis();
		System.out.println("Unico parser: " + (fim - inicio) +" ms");
		
//		avaliação para a expressão x * y
		System.out.println("\nAvaliacao da expressao x * y");
		parser.addVariable("x", 5);
		parser.addVariable("y", 3);
		parser.parseExpression("x * y");
		inicio = System.currentTimeMillis();
		for(int i = 0; i < lacos; i++){
			parser.addVariable("x", 5);
			parser.addVariable("y", 3);
			parser.getValue();
		}
		fim = System.currentTimeMillis();
		System.out.println("Varios parsers: " + (fim - inicio) +" ms");
		inicio = System.currentTimeMillis();
		for(int i = 0; i < lacos; i++){
			parser.addVariable("x", 5);
			parser.addVariable("y", 3);
			parser.parseExpression("x * y");
			parser.getValue();
		}
		fim = System.currentTimeMillis();
		System.out.println("Unico parser: " + (fim - inicio) +" ms");
		
//		avaliação para a expressão x ^ y
		System.out.println("\nAvaliacao da expressao x ^ y");
		parser.addVariable("x", 5);
		parser.addVariable("y", 3);
		parser.parseExpression("x ^ y");
		inicio = System.currentTimeMillis();
		for(int i = 0; i < lacos; i++){
			parser.addVariable("x", 5);
			parser.addVariable("y", 3);
			parser.getValue();
		}
		fim = System.currentTimeMillis();
		System.out.println("Varios parsers: " + (fim - inicio) +" ms");
		inicio = System.currentTimeMillis();
		for(int i = 0; i < lacos; i++){
			parser.addVariable("x", 5);
			parser.addVariable("y", 3);
			parser.parseExpression("x ^ y");
			parser.getValue();
		}
		fim = System.currentTimeMillis();
		System.out.println("Unico parser: " + (fim - inicio) +" ms");
	}

}
