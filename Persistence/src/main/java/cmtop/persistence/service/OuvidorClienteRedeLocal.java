package cmtop.persistence.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class OuvidorClienteRedeLocal {

	private boolean executando = false;
	private int porta;

	public static interface MensagemListener {
		String onMensagemRecebida(String mensagem);
	}
	
	public OuvidorClienteRedeLocal(int porta) {
		this.porta = porta;
	}

	public void aguardarEOuvirNovoCliente(MensagemListener mensagemClienteListener) throws IOException {
		executando = true;

		try (ServerSocket serverSocket = new ServerSocket(porta)) {
			Socket socket = serverSocket.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			new Thread(() -> {
				while (executando) {
					String message = "";
					try {
						message = in.readLine();
						String resposta = mensagemClienteListener.onMensagemRecebida(message);

						out.println(resposta);
					} catch (IOException e) {
						break;
					}
				}
			}).run();
		}
	}

	public void interromper() {
		executando = false;
	}

}
