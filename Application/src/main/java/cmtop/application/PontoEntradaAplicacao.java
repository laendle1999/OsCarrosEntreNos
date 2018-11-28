package cmtop.application;

import cmtop.application.service.NetworkUtil;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.BancoClienteRedeLocal;
import cmtop.persistence.entity.BancoRemoto;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import javafx.application.Application;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	public enum ConfiguracaoBanco {
		SERVIDOR_REDE_LOCAL, CLIENTE_REDE_LOCAL, REMOTO_NUVEM
	}

	public static void iniciarAplicacao() {
		ConfiguracaoBanco configuracaoBanco;
		configuracaoBanco = ConfiguracaoBanco.SERVIDOR_REDE_LOCAL;
		// configuracaoBanco = ConfiguracaoBanco.CLIENTE_REDE_LOCAL;

		Banco banco;

		switch (configuracaoBanco) {
		case SERVIDOR_REDE_LOCAL:
			banco = new BancoServidorRedeLocal(TipoBanco.DERBY);
			System.out.println(getIpAddress());
			break;
		case CLIENTE_REDE_LOCAL:
			String ipComputadorNaRede = "192.168.0.103"; // O IP local altera constantemente
			banco = new BancoClienteRedeLocal(ipComputadorNaRede, "", "", TipoBanco.DERBY);
			break;
		case REMOTO_NUVEM:
			banco = new BancoRemoto(TipoBanco.AZURE);
			break;
		default:
			return;
		}

		new TelaLogin(banco).show();

	}

	private static String getIpAddress() {
		return NetworkUtil.getCurrentEnvironmentNetworkIp();
	}

	@Override
	public void start(Stage _stage) throws Exception {
		iniciarAplicacao();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
