package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.algoritmo.Algoritmo;
import core.algoritmo.MetodoDeEuler;

public class Modelo {
	
	private Map<String, ComponenteDeModelo> componentes; //todos os componentes do model
	private Set<Estoque> estoques;
	private double dt;
	private double inicio;
	private double fim;
	private double tempo;
	private int qtdCiclos;
	private VariavelAuxiliar componenteTempo;
	private Algoritmo algoritmo;
	//cont�m os componentes influenci�veis em ordem de avalia��o.
	private List<ComponenteInfluenciavel> listaDeAvaliacao;
	private String descricao;
	
	/**
	 * Cria um novo modelo.
	 * 
	 * @param dt		Fra��o de tempo (passo) na qual a simula��o ser� executada.
	 * @param inicio	Tempo inicial da simula��o.
	 * @param fim		Tempo final da simula��o.
	 * @throws ModeloException
	 */
	public Modelo(double dt, int inicio, int fim){
		if(fim < inicio){
			throw new IllegalArgumentException("fim < inicio");
		}
		if(dt <= 0){
			throw new IllegalArgumentException("dt <= 0");
		}
		
		this.dt = dt;
		this.inicio = inicio;
		this.tempo = inicio;
		this.fim = fim;
		this.qtdCiclos = (int)(this.fim - this.inicio + 1);
		componentes = new HashMap<String, ComponenteDeModelo>();
		estoques = new HashSet<Estoque>();
		this.algoritmo = new MetodoDeEuler(this);
		listaDeAvaliacao = new ArrayList<ComponenteInfluenciavel>();
		
		try {
			componenteTempo = new VariavelAuxiliar("tempo", String.valueOf(inicio), true, this);
			adicionarComponente(componenteTempo);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar modelo: " + e.getMessage(), e);
		}
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	/**
	 * Executa uma unidade de tempo (ciclo) de simula��o.
	 */
	public void simular() throws InterpretadorException{
		if(tempo <= fim){
			int steps = (int)(1 / dt);
			for(int i = 0; i < steps; i++){
				executarStep();
			}
		}
	}
	
	/**
	 * Executa um step (dt) de simula��o.
	 */
	public void executarStep() throws InterpretadorException{
		if(tempo <= fim){
			if(tempo == inicio){
				prepapararListaDeAvaliacao();
				calcularVariaveisEFluxos();
			}
			else{
				calcularEstoques();
				calcularVariaveisEFluxos();
			}
			tempo = tempo + dt;
		}
	}
	
	/**
	 * Define o algoritmo de integra��o a ser utilizado na simula��o.
	 * @param alg	O algorimo a ser utilizado. Consulte as classes do pacote <code>core.Algoritmo</code>.
	 * @throws ModeloException	Caso este m�todo seja chamado ap�s o in�cio da simula��o.
	 */
	public void setAlgoritmoDeIntegracao(Algoritmo alg) throws ModeloException{
		if( tempo == inicio ){
			algoritmo = alg;
		}
		else{
			throw new ModeloException("N�o � poss�vel alterar o algoritmo ap�s o in�cio da simula��o.");
		}
	}
	
	/* 
	 */
	private void calcularVariaveisEFluxos() throws InterpretadorException{
		algoritmo.calcularVariaveisEFluxos();
	}
	
	/* Prepara a lista da ordem de avalia��o dos componentes.
	 */
	private void prepapararListaDeAvaliacao() throws InterpretadorException{
		//calculando a ordem dos componentes influenciaveis e inserindo-os na listaDeAvaliacao
		for(ComponenteDeModelo obj : componentes.values()){
			if( obj instanceof ComponenteInfluenciavel ){
				ComponenteInfluenciavel comp = (ComponenteInfluenciavel)obj;
				comp.setOrdem( getOrdem(comp) );
				listaDeAvaliacao.add(comp);
			}
		}
		//ordenando a lista
		Collections.sort(listaDeAvaliacao, new ComparadorDeOrdem());
	}
	
	/*
	 * Retorna a ordem de execu��o de um componente influenci�vel.
	 */
	private int getOrdem(ComponenteInfluenciavel comp){
		if(comp.getInfluencias().size() == 0){
			//comp n�o � influenciado por outros componentes, assim possui a prioridade (ordem)
			//de avalia��o mais alta
			return 0;
		}
		else{
			int ordem = 0, temp;
			for(ComponenteDeModelo obj : comp.getInfluencias().values()){
				if(obj instanceof ComponenteInfluenciavel){
					//comp � influenciado por um componente influenci�vel, assim
					//a influ�ncia de comp pode ser influenciado por um terceiro componente,
					//o qual pode ser influenciado por um quarto componente e assim por diante.
					//Desta forma, usa recurs�o para descobrir a "profundidade" da prioridade 
					//(ordem) de comp
					temp = getOrdem((ComponenteInfluenciavel)obj) + 1; //recurs�o
				}
				else{
					//comp � influenciado por um componente que n�o � influenci�vel, assim
					//a influ�ncia de comp n�o possui influ�ncias e sua prioridade (ordem)
					//de avalia��o � a segunda mais alta
					temp = 1;
				}
				
				if(temp > ordem){
					ordem = temp;
				}
			}
			return ordem;
		}
	}
	
	private void calcularEstoques(){
		algoritmo.calcularEstoques();
	}

	public int getCicloAtual() {
		//return (int)Math.ceil(cicloAtual);
		return (int)Math.ceil(tempo);
	}

	public int getQtdCiclos() {
		return qtdCiclos;
	}

	public double getDt() {
		return dt;
	}
	public void setDt(double dt) {
		this.dt = dt;
	}
	
	public double getInicio() {
		return inicio;
	}

	public void setInicio(double inicio) {
		this.inicio = inicio;
	}

	public double getFim() {
		return fim;
	}

	public void setFim(double fim) {
		this.fim = fim;
	}

	public Set<Estoque> getEstoques() {
		return Collections.unmodifiableSet( estoques );
	}
	
	public void adicionarComponente(ComponenteDeModelo componente) 
	throws NomeDuplicadoException{
		ComponenteDeModelo atual = componentes.get(componente.getNome());
		if(atual != null && atual != componente){
			throw new NomeDuplicadoException();
		}
		
		componentes.put(componente.getNome(), componente);
		if( componente instanceof Estoque ){
			estoques.add((Estoque)componente);
		}
	}
	
	/**
	 * Retorna o tempo atual da simula��o. Note que h� uma diferen�a entre 
	 * tempo atual e ciclo atual. O tempo, por exemplo, pode iniciar em 10 
	 * (representando que a simula��o teve in�cio no m�s de outubro) enquanto 
	 * que o ciclo sempre inciar� em zero. Neste exemplo, na primeira rodada de 
	 * simula��o, o tempo atual ser� 10 e o ciclo atual ser� zero.
	 * @return
	 */
	public double getTempo() {
		return tempo;
	}
	
	/**
	 * Retorna uma lista com os componentes influenci�veis do modelo. 
	 * Os elementos da lista est�o ordenados de acordo com a sua ordem de 
	 * execu��o.
	 * 
	 * @return	Uma lista contendo inst�ncias de <code>ComponenteInfluenciavel</code>.
	 */
	public List<ComponenteInfluenciavel> getListaDeAvaliacao() {
		return listaDeAvaliacao;
	}
	
	/**
	 * Verifica se uma express�o possui erros de sintaxe.
	 * @param expr           nova express�o
	 * @param nomesVariaveis vari�veis (influe�ncias) envolvidas na express�o
	 * @throws InterpretadorException
	 */
	public void validarExpressao(String expr, String[] nomesVariaveis) throws 
	InterpretadorException{
		algoritmo.validarExpressao(expr);
	}

	public Map<String, ComponenteDeModelo> getComponentes() {
		return componentes;
	}
	
	Algoritmo getAlgoritmoIntegracao(){
		return algoritmo;
	}
}

/**
 * Classe que, baseada na ordem de execu��o, faz a compara��o 
 * entre dois componentes influenci�veis.
 * @author Alexandre
 */
class ComparadorDeOrdem implements Comparator<ComponenteInfluenciavel>{

	public int compare(ComponenteInfluenciavel arg0, ComponenteInfluenciavel arg1) {
		ComponenteInfluenciavel comp1 = (ComponenteInfluenciavel)arg0;
		ComponenteInfluenciavel comp2 = (ComponenteInfluenciavel)arg1;
		
		if(comp1.getOrdem() == comp2.getOrdem())
			return 0;
		if(comp1.getOrdem() > comp2.getOrdem())
			return 1;
		if(comp1.getOrdem() < comp2.getOrdem())
			return -1;
		
		return 0;
	}
}
