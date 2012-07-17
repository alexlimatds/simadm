package core;


/**
 * Contém as características comuns aos elementos que podem estar 
 * presentes em um modelo de fluxos e estoques.
 * 
 * @author Alexandre
 */
public abstract class ComponenteDeModelo {
	private String nome;
	private HistoricoComponente historico;
	private Modelo modelo;
	private String unidade;
	
	/**
	 * Cria uma instância desta classe.
	 * 
	 * @param nome	Identificador deste componente.
	 * @param modelo	Modelo ao qual este componente pertence.
	 * @throws NomeDuplicadoException	Caso o modelo já possua um 
	 * 			componente com o nome passado como parâmetro.
	 */
	public ComponenteDeModelo(String nome, Modelo modelo) throws 
	NomeDuplicadoException{
		this.nome = nome;
		this.modelo = modelo;
		historico = new HistoricoComponente(this);
		modelo.adicionarComponente(this);
	}
	
	public Modelo getModelo() {
		return modelo;
	}
	public HistoricoComponente getHistorico() {
		return historico;
	}
	public String getNome() {
		return nome;
	}
	public abstract double getValorAtual();
	/**
	 * @return Returns the unidade.
	 */
	public String getUnidade() {
		return unidade;
	}
	/**
	 * @param unidade The unidade to set.
	 */
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
}
