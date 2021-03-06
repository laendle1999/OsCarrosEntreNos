package cmtop.domain.entity;

public class Compra {

	private String localCompra, nomeFornecedor;

	private long data;

	private float custo;

	private int carro;

	public Compra(String localCompra, String nomeFornecedor, long data, float custo, int id_carro) {
		this.localCompra = localCompra;
		this.nomeFornecedor = nomeFornecedor;
		this.data = data;
		this.custo = custo;
		this.carro = id_carro;
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

	public int getCarro() {
		return carro;
	}

	public void setCarro(int carro) {
		this.carro = carro;
	}

}
