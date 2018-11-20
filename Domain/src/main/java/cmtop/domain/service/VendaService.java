package cmtop.domain.service;

import cmtop.domain.aggregate.Pagamento;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
import cmtop.domain.entity.Financiamento;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.entity.ValorEntrada;
import cmtop.domain.entity.Venda;
import cmtop.domain.entity.Vendedor;

public class VendaService {

	private static Venda vendaEmAndamento;

	public static void iniciarVenda(String numeroVenda, Cliente cliente, Vendedor vendedor) {
		long dataVenda = System.currentTimeMillis() / 1000; // Timestamp em segundos

		vendaEmAndamento = new Venda(numeroVenda, dataVenda, null, null, new Pagamento(), cliente, vendedor);
	}

	public static void adicionarFinanciamento(Financiamento financiamento) {
		vendaEmAndamento.getPagamento().adicionarFinanciamento(financiamento);
	}

	public static void adicionarTrocaCarro(TrocaCarro trocaCarro) {
		vendaEmAndamento.getPagamento().adicionarTrocaCarro(trocaCarro);
	}

	public static void adicionarValorEntrada(ValorEntrada valorEntrada) {
		vendaEmAndamento.getPagamento().adicionarValorEntrada(valorEntrada);
	}

	public static void venderCarro(Carro carro) {
		vendaEmAndamento.setCarro(carro);
	}

	public static void finalizarVenda() {
		// TODO
		// VendaRepository vendaRepository = new VendaRepository();
		// vendaRepository.gravarVenda(vendaEmAndamento);

		vendaEmAndamento = null;
	}
}
