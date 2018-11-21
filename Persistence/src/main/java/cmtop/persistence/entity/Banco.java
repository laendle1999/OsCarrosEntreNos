package cmtop.persistence.entity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
	private static final String DEFAULT_DATABASE_NAME = "auto_manager";

	private String url = "jdbc:mysql://localhost/" + DEFAULT_DATABASE_NAME;
	private String user = "root";
	private String password = "";

	private Connection connection;

	public Banco(String host, String database, String user, String password) {
		this.url = "jdbc:mysql://" + host + "/" + database;
		this.user = user;
		this.password = password;
	}

	public Banco(String host) {
		this.url = "jdbc:mysql://" + host + "/" + DEFAULT_DATABASE_NAME;
	}

	public Connection getConnection() throws IOException {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}

		return connection;
	}

	public void closeConnection() {
		if (connection != null) {
			connection = null;
		}
	}

	public Tabela getTabela(String nome) {
		return new Tabela(nome, this);
	}

}
