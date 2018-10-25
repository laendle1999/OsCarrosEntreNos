package cmtop.persistence.entity;

import java.util.List;

import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoValor;
import cmtop.persistence.valueobject.Valor;

public class Tabela {

	private String nome;

	public Tabela(String nome) {
		this.nome = nome;
	}

	public void inserir(Registro registro) {
		String sql = "INSERT INTO " + getNome() + " ";

		List<String> chaves = registro.getChaves();

		sql += "(";
		for (int i = 0; i < chaves.size(); i++) {
			sql += chaves.get(i);

			if (i + 1 < chaves.size()) {
				sql += ", ";
			}
		}
		sql += ") ";

		sql += "VALUES (";
		for (int i = 0; i < chaves.size(); i++) {
			Valor valor = registro.get(chaves.get(i));

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
		sql += ")";

		// TODO executar consulta
		System.out.println(sql);
	}

	public void atualizar(Condicao condicao, Registro registro) {
		String sql = "UPDATE " + getNome() + " ";
		sql += "SET " + registro.toSQL() + " ";
		sql += "WHERE " + condicao.toSQL();

		// TODO executar consulta
		System.out.println(sql);
	}

	public void remover(Condicao condicao) {
		String sql = "DELETE FROM " + getNome() + " WHERE " + condicao.toSQL();

		// TODO executar consulta
		System.out.println(sql);
	}

	public List<Registro> buscar(Condicao condicao) {
		String sql = "SELECT * FROM " + getNome() + " WHERE " + condicao.toSQL();

		// TODO executar consulta
		System.out.println(sql);

		return null;
	}

	public String getNome() {
		return nome;
	}

}
