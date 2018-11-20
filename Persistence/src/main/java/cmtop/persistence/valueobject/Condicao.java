package cmtop.persistence.valueobject;

import java.util.ArrayList;
import java.util.List;

public class Condicao {

	private List<CampoCondicao> campos = new ArrayList<>();

	public void add(CampoCondicao campo) {
		campos.add(campo);
	}

	public CampoCondicao add(String nome, TipoCondicao condicao, Valor valor) {
		CampoCondicao campoCondicao = new CampoCondicao(nome, condicao, valor);
		add(campoCondicao);
		return campoCondicao;
	}

	public void remove(CampoCondicao campo) {
		campos.remove(campo);
	}

	public String toSQL() {
		StringBuilder source = new StringBuilder();

		for (int i = 0; i < campos.size(); i++) {
			CampoCondicao campo = campos.get(i);
			source.append(campo.toSQL());

			if (i + 1 < campos.size()) {
				source.append(" AND ");
			}
		}

		return source.toString();
	}

}
