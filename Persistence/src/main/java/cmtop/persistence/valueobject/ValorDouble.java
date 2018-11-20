package cmtop.persistence.valueobject;

public class ValorDouble extends Valor {

	private double valor;

	public ValorDouble(double valor) {
		super(TipoValor.DOUBLE);
		this.valor = valor;
	}

	@Override
	public int getAsInt() {
		return (int) valor;
	}

	@Override
	public float getAsFloat() {
		return (float) valor;
	}

	@Override
	public double getAsDouble() {
		return valor;
	}

	@Override
	public String getAsString() {
		return valor + "";
	}

}
