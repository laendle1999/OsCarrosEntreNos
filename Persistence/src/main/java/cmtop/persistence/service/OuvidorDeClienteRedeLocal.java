package cmtop.persistence.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OuvidorDeClienteRedeLocal {

	private boolean executando = false;

	public static interface MensagemListener {
		String onMensagemRecebida(String mensagem);
	}

	public OuvidorDeClienteRedeLocal(Socket socket, MensagemListener mensagemClienteListener) throws IOException {
		executando = true;

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		new Thread(() -> {
			while (executando) {
				try {
					String message = in.readLine();
					if (message == null)
						continue;

					String resposta = mensagemClienteListener.onMensagemRecebida(message);
					out.println(resposta);
				} catch (IOException e) {
					break;
				}
			}
		}).start();
	}

	public void interromper() {
		executando = false;
	}

}
