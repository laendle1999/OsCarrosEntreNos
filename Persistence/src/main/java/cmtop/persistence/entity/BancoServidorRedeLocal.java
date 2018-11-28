package cmtop.persistence.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import cmtop.persistence.service.ScriptRunner;
import cmtop.persistence.service.ServidorRedeLocal;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import cmtop.persistence.valueobject.ValorDouble;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class BancoServidorRedeLocal extends Banco {

	private static final String DERBY_SQL_CREATE_SCRIPT_LOCATION = "../dbcarrosderby.sql";

	private String url;

	private Connection connection;

	private String user;

	private String password;

	private TipoBanco tipoBanco;

	public static int PORTA_PADRAO = 8910;

	public BancoServidorRedeLocal(TipoBanco tipoConexao) {
		this("root", "", tipoConexao);
	}

	public BancoServidorRedeLocal(String user, String password, TipoBanco tipoBanco) {
		super(tipoBanco);
		this.user = user;
		this.password = password;
		this.tipoBanco = tipoBanco;

		if (tipoBanco == TipoBanco.DERBY) {
			this.url = "jdbc:derby:" + DEFAULT_DATABASE_NAME + ";create=true";
		} else if (tipoBanco == TipoBanco.MYSQL) {
			this.url = "jdbc:mysql://localhost/" + DEFAULT_DATABASE_NAME;
		} else {
			throw new InvalidParameterException("Tipo de banco não aceito");
		}
	}

	public TipoBanco getTipoConexao() {
		return tipoBanco;
	}

	private Connection getConnection() throws IOException {
		boolean shouldCreateDatabase = false;
		if (Files.notExists(Paths.get(DEFAULT_DATABASE_NAME))) {
			shouldCreateDatabase = true;
		}

		if (connection == null) {
			try {
				if (tipoBanco == TipoBanco.DERBY) {
					connection = DriverManager.getConnection(url);
				} else if (tipoBanco == TipoBanco.MYSQL) {
					connection = DriverManager.getConnection(url, user, password);
				}
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}

		if (shouldCreateDatabase) {
			createDatabase(connection);
		}

		iniciarServidorRedeLocal();

		return connection;
	}

	private void iniciarServidorRedeLocal() {
		ServidorRedeLocal servidorRedeLocal = new ServidorRedeLocal();
		servidorRedeLocal.iniciar(PORTA_PADRAO);
	}

	private void createDatabase(Connection connection) throws IOException {
		File file = new File(DERBY_SQL_CREATE_SCRIPT_LOCATION);
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

	@Override
	public Tabela getTabela(String nome) throws IOException {
		return new Tabela(nome, this);
	}

	public void executarConsulta(String sql, ListenerConsulta listener) {
		new Thread(() -> {
			try {
				Statement statement = getConnection().createStatement();
				statement.execute(sql);
				listener.sucesso();
			} catch (SQLException | IOException e) {
				listener.erro(e);
			}
		}).run();
	}

	public void consultaComResultado(String sql, ListenerConsultaComResposta<Registro> listener) {
		new Thread(() -> {
			List<Registro> resultados = new ArrayList<>();

			try {
				Statement statement = getConnection().createStatement();

				if (!statement.execute(sql)) {
					listener.resposta(resultados);
				}

				ResultSet resultSet = statement.getResultSet();

				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();

				while (resultSet.next()) {
					Registro registro = new Registro(getTipoConexao());

					for (int i = 1; i <= columnCount; i++) {
						String colName = metaData.getColumnName(i);

						switch (metaData.getColumnType(i)) {
						case Types.INTEGER:
						case Types.TINYINT:
							registro.set(colName, new ValorInt(resultSet.getInt(i)));
							break;
						case Types.BIGINT:
							registro.set(colName, new ValorLong(resultSet.getLong(i)));
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
							System.err.println(
									"Field not identified on register: " + colName + "; converting it to string");
							registro.set(colName, new ValorString(resultSet.getString(i)));
							continue;
						}
					}

					resultados.add(registro);
				}
			} catch (SQLException | IOException e) {
				listener.erro(e);
				return;
			}

			listener.resposta(resultados);
		}).run();
	}

}
