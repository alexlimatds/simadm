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
	private int cicloAtual;
	private VariavelAuxiliar componenteTempo;
	private Algoritmo algoritmo;
	//contém os componentes influenciáveis em ordem de avaliação.
	private List<ComponenteInfluenciavel> listaDeAvaliacao;
	
	/**
	 * Cria um novo modelo.
	 * 
	 * @param dt		Fração de tempo (passo) na qual a simulação será executada.
	 * @param inicio	Tempo inicial da simulação.
	 * @param fim		Tempo final da simulação.
	 * @throws ModeloException
	 */
	public Modelo(double dt, int inicio, int fim) throws ModeloException{
		if(fim < inicio){
			throw new ModeloException("fim < inicio");
		}
		if(dt <= 0){
			throw new ModeloException("dt <= 0");
		}
		
		this.dt = dt;
		this.inicio = inicio;
		this.tempo = inicio;
		this.fim = fim;
		this.qtdCiclos = (int)((this.fim - this.inicio) / this.dt) + 1;
		componentes = new HashMap<String, ComponenteDeModelo>();
		estoques = new HashSet<Estoque>();
		this.algoritmo = new MetodoDeEuler(this);
		listaDeAvaliacao = new ArrayList<ComponenteInfluenciavel>();
		
		try {
			componenteTempo = new VariavelAuxiliar("tempo", String.valueOf(inicio), true, this);
			adicionarComponente(componenteTempo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Executa um step (dt) de simulação.
	 */
	public void simular() throws InterpretadorException{
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
			cicloAtual++;
		}
	}
	
	/**
	 * Define o algoritmo de integração a ser utilizado na simulação.
	 * @param alg	O algorimo a ser utilizado. Consulte as classes do pacote <code>core.Algoritmo</code>.
	 * @throws ModeloException	Caso este método seja chamado após o início da simulação.
	 */
	public void setAlgoritmoDeIntegracao(Algoritmo alg) throws ModeloException{
		if( tempo == inicio ){
			algoritmo = alg;
		}
		else{
			throw new ModeloException("Não é possível alterar o algoritmo após o início da simulação.");
		}
	}
	
	/* 
	 */
	private void calcularVariaveisEFluxos() throws InterpretadorException{
		algoritmo.calcularVariaveisEFluxos();
	}
	
	/* Prepara a lista da ordem de avaliação dos componentes.
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
	 * Retorna a ordem de execução de um componente influenciável.
	 */
	private int getOrdem(ComponenteInfluenciavel comp){
		if(comp.getInfluencias().size() == 0){
			//comp não é influenciado por outros componentes, assim possui a prioridade (ordem)
			//de avaliação mais alta
			return 0;
		}
		else{
			int ordem = 0, temp;
			for(ComponenteDeModelo obj : comp.getInfluencias().values()){
				if(obj instanceof ComponenteInfluenciavel){
					//comp é influenciado por um componente influenciável, assim
					//a influência de comp pode ser influenciado por um terceiro componente,
					//o qual pode ser influenciado por um quarto componente e assim por diante.
					//Desta forma, usa recursão para descobrir a "profundidade" da prioridade 
					//(ordem) de comp
					temp = getOrdem((ComponenteInfluenciavel)obj) + 1; //recursão
				}
				else{
					//comp é influenciado por um componente que não é influenciável, assim
					//a influência de comp não possui influências e sua prioridade (ordem)
					//de avaliação é a segunda mais alta
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
		return cicloAtual;
	}

	public int getQtdCiclos() {
		return qtdCiclos;
	}

	public double getDt() {
		return dt;
	}

	public Set<Estoque> getEstoques() {
		return Collections.unmodifiableSet( estoques );
	}
	
	public void adicionarComponente(ComponenteDeModelo componente) 
	throws NomeDuplicadoException{
		componentes.put(componente.getNome(), componente);
		if( componente instanceof Estoque ){
			estoques.add((Estoque)componente);
		}
	}
	
	/**
	 * Retorna o tempo atual da simulação. Note que há uma diferença entre 
	 * tempo atual e ciclo atual. O tempo, por exemplo, pode iniciar em 10 
	 * (representando que a simulação teve início no mês de outubro) enquanto 
	 * que o ciclo sempre inciará em zero. Neste exemplo, na primeira rodada de 
	 * simulação, o tempo atual será 10 e o ciclo atual será zero.
	 * @return
	 */
	public double getTempo() {
		return tempo;
	}
	
	/**
	 * Retorna uma lista com os componentes influenciáveis do modelo. 
	 * Os elementos da lista estão ordenados de acordo com a sua ordem de 
	 * execução.
	 * 
	 * @return	Uma lista contendo instâncias de <code>ComponenteInfluenciavel</code>.
	 */
	public List<ComponenteInfluenciavel> getListaDeAvaliacao() {
		return listaDeAvaliacao;
	}
	
	/**
	 * Verifica se uma expressão possui erros de sintaxe.
	 * @param expr
	 * @throws InterpretadorException
	 */
	public void validarExpressao(String expr, String[] nomesVariaveis) throws 
	InterpretadorException{
		algoritmo.validarExpressao(expr);
	}

	public Map<String, ComponenteDeModelo> getComponentes() {
		return componentes;
	}
}

/**
 * Classe que, baseada na ordem de execução, faz a comparação 
 * entre dois componentes influenciáveis.
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
