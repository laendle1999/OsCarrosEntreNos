package cmtop.persistence.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmtop.persistence.valueobject.TipoValor;
import cmtop.persistence.valueobject.Valor;

public class Registro {

	private final Map<String, Valor> map = new HashMap<>();

	public void set(String chave, Valor valor) {
		String upperCaseChave = chave.toUpperCase();
		this.map.put(upperCaseChave, valor);
	}

	public Valor get(String chave) {
		String upperCaseChave = chave.toUpperCase();
		return map.get(upperCaseChave);
	}

	public boolean has(String chave) {
		String upperCaseChave = chave.toUpperCase();
		return map.containsKey(upperCaseChave);
	}

	public List<String> getChaves() {
		return new ArrayList<>(map.keySet());
	}

	@Override
	public String toString() {
		StringBuilder source = new StringBuilder("{");

		List<String> chaves = new ArrayList<>(map.keySet());
		for (int i = 0; i < chaves.size(); i++) {
			source.append(chaves.get(i) + ": " + map.get(chaves.get(i)));

			if (i + 1 < map.size()) {
				source.append(", ");
			}
		}

		source.append("}");

		return source.toString();
	}

	public String getSQLSET() {
		StringBuilder source = new StringBuilder();

		List<String> chaves = new ArrayList<>(map.keySet());
		for (int i = 0; i < chaves.size(); i++) {
			String chave = chaves.get(i);
			Valor valor = map.get(chave);

			source.append(chave + " = ");

			if (valor.getTipo() == TipoValor.STRING) {
				source.append("'");
			}

			source.append(valor);

			if (valor.getTipo() == TipoValor.STRING) {
				source.append("'");
			}

			if (i + 1 < map.size()) {
				source.append(", ");
			}
		}

		return source.toString();
	}

	public String getSQLKeys() {
		String sql = "";

		List<String> chaves = new ArrayList<>(map.keySet());
		for (int i = 0; i < chaves.size(); i++) {
			sql += chaves.get(i);

			if (i + 1 < chaves.size()) {
				sql += ", ";
			}
		}

		return sql;
	}

	public String getSQLValues() {
		String sql = "";

		List<String> chaves = new ArrayList<>(map.keySet());
		for (int i = 0; i < chaves.size(); i++) {
			Valor valor = this.get(chaves.get(i));

			if (valor.getTipo() == TipoValor.STRING) {
				sql += "'";
			}
			sql += valor;
			if (valor.getTipo() == TipoValor.STRING) {
				sql += "'";
			}

			if (i + 1 < chaves.size()) {
				sql += ", ";
			}
		}

		return sql;
	}

}
