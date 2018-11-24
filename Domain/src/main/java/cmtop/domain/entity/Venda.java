package cmtop.domain.entity;

public class Venda {

	private String numeroVenda;

	private String dataVenda;

	private int carro;

	private int cliente;

	private int vendedor;

	private int id;

	public Venda(int id, String numeroVenda, String dataVenda, int cliente, int vendedor) {
		this.id = id;
		this.numeroVenda = numeroVenda;
		this.carro = -1;
		this.dataVenda = dataVenda;
		this.cliente = cliente;
		this.vendedor = vendedor;
	}

	public String getNumeroVenda() {
		return numeroVenda;
	}

	public void setNumeroVenda(String numeroVenda) {
		this.numeroVenda = numeroVenda;
	}

	public String getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(String dataVenda) {
		this.dataVenda = dataVenda;
	}

	public int getCarro() {
		return carro;
	}

	public void setCarro(int carro) {
		this.carro = carro;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public int getVendedor() {
		return vendedor;
	}

	public void setVendedor(int vendedor) {
		this.vendedor = vendedor;
	}

	public int getId() {
		return id;
	}

}
