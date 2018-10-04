package domain.entity;

public class Manutencao {

	private String descricao;

	private long data;

	private float custo;

	private Carro carro;
	
	public Manutencao(String descricao, long data, float custo, Carro carro) {
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
		return this.carro;
	}

}
