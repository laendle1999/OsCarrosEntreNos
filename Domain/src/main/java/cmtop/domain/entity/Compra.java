package cmtop.domain.entity;

public class Compra {

	private String localCompra, nomeFornecedor;

	private long data;

	private float custo;

	private Carro carro;

	public Compra(String localCompra, String nomeFornecedor, long data, float custo, Carro carro) {
		super();
		this.localCompra = localCompra;
		this.nomeFornecedor = nomeFornecedor;
		this.data = data;
		this.custo = custo;
		this.carro = carro;
	}

	public String getLocalCompra() {
		return localCompra;
	}

	public void setLocalCompra(String localCompra) {
		this.localCompra = localCompra;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}

	public float getCusto() {
		return custo;
	}

	public void setCusto(float custo) {
		this.custo = custo;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

}
