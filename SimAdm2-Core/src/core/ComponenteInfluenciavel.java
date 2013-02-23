package core;

import java.util.HashMap;
import java.util.Map;

import core.algoritmo.Algoritmo;


/**
 * Representa um componente que pode ter seu valor definido com base nos valores 
 * de outros componentes. Na verdade, este comportamento é típico dos fluxos 
 * e das variáveis auxiliares.
 * 
 * @author Alexandre
 */
public abstract class ComponenteInfluenciavel extends ComponenteDeModelo {
	private Map<String, ComponenteDeModelo> influencias; //chave -> nome do componente
	private String expressao;
	// registra se o valor deste componente já foi calculado em um determinado ciclo
	private boolean[] calculadoEm;
	// indica se o valor do componente pode ser alterado pelo usuário durante a simulação 
	private boolean alteravel;
	protected double valorAtual;
	//indica a ordem de execução do componente
	private int ordem;
	
	/**
	 * Cria uma instância desta classe.
	 * 
	 * @param nome		  Nome identificador do componente.
	 * @param expressao	  Expressão que determina o valor do componente.
	 * @param alteravel	  Indica se a expressão do componente pode ser alterada pelo usuário durante a simulação.
	 * @param modelo	  Modelo ao qual este componente pertence.
	 * @throws NomeDuplicadoException	Caso o modelo já possua um componente com o nome informado.
	 * @throws InterpretadorException	Caso ocorra algum erro na avaliação da expressão.
	 */
	public ComponenteInfluenciavel(String nome, String expressao, 
			boolean alteravel, Modelo modelo) throws NomeDuplicadoException, 
			InterpretadorException{
		
		super(nome, modelo); //adiciona este componente ao modelo
		
		this.influencias = new HashMap<String, ComponenteDeModelo>();
		this.expressao = expressao;
		this.alteravel = alteravel;
		calculadoEm = new boolean[getModelo().getQtdCiclos()];
	}
	
	/**
	 * Retorna um mapa contendo os componente que influenciam o cálculo 
	 * do valor deste componente. 
	 * @return	Um mapa contendo instâncias de <code>ComponenteDeModelo</code>, cujas 
	 * 			chaves são os nomes dos componentes. 
	 */
	public Map<String, ComponenteDeModelo> getInfluencias(){
		return influencias;
	}
	
	/**
	 * Insere um componente disponível para compor a expressão numérica deste componente.
	 * @param componente	O componente que estará disponível na 
	 * 						avaliação do valor da expressão.
	 */
	public void adicionarInfluencia(ComponenteDeModelo componente){
		influencias.put(componente.getNome(), componente);
	}
	
	/**
	 * Remove um componente que está disponível para o cálculo 
	 * deste componente.
	 * @param componente	O componente a ser removido.
	 */
	public void removerInfluencia(ComponenteDeModelo componente){
		influencias.remove(componente);
	}
	
	/**
	 * Retorna a expressão numérica que determina o valor deste componente.
	 * @return	Uma representação textual da expressão numérica
	 */
	public String getExpressao(){
		return expressao;
	}
	
	/**
	 * Altera a expressão numérica deste componente.
	 * @param expressao a nova expressão
	 * @throws InterpretadorException Caso a nova expressão seja sintaticamente inválida. 
	 * @throws UnsupportedOperationException Caso este componente não seja alterável.
	 */
	public void setExpressao(String expressao) throws InterpretadorException{
		if(!alteravel){
			throw new UnsupportedOperationException("Operação permitida apenas para componentes alteráveis.");
		}
		getModelo().validarExpressao(expressao, null);
		
		this.expressao = expressao;
	}
	
	/**
	 * Altera o valor atual do componente. Este método deve ser utilizado apenas pelo algoritmo de 
	 * integração do modelo.
	 * @param valor	    O novo valor do componente.
	 * @param algoritmo A instância de algoritmo de integração utilizada pelo modelo.
	 * @throws IllegalArgumentException Caso <code>algoritmo</code> não seja a instância referenciada pelo modelo.
	 */
	public void setValorAtual(double valor, Algoritmo algoritmo){
		if(algoritmo != getModelo().getAlgoritmoIntegracao()){
			throw new IllegalArgumentException("Operação não permitida.");
		}
		valorAtual = valor;
		calculadoEm[ getModelo().getCicloAtual() ] = true;
		getHistorico().setValor(getModelo().getCicloAtual(), valorAtual);
	}
	
	/**
	 * Retorna o valor atual deste componente.
	 */
	public double getValorAtual(){
		return valorAtual;
	}
	
	/**
	 * Indica se a expressão do componente pode ser alterada pelo 
	 * usuário do modelo durante a simulação.
	 * @return
	 */
	public boolean isAlteravel() {
		return alteravel;
	}
	
	/**
	 * Indica se o valor do componente já foi calculado em determinado ciclo.
	 * @param ciclo	Ciclo que se deseja obter a informação.
	 * @return	<code>true</code> - caso já tenha sido calculado. <code>false</code> em caso contrário.
	 */
	public boolean isCalculadoEm(int ciclo){
		return calculadoEm[ciclo];
	}
	
	/**
	 * Retorna a ordem de execução deste componente.
	 * @return
	 */
	public int getOrdem() {
		return ordem;
	}
	
	/**
	 * Define a ordem de execução deste componente.
	 * @param ordem
	 */
	void setOrdem(int ordem) {
		this.ordem = ordem;
	}
}
