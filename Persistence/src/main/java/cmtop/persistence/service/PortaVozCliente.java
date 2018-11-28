package cmtop.persistence.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PortaVozCliente {

	private PrintWriter out;
	private BufferedReader in;
	private Socket socket;

	public PortaVozCliente(String host, int porta, int timeoutSegundos) throws IOException {
		socket = new Socket(host, porta);
		socket.setSoTimeout(timeoutSegundos * 1000);

		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void enviarMensagem(String mensagem) {
		out.println(mensagem);
	}

	public String aguardarMensagem() throws IOException {
		return in.readLine();
	}

	public void fecharConexao() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}

}
