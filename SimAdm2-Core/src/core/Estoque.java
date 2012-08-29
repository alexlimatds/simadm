package core;

import java.util.ArrayList;
import java.util.List;

public class Estoque extends ComponenteDeModelo{
	private double valorInicial;
	private double valorAtual;
	private List<Fluxo> fluxosDeEntrada;
	private List<Fluxo> fluxosDeSaida;
	
	/**
	 * Cria uma instância desta classe.
	 * 
	 * @param nome	Nome do estoque. Lembrando que o nome dever ser único 
	 * 				entre todos os componentes do modelo.
	 * @param valorInicial	Valor inicial do estoque.
	 * @param modelo	Modelo ao qual este estoque pertence.
	 * @throws NomeDuplicadoException	Caso o modelo já possua um componente 
	 * 									com o nome fornecido.
	 */
	public Estoque(String nome, double valorInicial, Modelo modelo) 
	throws NomeDuplicadoException{
		super(nome, modelo);
		setValorInical(valorInicial);
		this.valorAtual = valorInicial;
		fluxosDeEntrada = new ArrayList<Fluxo>();
		fluxosDeSaida = new ArrayList<Fluxo>();
		modelo.adicionarComponente(this);
	}
	
	/**
	 * Cria uma instância desta classe.
	 * 
	 * @param nome Nome do estoque. Lembrando que o nome dever ser único 
	 * 			   entre todos os componentes do modelo.
	 * @param valorInicial Constante com o valor inicial do componente. Note que 
	 * 					   o valor da variável terá influência no estoque apenas na inicialização 
	 * 					   do mesmo.
	 * @param modelo Modelo ao qual este estoque pertence.
	 * @throws NomeDuplicadoException	Caso o modelo já possua um componente 
	 * 									com o nome fornecido.
	 */
	public Estoque(String nome, Constante valorInicial, Modelo modelo) 
	throws NomeDuplicadoException{
		this(nome, valorInicial.getValorAtual(), modelo);
	}
	
	private void setValorInical(double value){
		valorInicial = value;
		getHistorico().setValor(0, valorInicial);
	}

	public double getValorInicial() {
		return valorInicial;
	}

	public double getValorAtual(){
		return valorAtual;
	}

	public List<Fluxo> getFluxosDeEntrada(){
		return fluxosDeEntrada;
	}

	public void adicionarFluxoDeEntrada(Fluxo f) {
		fluxosDeEntrada.add(f);
	}

	public List<Fluxo> getFluxosDeSaida() {
		return fluxosDeSaida;
	}

	public void adicionarFluxoDeSaida(Fluxo f) {
		fluxosDeSaida.add(f);
	}
	
	public void setValorAtual(double valor){
		valorAtual = valor;
		getHistorico().setValor(getModelo().getCicloAtual(), valorAtual);
	}
}
