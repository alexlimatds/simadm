package core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CoreTests extends TestCase{
	
	public CoreTests(){
		super("CoreTests");
	}
	
	public static Test suite(){
		TestSuite suite = new TestSuite();
		suite.addTestSuite(ModeloPoupancaTest.class);
		suite.addTestSuite(ModeloTest.class);
		suite.addTestSuite(ModeloFinanceiroTest.class);
		suite.addTestSuite(ComponenteInfluenciavelTest.class);
		
		return suite;
	}
}
