package cmtop.persistence.service;

import java.io.IOException;

public class ServidorRedeLocal {

	public void iniciar(int porta) {
		new Thread(() -> {
			while (true) {
				OuvidorClienteRedeLocal ouvidor = new OuvidorClienteRedeLocal(porta);

				try {
					ouvidor.aguardarEOuvirNovoCliente(mensagem -> {
						return mensagem;
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

}
