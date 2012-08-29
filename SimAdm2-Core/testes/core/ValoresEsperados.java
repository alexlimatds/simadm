package core;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

/**
 * Classe para auxiliar o cálculo do erro médio.
 * 
 * @author Alexandre
 */
public class ValoresEsperados{
	
	private int turnos;
	private Map<ComponenteDeModelo, Double[]> mapComponenteValorEsperado = new HashMap<ComponenteDeModelo, Double[]>();
	private boolean executouTeste = false;
	private double erroMedio;
	
	public ValoresEsperados(int turnos) {
		this.turnos = turnos;
	}
	
	public void addComponente(ComponenteDeModelo componente, Double valorEsperado, int turno){
		if(componente == null || valorEsperado == null)
			throw new IllegalArgumentException("Parâmetros nulos");
		
		Double[] valores = mapComponenteValorEsperado.get(componente);
		if(valores == null){
			valores = new Double[turnos];
			mapComponenteValorEsperado.put(componente, valores);
		}
		valores[turno] = valorEsperado;
	}
	
	public Double getValorEsperado(ComponenteDeModelo c, int turno){
		return mapComponenteValorEsperado.get(c)[turno];
	}
	
	public void testarResultados(double delta){
		int cont = 0;
		double erro = 0;
		for(ComponenteDeModelo componente : mapComponenteValorEsperado.keySet()){
			Double[] array = mapComponenteValorEsperado.get(componente);
			for(int i = 0; i < array.length; i++){
				cont++;
				double esperado = array[i];
				double obtido = componente.getHistorico().getValor(i);
				erro += Math.abs(esperado - obtido) / esperado;
				Assert.assertEquals(esperado, obtido, delta);
			}
		}
		erroMedio = erro / cont * 100.0;
		executouTeste = true;
	}
	
	/**
	 * Retorna o erro médio em percentual.
	 * 
	 * @return
	 */
	public double getErroMedio(){
		if(!executouTeste)
			throw new IllegalStateException("Execute o método testarResultados(double) primeiro");
		
		return erroMedio;
	}
}
