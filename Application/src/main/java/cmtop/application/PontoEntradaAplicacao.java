package cmtop.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import cmtop.application.service.ConfiguracaoService;
import cmtop.application.service.NetworkUtil;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.BancoClienteRedeLocal;
import cmtop.persistence.entity.BancoRemoto;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.service.MyThread;
import cmtop.persistence.service.PortaVozCliente;
import cmtop.persistence.service.ServidorRedeLocal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	private static ConfiguracaoBanco configuracaoBanco;

	private final static int TEMPO_LIMITE_BANCO_SEGUNDOS = 10;

	public enum ConfiguracaoBanco {
		SERVIDOR_REDE_LOCAL, CLIENTE_REDE_LOCAL, REMOTO_NUVEM
	}

	private static Banco banco;

	public static void iniciarAplicacao() {
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.show();

		new MyThread(() -> {
			configuracaoBanco = obterConfiguracaoBanco();

			switch (configuracaoBanco) {
			case SERVIDOR_REDE_LOCAL:
				try {
					Platform.runLater(() -> {
						splashScreen.mostrarStatus("Iniciando banco servidor local...");
					});
					System.out.println("Ip deste computador na rede: " + getIpAddress());
					banco = new BancoServidorRedeLocal(TipoBanco.DERBY, TEMPO_LIMITE_BANCO_SEGUNDOS);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Não foi possível iniciar banco servidor");
					Platform.runLater(() -> {
						splashScreen.close();
					});
					PontoEntradaAplicacao.finalizarAplicacao();
					return;
				}
				break;
			case CLIENTE_REDE_LOCAL:
				CountDownLatch latch = new CountDownLatch(1);
				Platform.runLater(() -> {
					splashScreen.mostrarStatus("Procurando servidor...");
				});
				procurarBancoServidorLocal(TipoBanco.DERBY, TEMPO_LIMITE_BANCO_SEGUNDOS, b -> {
					banco = b;
					latch.countDown();
				});
				try {
					latch.await();
				} catch (InterruptedException e) {
				}
				if (banco == null) {
					System.out.println("Servidor não encontrado");
					JOptionPane.showMessageDialog(null, "Servidor não encontrado na rede local");
					Platform.runLater(() -> {
						splashScreen.close();
					});
					PontoEntradaAplicacao.finalizarAplicacao();
					return;
				}
				break;
			case REMOTO_NUVEM:
				banco = new BancoRemoto(TipoBanco.AZURE);
				break;
			default:
				Platform.runLater(() -> {
					splashScreen.close();
				});
				PontoEntradaAplicacao.finalizarAplicacao();
				return;
			}

			Platform.runLater(() -> {
				splashScreen.close();
				new TelaLogin(banco).show();
			});
		}, "PontoEntradaAplicacao iniciarAplicacao").start();
	}

	private static void procurarBancoServidorLocal(TipoBanco tipoBanco, int timeoutSegundos, Consumer<Banco> listener) {
		new MyThread(new Runnable() {

			private boolean buscaInterrompida;

			private int computadoresFaltantes;

			private synchronized void decrementarComputadoresFaltantes() {
				if (buscaInterrompida) {
					return;
				}

				computadoresFaltantes--;
				if (computadoresFaltantes == 0) {
					listener.accept(null);
				}
			}

			private synchronized void servidorEncontrado(Banco banco) {
				if (buscaInterrompida)
					return;
				buscaInterrompida = true;
				listener.accept(banco);
			}

			@Override
			public void run() {
				List<String> listaIps = new ArrayList<>();
				listaIps = NetworkUtil.getNetworkIPs(timeoutSegundos);

				System.out.println("Computadores na rede: \n" + listaIps);

				computadoresFaltantes = listaIps.size();

				for (int i = 0; i < listaIps.size(); i++) {
					try {
						Banco banco = new BancoClienteRedeLocal(listaIps.get(i), "", "", tipoBanco, timeoutSegundos);
						servidorEncontrado(banco);
						System.out.println("Servidor encontrado: " + listaIps.get(i));
					} catch (IOException e) {
						decrementarComputadoresFaltantes();
						continue;
					}
				}
			}
		}, "procurarBancoServidorLocal").start();
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

	public static void finalizarAplicacao() {
		ServidorRedeLocal.fecharConexoes();
		PortaVozCliente.fecharConexoes();
		Platform.exit();
	}

	public static ConfiguracaoBanco getConfiguracaoBanco() {
		return configuracaoBanco;
	}

	public static ConfiguracaoBanco obterConfiguracaoBanco() {
		ConfiguracaoBanco configuracao = null;
		try {
			configuracao = ConfiguracaoService.obterConfiguracaoBanco();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (configuracao == null) {
			int dialogResult = JOptionPane.showConfirmDialog(null, "Executar como servidor?", "",
					JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				configuracao = ConfiguracaoBanco.SERVIDOR_REDE_LOCAL;
			} else {
				configuracao = ConfiguracaoBanco.CLIENTE_REDE_LOCAL;
			}
			try {
				ConfiguracaoService.gravarConfiguracaoBanco(configuracao);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return configuracao;
	}

}
