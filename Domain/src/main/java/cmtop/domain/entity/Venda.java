package cmtop.domain.entity;

import cmtop.domain.aggregate.Pagamento;

public class Venda {

	private String numeroVenda;

	private long dataVenda;

	private Carro carro;

	private NotaFiscal notaFiscal;

	private Pagamento pagamento;

	private Cliente cliente;

	private Vendedor vendedor;

	public Venda(String numeroVenda, long dataVenda, Carro carro, NotaFiscal notaFiscal, Pagamento pagamento,
			Cliente cliente, Vendedor vendedor) {
		this.numeroVenda = numeroVenda;
		this.dataVenda = dataVenda;
		this.carro = carro;
		this.notaFiscal = notaFiscal;
		this.pagamento = pagamento;
		this.cliente = cliente;
		this.vendedor = vendedor;
	}

	public String getNumeroVenda() {
		return numeroVenda;
	}

	public void setNumeroVenda(String numeroVenda) {
		this.numeroVenda = numeroVenda;
	}

	public long getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(long dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

}
