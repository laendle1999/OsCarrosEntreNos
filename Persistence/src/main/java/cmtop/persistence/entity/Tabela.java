package cmtop.persistence.entity;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSetMetaData;

import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.ValorDouble;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class Tabela {

	private String nome;

	private Banco banco;

	public Tabela(String nome, Banco banco) {
		this.nome = nome;
		this.banco = banco;
	}

	public void inserir(Registro registro) throws IOException {
		String sql = "INSERT INTO " + getNome() + " ";

		sql += "(" + registro.getSQLKeys() + ") ";

		sql += "VALUES (" + registro.getSQLValues() + ")";

		try {
			Statement statement = banco.getConnection().createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public void atualizar(Condicao condicao, Registro registro) throws IOException {
		String sql = "UPDATE " + getNome() + " ";
		sql += "SET " + registro.getSQLSET() + " ";
		sql += "WHERE " + condicao.toSQL();

		try {
			Statement statement = banco.getConnection().createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public void remover(Condicao condicao) throws IOException {
		String sql = "DELETE FROM " + getNome() + " WHERE " + condicao.toSQL();

		try {
			Statement statement = banco.getConnection().createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	public List<Registro> buscar(Condicao condicao, int limite) throws IOException {
		String sql = "SELECT * FROM " + getNome() + " WHERE " + condicao.toSQL() + " LIMIT " + limite;

		List<Registro> resultados = new ArrayList<>();

		try {
			Statement statement = banco.getConnection().createStatement();

			if (!statement.execute(sql)) {
				return resultados;
			}

			ResultSet resultSet = statement.getResultSet();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				Registro registro = new Registro();

				for (int i = 1; i <= columnCount; i++) {
					String colName = metaData.getColumnName(i);

					switch (metaData.getColumnType(i)) {
					case Types.INTEGER:
					case Types.TINYINT:
						registro.set(colName, new ValorInt(resultSet.getInt(i)));
						break;
					case Types.DOUBLE:
						registro.set(colName, new ValorDouble(resultSet.getDouble(i)));
						break;
					case Types.FLOAT:
					case Types.DECIMAL:
						registro.set(colName, new ValorFloat(resultSet.getFloat(i)));
						break;
					case Types.VARCHAR:
					case Types.LONGVARCHAR:
					case Types.DATE:
						registro.set(colName, new ValorString(resultSet.getString(i)));
						break;
					default:
						// System.err.println("Field not identified on table \"" + getNome() + "\": " +
						// colName + "; converting to string");
						registro.set(colName, new ValorString(resultSet.getString(i)));
						continue;
					}
				}

				resultados.add(registro);
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}

		return resultados;
	}

	public String getNome() {
		return nome;
	}

}
