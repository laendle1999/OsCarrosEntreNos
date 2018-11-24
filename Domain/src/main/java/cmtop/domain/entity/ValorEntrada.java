package cmtop.domain.entity;

public class ValorEntrada {

	private String descricao;

	private float valor;

	private int id_venda;

	public ValorEntrada(String descricao, float valor) {
		this.id_venda = -1;
		this.descricao = descricao;
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public int getIdVenda() {
		return id_venda;
	}

	public void setIdVenda(int id_venda) {
		this.id_venda = id_venda;
	}

}
