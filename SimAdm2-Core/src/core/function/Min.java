package core.function;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

public class Min extends PostfixMathCommand {
	
	public Min() {
		numberOfParameters = 2;
	}
	
	@Override
	public void run(Stack stack) throws ParseException {
		checkStack(stack);
		//obtém parâmetros
		Object a = stack.pop();
		Object b = stack.pop();
		if(a instanceof Double && b instanceof Double){
			double min = Math.min((Double)a, (Double)b);
			stack.push(min);
		}
		else{
			throw new ParseException("Invalid Parameter Type");
		}
	}
	
}
