package cmtop.persistence.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class ServidorRedeLocal {

	private static boolean running = false;

	private static boolean aguardandoConsulta = false;

	private static boolean aguardandoConsultaComResposta = false;

	public static int PORTA_PADRAO = 6370;

	private Banco banco;

	public static class SessaoCliente {
		private boolean autenticado = false;

		private String resultado;

		private OuvidorDeClienteRedeLocal ouvidorCliente;

		private Banco banco;

		public SessaoCliente(Banco banco, Socket socket, int timeoutSegundos) throws IOException {
			this.banco = banco;
			ouvidorCliente = new OuvidorDeClienteRedeLocal(socket, mensagem -> {
				if (!autenticado) {
					if (mensagem.equals(ComandosRede.COMANDO_CONECTAR)) {
						autenticado = true;
						return ComandosRede.OK;
					}
					return ComandosRede.NAO_AUTENTICADO;
				} else {
					if (aguardandoConsulta) {
						aguardandoConsulta = false;

						String sql = mensagem;
						return processarConsulta(sql, timeoutSegundos);
					} else if (aguardandoConsultaComResposta) {
						aguardandoConsultaComResposta = false;

						String sql = mensagem;
						return processarConsultaComResposta(sql, timeoutSegundos);
					}

					if (mensagem.equals(ComandosRede.COMANDO_CONECTAR)) {
						return ComandosRede.OK;
					} else if (mensagem.equals(ComandosRede.COMANDO_DESCONECTAR)) {
						autenticado = false;
						return ComandosRede.OK;
					} else if (mensagem.equals(ComandosRede.CONSULTA_SQL)) {
						aguardandoConsulta = true;
						return ComandosRede.OK;
					} else if (mensagem.equals(ComandosRede.CONSULTA_SQL_COM_RESPOSTA)) {
						aguardandoConsultaComResposta = true;
						return ComandosRede.OK;
					}

					return ComandosRede.COMANDO_DESCONHECIDO;
				}
			});
		}

		private String processarConsultaComResposta(String sql, int timeoutSegundos) {
			resultado = ComandosRede.ERRO;

			CountDownLatch latch = new CountDownLatch(1);
			banco.consultaComResultado(sql, new ListenerConsultaComResposta<Registro>() {

				@Override
				public void resposta(List<Registro> registros) {
					resultado = ComandosRede.OK + "\n";
					resultado += Registro.listaRegistrosToString(registros);
					latch.countDown();
				}

				@Override
				public void erro(Exception e) {
					resultado = ComandosRede.ERRO;
					e.printStackTrace();
					latch.countDown();
				}
			});

			try {
				latch.await(timeoutSegundos, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
			}

			return resultado;
		}

		private String processarConsulta(String sql, int timeoutSegundos) {
			resultado = ComandosRede.ERRO;

			CountDownLatch latch = new CountDownLatch(1);
			banco.executarConsulta(sql, new ListenerConsulta() {
				@Override
				public void sucesso(int resultadosAfetados) {
					resultado = ComandosRede.OK + "\n";
					resultado += resultadosAfetados;
					latch.countDown();
				}

				@Override
				public void erro(Exception e) {
					resultado = ComandosRede.ERRO;
					e.printStackTrace();
					latch.countDown();
				}
			});

			try {
				latch.await(timeoutSegundos, TimeUnit.SECONDS);
			} catch (InterruptedException e1) {
			}

			return resultado;
		}

		public void fecharSessao() {
			ouvidorCliente.interromper();
		}
	}

	public ServidorRedeLocal(Banco banco) {
		this.banco = banco;
	}

	public static List<SessaoCliente> SESSOES = new ArrayList<>();

	private static CountDownLatch clientWaitLatch = new CountDownLatch(1);

	public void iniciar(int timeoutSegundos) {
		running = true;

		new MyThread(() -> {
			try (ServerSocket serverSocket = new ServerSocket(ServidorRedeLocal.PORTA_PADRAO)) {
				serverSocket.setSoTimeout(timeoutSegundos * 1000);
				while (running) {
					clientWaitLatch = new CountDownLatch(1);

					new MyThread(() -> {
						try {
							Socket socket = serverSocket.accept();
							socket.setSoTimeout(timeoutSegundos * 1000);
							clientWaitLatch.countDown();

							SESSOES.add(new SessaoCliente(banco, socket, timeoutSegundos));
						} catch (IOException e) {
							clientWaitLatch.countDown();
						}
					}, "ServidorRedeLocal iniciar AguardandoCliente").start();

					try {
						clientWaitLatch.await(timeoutSegundos * 3, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, "ServidorRedeLocal iniciar").start();
	}

	public static void fecharConexoes() {
		running = false;
		for (SessaoCliente sessao : SESSOES) {
			sessao.fecharSessao();
		}
		clientWaitLatch.countDown();
	}

}
