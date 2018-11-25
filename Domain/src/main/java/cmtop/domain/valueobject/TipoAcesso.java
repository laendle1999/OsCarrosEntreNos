package cmtop.domain.valueobject;

import java.security.InvalidParameterException;

public enum TipoAcesso {
	GERENTE, VENDEDOR;

	public int asInt() {
		switch (this) {
		case GERENTE:
			return 0;
		case VENDEDOR:
			return 1;
		default:
			throw new Error("Unimplemented");
		}
	}

	public static TipoAcesso fromInt(int i) {
		switch (i) {
		case 0:
			return TipoAcesso.GERENTE;
		case 1:
			return TipoAcesso.VENDEDOR;
		default:
			throw new InvalidParameterException();
		}
	}
}
