package core.function;

import junit.framework.TestCase;

import org.nfunk.jep.JEP;

public class MinTest extends TestCase{
	
	public void testMin(){
		JEP parser = new JEP(); // Create a new parser
		String expr = "min(3, 5)";
		double value;
		
		parser.addStandardFunctions();
		parser.addStandardConstants();
		parser.addFunction("min", new Min()); // Add the custom function
		
		parser.parseExpression(expr); // Parse the expression
		if (parser.hasError()) {
			System.out.println("Error while parsing");
			System.out.println(parser.getErrorInfo());
			fail();
		}
		
		value = parser.getValue(); // Get the value
		if (parser.hasError()) {
			System.out.println("Error during evaluation");
			System.out.println(parser.getErrorInfo());
			fail();
		}
		
		assertEquals(3.0, value);
		
	}
}
