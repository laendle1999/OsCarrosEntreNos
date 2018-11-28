package cmtop.persistence.entity;

import java.security.InvalidParameterException;

import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BancoRemoto extends Banco {

	@SuppressWarnings("unused")
	private String url;

	public BancoRemoto(TipoBanco tipoBanco) {
		super(tipoBanco);
		if (tipoBanco == TipoBanco.AZURE) {
			this.url = "jdbc:sqlserver://ft-projetos-server.database.windows.net:1433;database=auto_manager;user=laendle1999@ft-projetos-server;password=rumGa5Pp;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		} else
			throw new InvalidParameterException("Tipo de banco remoto não suportado");
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
