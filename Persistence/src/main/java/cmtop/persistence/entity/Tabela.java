package cmtop.persistence.entity;

import java.util.List;

import cmtop.persistence.valueobject.Condicao;

public class Tabela {

	private String nome;

	public Tabela(String nome) {
		this.nome = nome;
	}

	public void inserir(Registro registro) {
		String sql = "INSERT INTO " + getNome() + " ";

		sql += "(" + registro.getSQLKeys() + ") ";

		sql += "VALUES (" + registro.getSQLValues() + ")";

		// TODO executar consulta
		System.out.println(sql);
	}

	public void atualizar(Condicao condicao, Registro registro) {
		String sql = "UPDATE " + getNome() + " ";
		sql += "SET " + registro.getSQLSET() + " ";
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
