package cmtop.persistence.entity;

import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BancoClienteRedeLocal extends Banco {

	@SuppressWarnings("unused")
	private String url;

	public BancoClienteRedeLocal(String host, String user, String password, TipoBanco tipoBanco) {
		super(tipoBanco);
		if (tipoBanco == TipoBanco.DERBY) {
			this.url = "jdbc:derby://" + host + ":1527/" + Banco.DEFAULT_DATABASE_NAME + ";create=true";
		} else {
			throw new Error("Tipo de banco não suportado");
		}
	}

	@Override
	public Tabela getTabela(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executarConsulta(String sql, ListenerConsulta listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void consultaComResultado(String sql, ListenerConsultaComResposta<Registro> listener) {
		// TODO Auto-generated method stub

	}

}
