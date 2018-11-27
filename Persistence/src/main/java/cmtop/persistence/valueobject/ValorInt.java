package cmtop.persistence.valueobject;

public class ValorInt extends Valor {

	private int valor;

	public ValorInt(int valor) {
		super(TipoValor.INT);
		this.valor = valor;
	}

	@Override
	public int getAsInt() {
		return valor;
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

	@Override
	public long getAsLong() {
		return valor;
	}

}
