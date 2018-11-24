package cmtop.busca;

import java.util.HashMap;

public class ValoresCamposBusca extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;

	public void adicionarCampo(String nome, String valor) {
		put(nome, valor);
	}

	public String obterCampo(String nome, String valor) {
		return get(nome);
	}

	public void removerCampo(String nome) {
		remove(nome);
	}

}
