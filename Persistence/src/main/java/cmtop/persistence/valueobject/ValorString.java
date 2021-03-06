package cmtop.persistence.valueobject;

public class ValorString extends Valor {

	private String valor;

	public ValorString(String valor) {
		super(TipoValor.STRING);
		this.valor = valor;
	}

	@Override
	public int getAsInt() {
		return Integer.parseInt(valor);
	}

	@Override
	public float getAsFloat() {
		return Float.parseFloat(valor);
	}

	@Override
	public double getAsDouble() {
		return Double.parseDouble(valor);
	}

	@Override
	public long getAsLong() {
		return Long.parseLong(valor);
	}

	@Override
	public String getAsString() {
		return valor;
	}

}
