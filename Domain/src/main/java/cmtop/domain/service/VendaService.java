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

	private Pagamento pagamento;

	private Carro carro;

	private Cliente cliente;

	private Vendedor vendedor;

	private Banco banco;

	private String numeroVenda;

	public VendaService(Banco banco) {
		this.banco = banco;
		pagamento = new Pagamento();
	}

	public void definirNumero(String numeroVenda) {
		this.numeroVenda = numeroVenda;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Carro getCarro() {
		return carro;
	}

	public void adicionarFinanciamento(Financiamento financiamento) {
		pagamento.adicionarFinanciamento(financiamento);
	}

	public void adicionarTrocaCarro(TrocaCarro trocaCarro) {
		pagamento.adicionarTrocaCarro(trocaCarro);
	}

	public void adicionarValorEntrada(ValorEntrada valorEntrada) {
		pagamento.adicionarValorEntrada(valorEntrada);
	}

	public void escolherCarro(Carro carro) {
		this.carro = carro;
	}

	public void finalizarVenda() throws IOException {
		String dataVenda = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());

		Venda venda = new Venda(-1, numeroVenda, dataVenda, cliente.getId(), vendedor.getId());
		venda.setCarro(carro.getId());

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
