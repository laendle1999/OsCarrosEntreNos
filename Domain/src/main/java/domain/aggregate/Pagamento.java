package domain.aggregate;

import java.util.ArrayList;
import java.util.List;

import domain.entity.Financiamento;
import domain.entity.TrocaCarro;
import domain.entity.ValorEntrada;

public class Pagamento {

	private List<ValorEntrada> valoresEntrada = new ArrayList<>();

	private List<TrocaCarro> trocasCarro = new ArrayList<>();

	private List<Financiamento> financiamentos = new ArrayList<>();

	public List<ValorEntrada> getValoresEntrada() {
		return valoresEntrada;
	}

	public List<TrocaCarro> getTrocasCarro() {
		return trocasCarro;
	}

	public List<Financiamento> getFinanciamentos() {
		return financiamentos;
	}

	public void adicionarValorEntrada(ValorEntrada valorEntrada) {
		valoresEntrada.add(valorEntrada);
	}

	public void adicionarTrocaCarro(TrocaCarro trocaCarro) {
		trocasCarro.add(trocaCarro);
	}

	public void adicionarFinanciamento(Financiamento financiamento) {
		financiamentos.add(financiamento);
	}

	public void removerValorEntrada(ValorEntrada valorEntrada) {
		valoresEntrada.remove(valorEntrada);
	}

	public void removerTrocaCarro(TrocaCarro trocaCarro) {
		trocasCarro.remove(trocaCarro);
	}

	public void removerFinanciamento(Financiamento financiamento) {
		financiamentos.remove(financiamento);
	}

}
