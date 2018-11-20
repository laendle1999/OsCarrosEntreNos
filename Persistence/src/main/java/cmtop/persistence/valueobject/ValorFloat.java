package cmtop.persistence.valueobject;

public class ValorFloat extends Valor {

	private float valor;

	public ValorFloat(float valor) {
		super(TipoValor.FLOAT);
		this.valor = valor;
	}

	@Override
	public int getAsInt() {
		return (int) valor;
	}

	@Override
	public float getAsFloat() {
		return valor;
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
