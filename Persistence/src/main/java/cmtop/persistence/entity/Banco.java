package cmtop.persistence.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cmtop.persistence.service.ScriptRunner;

public class Banco {
	public enum TipoConexao {
		SERVIDOR, CLIENTE;
	}

	private static final String SQL_CREATE_SCRIPT_LOCATION = "../dbcarrosderby.sql";

	public static final String DEFAULT_DATABASE_NAME = "auto_manager";

	private String url;

	private Connection connection;

	private String user;

	private String password;

	public Banco(TipoConexao tipoConexao) {
		this("", "", tipoConexao);
	}

	public Banco(String user, String password, TipoConexao tipoConexao) {
		this.user = user;
		this.password = password;
		this.url = "jdbc:derby:" + DEFAULT_DATABASE_NAME + ";create=true";
	}

	public Connection getConnection() throws IOException {
		boolean shouldCreateDatabase = false;
		if (Files.notExists(Paths.get(DEFAULT_DATABASE_NAME))) {
			shouldCreateDatabase = true;
		}

		if (connection == null) {
			try {
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}

		if (shouldCreateDatabase) {
			createDatabase(connection);
		}

		return connection;
	}

	private void createDatabase(Connection connection) throws IOException {
		File file = new File(SQL_CREATE_SCRIPT_LOCATION);
		try {
			ScriptRunner scriptRunner = new ScriptRunner(connection, true, true);
			scriptRunner.runScript(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		} catch (SQLException e) {
			throw new IOException(e);
		}
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
