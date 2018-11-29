package cmtop.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

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

	public enum ConfiguracaoBanco {
		SERVIDOR_REDE_LOCAL, CLIENTE_REDE_LOCAL, REMOTO_NUVEM
	}

	private static Banco banco;

	public static void iniciarAplicacao() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Executar como servidor?", "",
				JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			configuracaoBanco = ConfiguracaoBanco.SERVIDOR_REDE_LOCAL;
		} else {
			configuracaoBanco = ConfiguracaoBanco.CLIENTE_REDE_LOCAL;
		}

		switch (configuracaoBanco) {
		case SERVIDOR_REDE_LOCAL:
			try {
				banco = new BancoServidorRedeLocal(TipoBanco.DERBY);
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possível iniciar banco servidor");
				PontoEntradaAplicacao.finalizarAplicacao();
				return;
			}
			break;
		case CLIENTE_REDE_LOCAL:
			CountDownLatch latch = new CountDownLatch(1);
			System.out.println("Procurando servidor...");
			procurarBancoServidorLocal(TipoBanco.DERBY,20, b -> {
				banco = b;
				latch.countDown();
			});
			try {
				latch.await(20, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
			}
			if (banco == null) {
				System.out.println("Servidor não encontrado");
				JOptionPane.showMessageDialog(null, "Servidor não encontrado na rede local");
				PontoEntradaAplicacao.finalizarAplicacao();
				return;
			}
			break;
		case REMOTO_NUVEM:
			banco = new BancoRemoto(TipoBanco.AZURE);
			break;
		default:
			PontoEntradaAplicacao.finalizarAplicacao();
			return;
		}

		new TelaLogin(banco).show();

		// float x = 3000;
		// new CadastrarPagamento(banco,x).show();
		// new GerarRelatorio().show();
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

			private synchronized void interromperBusca() {
				buscaInterrompida = true;
			}

			private synchronized void servidorEncontrado(Banco banco) {
				listener.accept(banco);
			}

			@Override
			public void run() {
				List<String> listaIps = new ArrayList<>();

				CountDownLatch latch = new CountDownLatch(1);
				NetworkUtil.getNetworkIPs(timeoutSegundos, list -> {
					listaIps.addAll(list);
					latch.countDown();
				});
				try {
					latch.await(timeoutSegundos, TimeUnit.SECONDS);
				} catch (InterruptedException e1) {
				}

				computadoresFaltantes = listaIps.size();

				for (int i = 0; i < listaIps.size(); i++) {
					try {
						Banco banco = new BancoClienteRedeLocal(listaIps.get(i), "", "", tipoBanco, timeoutSegundos);
						interromperBusca();
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

	public static void finalizarAplicacao() {
		ServidorRedeLocal.fecharConexoes();
		PortaVozCliente.fecharConexoes();
		Platform.exit();
	}

}
