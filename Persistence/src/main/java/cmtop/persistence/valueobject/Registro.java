package cmtop.persistence.valueobject;

import java.util.HashMap;
import java.util.Map;

public class Registro {

	private final Map<String, Valor> map = new HashMap<>();

	public void set(String chave, Valor valor) {
		this.map.put(chave, valor);
	}

	public Valor get(String chave) {
		return map.get(chave);
	}

}
