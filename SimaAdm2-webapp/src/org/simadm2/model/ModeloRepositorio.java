package org.simadm2.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import core.Constante;
import core.Estoque;
import core.Fluxo;
import core.InterpretadorException;
import core.Modelo;
import core.ModeloException;
import core.NomeDuplicadoException;
import core.VariavelAuxiliar;
import core.algoritmo.RungeKutta4;

@ManagedBean
@SessionScoped
public class ModeloRepositorio {

	public Modelo getModeloExemplo(){
		try{
			Modelo model = new Modelo(0.25, 0, 10);
			model.setDescricao("modeloExemplo");
			model.setAlgoritmoDeIntegracao(new RungeKutta4(model));
			Estoque e1 = new Estoque("e1", 1000000, model);
			VariavelAuxiliar fbr = new VariavelAuxiliar("fbr", "0.04", true, model);
			VariavelAuxiliar al = new VariavelAuxiliar("al", "80", true, model);
			Fluxo f1 = new Fluxo("f1", "fbr * e1", null, e1, false, model);
			f1.adicionarInfluencia(e1);
			f1.adicionarInfluencia(fbr);
			Fluxo f2 = new Fluxo("f2", "e1 / al", e1, null, false, model);
			f2.adicionarInfluencia(e1);
			f2.adicionarInfluencia(al);

			
			return model;
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public Modelo getModeloFinanceiro() {
		try{
		Modelo modelo = new Modelo(1, 0, 10);
		modelo.setDescricao("modelo financeiro");
		//constantes
			Constante custoUnitario = new Constante("custo_unitario", 6.0, modelo);
			Constante tempoAmortizacao = new Constante("tempo_amortizacao", 5.0, modelo);
			Constante investimentoInicial = new Constante("investimento_inicial", 15000.0, modelo);
		//variáveis
			//a equação de demanda gera um inteiro aleatório entre 900 e 1100. Como rand() gera um real 
			//entre 0 e 1, fazemos o cálculo para o intervalo desejado
			VariavelAuxiliar demanda = new VariavelAuxiliar("demanda", "round(rand() * (1100 - 900 + 1) + 900)", false, modelo);
			//preço unitário é um valor aleatório entre 9 (inclusive) e 11 (exclusivo)
			VariavelAuxiliar preco_unitario = new VariavelAuxiliar("preco_unitario", "round(rand() * (10 - 9 + 1) + 9, 2)", false, modelo);
			//taxa de juros é um valor aleatório entre 12 (inclusive) e 15 (exclusivo)
			VariavelAuxiliar taxaJuros = new VariavelAuxiliar("taxa_juros", "round(rand() * (14 - 12 + 1) + 12, 2)", false, modelo);
			VariavelAuxiliar faturamentoBruto = new VariavelAuxiliar("faturamento_bruto", "vendas * preco_unitario", false, modelo);
			VariavelAuxiliar custoTotal = new VariavelAuxiliar("custo_total", "custo_producao + custo_financeiro", false, modelo);
			VariavelAuxiliar resultado = new VariavelAuxiliar("resultado", "faturamento_bruto - custo_total", false, modelo);
			VariavelAuxiliar custoProducao = new VariavelAuxiliar("custo_producao", "producao * custo_unitario", false, modelo);
			VariavelAuxiliar custoFinanceiro = new VariavelAuxiliar("custo_financeiro", "amortizacao + juros", false, modelo);
			VariavelAuxiliar juros = new VariavelAuxiliar("juros", "divida * taxa_juros / 100", false, modelo);
		//estoques
			Estoque divida = new Estoque("divida", investimentoInicial, modelo);
			Estoque estoque = new Estoque("estoque", 1000.0, modelo);
		//fluxos
			Fluxo amortizacao = new Fluxo("amortizacao", "divida / tempo_amortizacao", divida, null, false, modelo);
			Fluxo emprestimos = new Fluxo("emprestimos", "if(resultado < 0, resultado, 0)", null, divida, false, modelo);
			Fluxo producao = new Fluxo("producao", "investimento_inicial / 15", null, estoque, false, modelo);
			Fluxo vendas = new Fluxo("vendas", "min(demanda, estoque)", estoque, null, false, modelo);
		//influências
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
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Modelo> getTodos(){
		ArrayList<Modelo> todos = new ArrayList<Modelo>();
		todos.add(getModeloExemplo());
		todos.add(getModeloFinanceiro());
		
		return todos;
	}
}
