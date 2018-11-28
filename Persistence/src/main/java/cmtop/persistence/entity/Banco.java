package cmtop.persistence.entity;

import java.io.IOException;

import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public abstract class Banco {

	public static final String DEFAULT_DATABASE_NAME = "auto_manager";
	private TipoBanco tipoConexao;

	public Banco(TipoBanco tipoConexao) {
		this.tipoConexao = tipoConexao;
	}

	public abstract Tabela getTabela(String nome) throws IOException;

	public TipoBanco getTipoConexao() {
		return tipoConexao;
	}

	public abstract void executarConsulta(String sql, ListenerConsulta listener);

	public abstract void consultaComResultado(String sql, ListenerConsultaComResposta<Registro> listener);

}
