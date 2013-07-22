package org.simadm2.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sun.org.apache.bcel.internal.generic.NEW;

import core.Constante;
import core.Estoque;
import core.Fluxo;
import core.Modelo;
import core.VariavelAuxiliar;
import core.algoritmo.RungeKutta4;

@ManagedBean
@SessionScoped
public class ModeloRepositorio {

	private double dt = 0.25;
	private int inicio;
	private int fim;
	private String descricao;
    private String delete;

	private Modelo modeloCad = new Modelo(dt, inicio, fim);

	static ArrayList<Modelo> todos = new ArrayList<Modelo>();
	 static ArrayList<ModeloWeb> todos2 = new ArrayList<ModeloWeb>();
	 static ArrayList<ModeloWeb> disponivel = new ArrayList<ModeloWeb>();
	
	

	public ModeloRepositorio() {
		ModeloWeb modeloWeb = new ModeloWeb(modeloCad, false, false, delete, delete);
		modeloWeb.setModelo(getModeloExemplo());
		modeloWeb.setContexto("Este é um modelo de exemplo muito simples.");
		modeloWeb.setDisponivelAsTurmas(true);
		modeloWeb.setDisponivelAOutrosProfessores(true);
		modeloWeb.setResumo("Modelo simples de exemplo");
		//TODO criar modeloWeb com os modelos abaixo
		ModeloWeb modeloWeb2 = new ModeloWeb(modeloCad, false, false, delete, delete);
		modeloWeb2.setModelo(getModeloFinanceiro());
		modeloWeb2.setContexto("Este é um modelo de Financeiro");
		modeloWeb2.setDisponivelAsTurmas(true);
		modeloWeb2.setResumo("Modelo simples de exemplo Financeiro");
		todos2.add(modeloWeb);
		todos2.add(modeloWeb2);
		//todos.add(getModeloFinanceiro());
		//todos.add(getModeloTeste());
	}

