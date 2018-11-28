package cmtop.persistence.entity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmtop.persistence.valueobject.TipoValor;
import cmtop.persistence.valueobject.Valor;
import cmtop.persistence.valueobject.ValorDouble;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class Registro {

	private final Map<String, Valor> map = new HashMap<>();
	private TipoBanco tipoBanco;

	public Registro(TipoBanco tipoBanco) {
		this.tipoBanco = tipoBanco;
	}

	public void set(String chave, Valor valor) {
		String novaChave = chave;
		if (tipoBanco == TipoBanco.DERBY) {
			novaChave = chave.toUpperCase();
		}
		this.map.put(novaChave, valor);
	}

	public Valor get(String chave) {
		String novaChave = chave;
		if (tipoBanco == TipoBanco.DERBY) {
			novaChave = chave.toUpperCase();
		}
		return map.get(novaChave);
	}

	public boolean has(String chave) {
		String novaChave = chave;
		if (tipoBanco == TipoBanco.DERBY) {
			novaChave = chave.toUpperCase();
		}
		return map.containsKey(novaChave);
	}

	public List<String> getChaves() {
		return new ArrayList<>(map.keySet());
	}

	@Override
	public String toString() {
		StringBuilder source = new StringBuilder("{");

		List<String> chaves = new ArrayList<>(map.keySet());
		for (int i = 0; i < chaves.size(); i++) {
			String nome = chaves.get(i);
			Valor valor = map.get(chaves.get(i));
			TipoValor tipo = valor.getTipo();
			source.append(nome + ":" + tipo + ":" + valor.toString().replace(",", "\\,"));

			if (i + 1 < map.size()) {
				source.append(",");
			}
		}

		source.append("}");

		return source.toString();
	}

	public static Registro fromString(String string, TipoBanco tipoBanco) throws ParseException {
		String s = string.trim();

		Registro registro = new Registro(tipoBanco);

		StringBuilder bufferCampo = new StringBuilder();

		String previousC = "";
		for (int i = 0; i < string.length(); i++) {
			String c = s.substring(i, i + 1);

			if (i == 0) {
				if (!c.equals("{")) {
					throw new ParseException("Registro inválido", 0);
				}
				continue;
			}

			if (c.equals(",") && !previousC.equals("\\")) {
				gravarCampoSerializadoEmRegistro(registro, bufferCampo.toString());
				bufferCampo.setLength(0);
				continue;
			}

			if (i == s.length() - 1) {
				if (!c.equals("}")) {
					throw new ParseException("Registro inválido", 0);
				}

				gravarCampoSerializadoEmRegistro(registro, bufferCampo.toString());
				bufferCampo.setLength(0);
				break;
			}

			bufferCampo.append(c);
			previousC = c;
		}

		return registro;
	}

	private static void gravarCampoSerializadoEmRegistro(Registro registro, String campo) {
		int i = 0;

		StringBuilder nameBuffer = new StringBuilder();
		for (; i < campo.length(); i++) {
			String c = campo.substring(i, i + 1);
			if (c.equals(":")) {
				i++;
				break;
			}
			nameBuffer.append(c);
		}

		StringBuilder tipoBuffer = new StringBuilder();
		for (; i < campo.length(); i++) {
			String c = campo.substring(i, i + 1);
			if (c.equals(":")) {
				i++;
				break;
			}
			tipoBuffer.append(c);
		}

		StringBuilder valorBuffer = new StringBuilder();
		for (; i < campo.length(); i++) {
			String c = campo.substring(i, i + 1);
			valorBuffer.append(c);
		}

		TipoValor tipo = TipoValor.fromString(tipoBuffer.toString().trim());

		Valor valor;

		String valorString = valorBuffer.toString().replace("\\,", ",");

		switch (tipo) {
		case DOUBLE:
			valor = new ValorDouble(Double.parseDouble(valorString.trim()));
			break;
		case FLOAT:
			valor = new ValorFloat(Float.parseFloat(valorString.trim()));
			break;
		case INT:
			valor = new ValorInt(Integer.parseInt(valorString.trim()));
			break;
		case LONG:
			valor = new ValorLong(Long.parseLong(valorString.trim()));
			break;
		case STRING:
			valor = new ValorString(valorString);
			break;
		default:
			return;
		}

		registro.set(nameBuffer.toString().trim(), valor);
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
