package cmtop.persistence.valueobject;

public abstract class Valor {

	private final TipoValor tipoValor;

	public Valor(TipoValor tipoValor) {
		this.tipoValor = tipoValor;
	}

	public abstract int getAsInt();

	public abstract float getAsFloat();

	public abstract double getAsDouble();

	public abstract String getAsString();

	public TipoValor getTipo() {
		return tipoValor;
	}

}
