package cmtop.persistence.valueobject;

public enum TipoValor {
	INT, FLOAT, DOUBLE, STRING, LONG;

	@Override
	public String toString() {
		return name();
	}

	public static TipoValor fromString(String name) {
		return TipoValor.valueOf(name);
	}
}
