package cmtop.persistence.valueobject;

public class ValorLong extends Valor {

	private long valor;

	public ValorLong(long valor) {
		super(TipoValor.LONG);
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
	public long getAsLong() {
		return valor;
	}

	@Override
	public String getAsString() {
		return valor + "";
	}

}
