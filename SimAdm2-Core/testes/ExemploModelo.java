import util.Relatorio;
import core.Estoque;
import core.Fluxo;
import core.Modelo;
import core.VariavelAuxiliar;
import core.algoritmo.RungeKutta4;

/*
 * Created on 21/03/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Servidor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExemploModelo {

	public static void main(String[] args) {
		try{
			Modelo model = new Modelo(0.25, 0, 1);
			model.setAlgoritmoDeIntegracao(new RungeKutta4(model));
			Estoque e1 = new Estoque("e1", 1000000, model);
			VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", false, model);
			VariavelAuxiliar al = new VariavelAuxiliar("al", "80", false, model);
			Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
			f1.adicionarInfluencia(e1);
			f1.adicionarInfluencia(fbr);
			Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
			f2.adicionarInfluencia(e1);
			f2.adicionarInfluencia(al);
			
			//t = 0
			model.simular();
			//t = 0.25
			model.simular();
			//t = 0.5
			model.simular();
			//t = 0.75
			model.simular();
			//t = 0.1
			model.simular();
			
			Relatorio report = new Relatorio(model);
			report.printReport();
		}
		catch(Exception ex){
			System.out.println("\nOcorreu um erro: ");
			ex.printStackTrace();
		}
	}
}
