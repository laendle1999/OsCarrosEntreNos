package cmtop.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import cmtop.application.service.NetworkUtil;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.BancoClienteRedeLocal;
import cmtop.persistence.entity.BancoRemoto;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import javafx.application.Application;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	private static ConfiguracaoBanco configuracaoBanco;

	public enum ConfiguracaoBanco {
		SERVIDOR_REDE_LOCAL, CLIENTE_REDE_LOCAL, REMOTO_NUVEM
	}

	public static void iniciarAplicacao() {

		int dialogResult = JOptionPane.showConfirmDialog(null, "Executar como servidor?", "",
				JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			configuracaoBanco = ConfiguracaoBanco.SERVIDOR_REDE_LOCAL;
		} else {
			configuracaoBanco = ConfiguracaoBanco.CLIENTE_REDE_LOCAL;
		}

		Banco banco;

		switch (configuracaoBanco) {
		case SERVIDOR_REDE_LOCAL:
			banco = new BancoServidorRedeLocal(TipoBanco.DERBY);
			break;
		case CLIENTE_REDE_LOCAL:
			try {
				banco = procurarBancoServidorLocal(TipoBanco.DERBY, 5);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Servidor não encontrado na rede local");
				e.printStackTrace();
				return;
			}
			break;
		case REMOTO_NUVEM:
			banco = new BancoRemoto(TipoBanco.AZURE);
			break;
		default:
			return;
		}

		new TelaLogin(banco).show();

		// float x = 3000;
		// new CadastrarPagamento(banco,x).show();
		// new GerarRelatorio().show();
	}

	private static Banco procurarBancoServidorLocal(TipoBanco tipoBanco, int timeoutSegundos) throws Exception {
		List<String> listaIps = new ArrayList<>();

		CountDownLatch latch = new CountDownLatch(1);

		System.out.println("Procurando servidor...");
		NetworkUtil.getNetworkIPs(list -> {
			listaIps.addAll(list);
		});

		latch.await(timeoutSegundos, TimeUnit.SECONDS);

		Banco banco = null;

		for (int i = 0; i < listaIps.size(); i++) {
			try {
				banco = new BancoClienteRedeLocal(listaIps.get(i), "", "", tipoBanco, timeoutSegundos);
				System.out.println("Servidor encontrado: " + listaIps.get(i));
			} catch (IOException e) {
				continue;
			}
		}

		if (banco == null) {
			throw new Exception("Servidor não encontrado na rede local");
		}

		return banco;
	}

	@SuppressWarnings("unused")
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
