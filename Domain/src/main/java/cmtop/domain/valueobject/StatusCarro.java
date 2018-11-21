package cmtop.domain.valueobject;

import java.security.InvalidParameterException;

public enum StatusCarro {
	DISPONIVEL, VENDIDO, EM_NEGOCIACAO;

	public int asInt() {
		switch (this) {
		case DISPONIVEL:
			return 0;
		case VENDIDO:
			return 1;
		case EM_NEGOCIACAO:
			return 2;
		default:
			throw new Error("Unimplemented");
		}
	}

	public static StatusCarro fromInt(int i) {
		switch (i) {
		case 0:
			return StatusCarro.DISPONIVEL;
		case 1:
			return StatusCarro.VENDIDO;
		case 2:
			return StatusCarro.EM_NEGOCIACAO;
		default:
			throw new InvalidParameterException();
		}
	}
}
