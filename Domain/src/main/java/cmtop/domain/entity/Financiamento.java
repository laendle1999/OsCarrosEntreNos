package cmtop.domain.entity;

public class Financiamento {

	private String banco;

	private float valorFinanciado;

	private int numeroParcelas;

	public Financiamento(String banco, float valorFinanciado, int numeroParcelas) {
		super();
		this.banco = banco;
		this.valorFinanciado = valorFinanciado;
		this.numeroParcelas = numeroParcelas;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public float getValorFinanciado() {
		return valorFinanciado;
	}

	public void setValorFinanciado(float valorFinanciado) {
		this.valorFinanciado = valorFinanciado;
	}

	public int getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(int numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

}
