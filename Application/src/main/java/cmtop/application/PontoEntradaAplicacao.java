package cmtop.application;

import cmtop.application.service.NetworkUtil;
import cmtop.busca.BuscaCliente;
import cmtop.busca.BuscarVendas;
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
		ConfiguracaoBanco configuracaoBanco = ConfiguracaoBanco.SERVIDOR_REDE_LOCAL;

		Banco banco;

		switch (configuracaoBanco) {
		case SERVIDOR_REDE_LOCAL:
			banco = new BancoServidorRedeLocal(TipoBanco.DERBY);
			System.out.println(getIpAddress());
			break;
		case CLIENTE_REDE_LOCAL:
			banco = new BancoClienteRedeLocal("", "", "", TipoBanco.DERBY);
			break;
		case REMOTO_NUVEM:
			banco = new BancoRemoto(TipoBanco.AZURE);
			break;
		default:
			return;
		}

		// new MenuPrincipal().show();
		// new TelaDeVenda().show();
		// new BuscaCarro(banco, carro -> {
		// if (carro == null) {
		// System.out.println("Nenhum carro escolhido");
		// } else {
		// System.out.println("Carro escolhido (marca): ");
		// System.out.println(carro.getMarca());
		// }
		// }).show();

		// new BuscaCarroComEdicao(banco, (carroAlterado, nomeCampo, valor) -> {
		// System.out.println(nomeCampo);
		// System.out.println(carroAlterado.getPlaca());
		// return true;
		// }, carro -> System.out.println("Carro apagado: " +
		// carro.getModelo())).show();

		//new TelaLogin(banco).show();
		new CadastrarVendedor().show();
		
		
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
