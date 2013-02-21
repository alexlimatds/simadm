package core;


public class Fluxo extends ComponenteInfluenciavel{
	private Estoque estoqueDeOrigem;
	private Estoque estoqueDeDestino;
	
	/**
	 * Cria uma instância desta classe.
	 * @param nome	Identificador do fluxo.
	 * @param equacao	Equação que determina o valor do fluxo.
	 * @param estoqueDeOrigem	Estoque de origem do fluxo.
	 * @param estoqueDeDestino	Estoque de destino do fluxo.
	 * @param alteravel	Indica se o valor do fluxo pode ser defindo interativamente  
	 * 					pelo usuário da simulação.
	 * @param modelo	Modelo ao qual este fluxo pertence
	 * @throws NomeDuplicadoException	Caso já exista um componente com o nome passado 
	 * 									como parâmetro.
	 * @throws InterpretadorException	Caso haja algum erro na equação do fluxo.
	 */
	public Fluxo(String nome, String equacao, Estoque estoqueDeOrigem, 
			Estoque estoqueDeDestino, boolean alteravel, Modelo modelo) throws 
			NomeDuplicadoException, InterpretadorException{
		super(nome, equacao, alteravel, modelo);
		if( estoqueDeOrigem != null ){
			this.estoqueDeOrigem = estoqueDeOrigem;
			estoqueDeOrigem.adicionarFluxoDeSaida(this);
		}
		if( estoqueDeDestino != null){
			this.estoqueDeDestino = estoqueDeDestino;
			estoqueDeDestino.adicionarFluxoDeEntrada(this);
		}
	}
	
	/**
	 * Retorna o estoque de destino do fluxo.
	 * @return	Uma instância de <code>Estoque</code>. Caso o fluxo não possua 
	 * 			estoque de destino, retorna <code>null</code>.
	 */
	public Estoque getEstoqueDeDestino() {
		return estoqueDeDestino;
	}
	
	/**
	 * Retorna o estoque de origem do fluxo.
	 * @return	Uma instância de <code>Estoque</code>. Caso o fluxo não possua 
	 * 			estoque de origem, retorna <code>null</code>.
	 */
	public Estoque getEstoqueDeOrigem() {
		return estoqueDeOrigem;
	}
}
