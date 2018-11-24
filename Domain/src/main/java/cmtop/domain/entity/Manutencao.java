package cmtop.domain.entity;

public class Manutencao {

	private String descricao;

	private String data;

	private float custo;

	private int carro;

	private int id;

	public Manutencao(int id, String descricao, String data, float custo, int carro) {
		this.id = id;
		this.descricao = descricao;
		this.data = data;
		this.custo = custo;
		this.carro = carro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public float getCusto() {
		return custo;
	}

	public void setCusto(float custo) {
		this.custo = custo;
	}

	public void setCarro(int carro) {
		this.carro = carro;
	}

	public int getCarro() {
		return this.carro;
	}

	public int getId() {
		return id;
	}

}
