package cmtop.domain.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cmtop.domain.aggregate.Pagamento;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
import cmtop.domain.entity.Financiamento;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.entity.ValorEntrada;
import cmtop.domain.entity.Venda;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.FinanciamentoRepository;
import cmtop.domain.repository.TrocaCarroRepository;
import cmtop.domain.repository.ValorEntradaRepository;
import cmtop.domain.repository.VendaRepository;
import cmtop.persistence.entity.Banco;

public class VendaService {

	private Venda venda;

	private Pagamento pagamento;

	private Carro carro;

	private Banco banco;

	public VendaService(Banco banco) {
		this.banco = banco;
	}

	public void iniciarVenda(String numeroVenda, Cliente cliente, Vendedor vendedor) {
		String dataVenda = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH).format(new Date());

		venda = new Venda(-1, numeroVenda, dataVenda, cliente.getId(), vendedor.getId());
		pagamento = new Pagamento();
	}

	public void adicionarFinanciamento(Financiamento financiamento) {
		financiamento.setIdVenda(venda.getId());
		pagamento.adicionarFinanciamento(financiamento);
	}

	public void adicionarTrocaCarro(TrocaCarro trocaCarro) {
		pagamento.adicionarTrocaCarro(trocaCarro);
	}

	public void adicionarValorEntrada(ValorEntrada valorEntrada) {
		pagamento.adicionarValorEntrada(valorEntrada);
	}

	public void venderCarro(Carro carro) {
		this.carro = carro;
		venda.setCarro(carro.getId());
	}

	public void finalizarVenda() throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.definirCarroVendido(carro);

		// Salvar trocas de carro
		for (TrocaCarro trocaCarro : pagamento.getTrocasCarro()) {
			trocaCarro.setIdVenda(venda.getId());
			new TrocaCarroRepository(banco).adicionarCarroTroca(trocaCarro);
		}

		// Salvar financiamentos
		for (Financiamento financiamento : pagamento.getFinanciamentos()) {
			financiamento.setIdVenda(venda.getId());
			new FinanciamentoRepository(banco).adicionarFinanciamento(financiamento);
		}

		// Salvar valores de entrada
		for (ValorEntrada valorEntrada : pagamento.getValoresEntrada()) {
			valorEntrada.setIdVenda(venda.getId());
			new ValorEntradaRepository(banco).adicionarValorEntrada(valorEntrada);
		}

		VendaRepository vendaRepository = new VendaRepository(banco);
		vendaRepository.gravarVenda(venda);

		venda = null;
		carro = null;
		pagamento = null;
	}
}
