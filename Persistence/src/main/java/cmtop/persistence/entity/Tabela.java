package cmtop.persistence.entity;

import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class Tabela {

	private String nome;

	private Banco banco;

	public Tabela(String nome, Banco banco) {
		this.nome = nome;
		this.banco = banco;
	}

	public void inserir(Registro registro, ListenerConsulta listener) {
		String sql = "INSERT INTO " + getNome() + " ";

		sql += "(" + registro.getSQLKeys() + ") ";

		sql += "VALUES (" + registro.getSQLValues() + ")";

		banco.executarConsulta(sql, listener);
	}

	public void atualizar(Condicao condicao, Registro registro, ListenerConsulta listener) {
		String sql = "UPDATE " + getNome() + " ";
		sql += "SET " + registro.getSQLSET() + " ";
		sql += "WHERE " + condicao.toSQL();

		banco.executarConsulta(sql, listener);
	}

	public void remover(Condicao condicao, ListenerConsulta listener) {
		String sql = "DELETE FROM " + getNome() + " WHERE " + condicao.toSQL();

		banco.executarConsulta(sql, listener);
	}

	public void buscar(Condicao condicao, int limite,
			ListenerConsultaComResposta<Registro> listenerConsultaComResposta) {
		String sql = "SELECT * FROM " + getNome();
		if (!condicao.isEmpty()) {
			sql += " WHERE " + condicao.toSQL();
		}

		if (banco.getTipoConexao() == TipoBanco.DERBY) {
			sql += " fetch first " + limite + " rows only";
		} else {
			sql += " LIMIT " + limite;
		}

		banco.consultaComResultado(sql, listenerConsultaComResposta);
	}

	public String getNome() {
		return nome;
	}

}