	public Modelo getModeloExemplo() {
		try {
			Modelo model = new Modelo(0.25, 0, 10);
			model.setDescricao("modeloExemplo");
			model.setAlgoritmoDeIntegracao(new RungeKutta4(model));
			Estoque e1 = new Estoque("e1", 1000000, model);
			VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", true,
					model);
			VariavelAuxiliar al = new VariavelAuxiliar("al", "80", true, model);
			Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
			f1.adicionarInfluencia(e1);
			f1.adicionarInfluencia(fbr);
			Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
			f2.adicionarInfluencia(e1);
			f2.adicionarInfluencia(al);

			return model;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Modelo getModeloTeste() {
		try {
			Modelo model = new Modelo(0.25, 0, 10);
			model.setDescricao("modeloTeste");
			model.setAlgoritmoDeIntegracao(new RungeKutta4(model));
			Estoque e1 = new Estoque("e1", 1000000, model);
			VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", true,
					model);
			VariavelAuxiliar al = new VariavelAuxiliar("al", "80", true, model);
			Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
			f1.adicionarInfluencia(e1);
			f1.adicionarInfluencia(fbr);
			Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
			f2.adicionarInfluencia(e1);
			f2.adicionarInfluencia(al);

			return model;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Modelo getModeloFinanceiro() {
		try {
			Modelo modelo = new Modelo(1, 0, 10);
			modelo.setDescricao("modelo financeiro");
			// constantes
			Constante custoUnitario = new Constante("custo_unitario", 6.0,
					modelo);
			Constante tempoAmortizacao = new Constante("tempo_amortizacao",
					5.0, modelo);
			Constante investimentoInicial = new Constante(
					"investimento_inicial", 15000.0, modelo);
			// vari�veis
			// a equa��o de demanda gera um inteiro aleat�rio entre 900 e 1100.
			// Como rand() gera um real
			// entre 0 e 1, fazemos o c�lculo para o intervalo desejado
			VariavelAuxiliar demanda = new VariavelAuxiliar("demanda",
					"round(rand() * (1100 - 900 + 1) + 900)", false, modelo);
			// pre�o unit�rio � um valor aleat�rio entre 9 (inclusive) e 11
			// (exclusivo)
			VariavelAuxiliar preco_unitario = new VariavelAuxiliar(
					"preco_unitario", "round(rand() * (10 - 9 + 1) + 9, 2)",
					false, modelo);
			// taxa de juros � um valor aleat�rio entre 12 (inclusive) e 15
			// (exclusivo)
			VariavelAuxiliar taxaJuros = new VariavelAuxiliar("taxa_juros",
					"round(rand() * (14 - 12 + 1) + 12, 2)", false, modelo);
			VariavelAuxiliar faturamentoBruto = new VariavelAuxiliar(
					"faturamento_bruto", "vendas * preco_unitario", false,
					modelo);
			VariavelAuxiliar custoTotal = new VariavelAuxiliar("custo_total",
					"custo_producao + custo_financeiro", false, modelo);
			VariavelAuxiliar resultado = new VariavelAuxiliar("resultado",
					"faturamento_bruto - custo_total", false, modelo);
			VariavelAuxiliar custoProducao = new VariavelAuxiliar(
					"custo_producao", "producao * custo_unitario", false,
					modelo);
			VariavelAuxiliar custoFinanceiro = new VariavelAuxiliar(
					"custo_financeiro", "amortizacao + juros", false, modelo);
			VariavelAuxiliar juros = new VariavelAuxiliar("juros",
					"divida * taxa_juros / 100", false, modelo);
			// estoques
			Estoque divida = new Estoque("divida", investimentoInicial, modelo);
			Estoque estoque = new Estoque("estoque", 1000.0, modelo);
			// fluxos
			Fluxo amortizacao = new Fluxo("amortizacao",
					"divida / tempo_amortizacao", divida, null, false, modelo);
			Fluxo emprestimos = new Fluxo("emprestimos",
					"if(resultado < 0, resultado, 0)", null, divida, false,
					modelo);
			Fluxo producao = new Fluxo("producao", "investimento_inicial / 15",
					null, estoque, false, modelo);
			Fluxo vendas = new Fluxo("vendas", "min(demanda, estoque)",
					estoque, null, false, modelo);
			// influ�ncias
			faturamentoBruto.adicionarInfluencia(vendas);
			faturamentoBruto.adicionarInfluencia(preco_unitario);
			amortizacao.adicionarInfluencia(divida);
			amortizacao.adicionarInfluencia(tempoAmortizacao);
			emprestimos.adicionarInfluencia(resultado);
			vendas.adicionarInfluencia(estoque);
			vendas.adicionarInfluencia(demanda);
			producao.adicionarInfluencia(investimentoInicial);
			custoTotal.adicionarInfluencia(custoProducao);
			custoTotal.adicionarInfluencia(custoFinanceiro);
			resultado.adicionarInfluencia(faturamentoBruto);
			resultado.adicionarInfluencia(custoTotal);
			custoProducao.adicionarInfluencia(producao);
			custoProducao.adicionarInfluencia(custoUnitario);
			custoFinanceiro.adicionarInfluencia(amortizacao);
			custoFinanceiro.adicionarInfluencia(juros);
			juros.adicionarInfluencia(divida);
			juros.adicionarInfluencia(taxaJuros);

			return modelo;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void cadastrarModelo() {
		try {
			modeloCad.setDescricao(descricao);
			// modeloCad = new Modelo(dt, inicio, fim);
			todos.add(modeloCad);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void deletarModelo(){
		System.out.println(delete);
	    for (int i = 0; i < todos.size(); i++) {  
	        if (todos.get(i).getDescricao().equals(delete)) {  
	            delete += todos.remove(i);  
	        }  
	    }  
	}
	
	public void deletarModeloWeb(){
		System.out.println(delete);
	    for (int i = 0; i < todos2.size(); i++) {  
	        if (todos2.get(i).getModelo().getDescricao().equals(delete)) {  
	            delete += todos2.remove(i);  
	        }  
	    }  
	}
	
	public void deletarModeloDisp(){
		System.out.println(delete);
	    for (int i = 0; i < disponivel.size(); i++) {  
	        if (disponivel.get(i).getModelo().getDescricao().equals(delete)) {  
	            delete += disponivel.remove(i);  
	        }  
	    }  
	}

	public void addTodos(Modelo mod){
		todos.add(mod);
		System.out.println("Passou aki" + mod);
	}
	public void addTodos2(ModeloWeb mod2){
		todos2.add(mod2);
		System.out.println("Passou aki2 " + mod2);
	}
	
	public void addDisponivel(ModeloWeb disp){
		disponivel.add(disp);
		System.out.println("Passou aki3 " + disp);
	}
	
	// =================================== GETS SETS
	// ================================
	public List<Modelo> getTodos() {
		// todos.add(getModeloExemplo());
		// todos.add(getModeloFinanceiro());
		// todos.add(getModeloTeste());
		return todos;
	}
	
	public List<ModeloWeb> getTodos2() {
		return todos2;
	}
	
	public  ArrayList<ModeloWeb> getDisponivel() {
		return disponivel;
	}

	public static void setDisponivel(ArrayList<ModeloWeb> disponivel) {
		ModeloRepositorio.disponivel = disponivel;
	}

	public Modelo getModeloCad() {
		return modeloCad;
	}

	public void setModel(Modelo model) {
		this.modeloCad = model;
	}

	public double getDt() {
		return dt;
	}

	public void setDt(double dt) {
		this.dt = dt;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFim() {
		return fim;
	}

	public void setFim(int fim) {
		this.fim = fim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setModeloCad(Modelo modeloCad) {
		this.modeloCad = modeloCad;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}
	
}
