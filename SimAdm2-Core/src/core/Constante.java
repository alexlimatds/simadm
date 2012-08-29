package core;

/**
 * Representa uma variável auxiliar cujo valor não pode ser alterado ao longo da simulação.
 * 
 * @author Alexandre
 *
 */
public class Constante extends ComponenteDeModelo{
	
	private double valor;
	
	public Constante(String nome, double valor, Modelo modelo) throws NomeDuplicadoException {
		super(nome, modelo);
		this.valor = valor;
		for(int i = 0; i < modelo.getQtdCiclos(); i++){
			getHistorico().setValor(i, valor);
		}
	}
	
	@Override
	public double getValorAtual() {
		return valor;
	}
	
}
