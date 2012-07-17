package core;

import junit.framework.TestCase;

/**
 * Caso de teste baseado no modelo descrito no capítulo 5 do texto Introdução à 
 * Dinâmica de Sistemas de autoria de Paulo R. C. Villela.
 *  
 * @author Alexandre
 */
public class ModeloFinanceiroTest extends TestCase {
	
	/**
	 * Monta um modelo financeiro e verifica os resultados.
	 */
	public void testSimulacao() throws Exception {
		Modelo modelo = new Modelo(1, 0, 10);
		//variáveis
			VariavelAuxiliar investimentoInicial = new VariavelAuxiliar("investimento_inicial", "15000", false, modelo);
			//a equação de demanda gera um inteiro aleatório entre 900 e 1100. Como rand() gera um real 
			//entre 0 e 1, fazemos o cálculo para o intervalo desejado
			VariavelAuxiliar demanda = new VariavelAuxiliar("demanda", "round(rand() * (1100 - 900 + 1) + 900)", false, modelo);
			//preço unitário é um valor aleatório entre 9 (inclusive) e 11 (exclusivo)
			VariavelAuxiliar preco_unitario = new VariavelAuxiliar("preco_unitario", "round(rand() * (10 - 9 + 1) + 9, 2)", false, modelo);
			VariavelAuxiliar custoUnitario = new VariavelAuxiliar("custo_unitario", "6", false, modelo);
			//taxa de juros é um valor aleatório entre 12 (inclusive) e 15 (exclusivo)
			VariavelAuxiliar taxaJuros = new VariavelAuxiliar("taxa_juros", "round(rand() * (14 - 12 + 1) + 12, 2)", false, modelo);
			VariavelAuxiliar tempoAmortizacao = new VariavelAuxiliar("tempo_amortizacao", "5", false, modelo);
			VariavelAuxiliar faturamentoBruto = new VariavelAuxiliar("faturamento_bruto", "vendas * preco_unitario", false, modelo);
			VariavelAuxiliar custoTotal = new VariavelAuxiliar("custo_total", "custo_producao + custo_financeiro", false, modelo);
			VariavelAuxiliar resultado = new VariavelAuxiliar("resultado", "faturamento_bruto - custo_total", false, modelo);
			VariavelAuxiliar custoProducao = new VariavelAuxiliar("custo_producao", "producao * custo_unitario", false, modelo);
			VariavelAuxiliar custoFinanceiro = new VariavelAuxiliar("custo_financeiro", "amortizacao + juros", false, modelo);
			VariavelAuxiliar juros = new VariavelAuxiliar("juros", "divida * taxa_juros / 100", false, modelo);
		//estoques
			Estoque divida = new Estoque("divida", investimentoInicial, modelo);
			Estoque estoque = new Estoque("estoque", 1000, modelo);
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
		
		for(int i = 0; i < 11; i++){
			modelo.simular();
		}
		
		//verifica demanda
		HistoricoComponente historico = demanda.getHistorico();
		double[] valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			assertTrue("Na iteração "+i+" valor foi " + valor, valor <= 1100 && valor >= 900);
		}
		//verifica preço unitário
		historico = preco_unitario.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			assertTrue("Na iteração "+i+" valor foi " + valor, valor <= 11 && valor >= 9);
		}
		//verifica taxa de juros
		historico = taxaJuros.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			assertTrue("Na iteração "+i+" valor foi " + valor, valor <= 15 && valor >= 12);
		}
		//verifica investimento inicial
		historico = investimentoInicial.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			assertEquals(15000, valor, 0.01);
		}
		//verifica custo unitário
		historico = custoUnitario.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			assertEquals(6, valor, 0.01);
		}
		//verifica tempo de amortização
		historico = tempoAmortizacao.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			assertEquals(5, valor, 0.01);
		}
		//verifica faturamento bruto
		historico = faturamentoBruto.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			double esperado = vendas.getHistorico().getValor(i) * preco_unitario.getHistorico().getValor(i);
			assertEquals(esperado, valor, 0.01);
		}
		//verifica custo total
		historico = custoTotal.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			double esperado = custoProducao.getHistorico().getValor(i) + custoFinanceiro.getHistorico().getValor(i);
			assertEquals(esperado, valor, 0.01);
		}
		//verifica resultado
		historico = resultado.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			double esperado = faturamentoBruto.getHistorico().getValor(i) - custoTotal.getHistorico().getValor(i);
			assertEquals(esperado, valor, 0.01);
		}
		//verifica custo de produção
		historico = custoProducao.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			double esperado = producao.getHistorico().getValor(i) * custoUnitario.getHistorico().getValor(i);
			assertEquals(esperado, valor, 0.01);
		}
		//verifica custo financeiro
		historico = custoFinanceiro.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			double esperado = amortizacao.getHistorico().getValor(i) + juros.getHistorico().getValor(i);
			assertEquals(esperado, valor, 0.01);
		}
		//verifica juros
		historico = juros.getHistorico();
		valores = historico.getValores();
		for(int i = 0; i < valores.length; i++){
			double valor = valores[i];
			double esperado = divida.getHistorico().getValor(i) * taxaJuros.getHistorico().getValor(i) / 100.0;
			assertEquals(esperado, valor, 0.01);
		}
	}
	
}
