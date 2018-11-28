package cmtop.persistence.entity;

import java.io.IOException;
import java.text.ParseException;

import cmtop.persistence.service.ComandosRede;
import cmtop.persistence.service.PortaVozCliente;
import cmtop.persistence.service.ServidorRedeLocal;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BancoClienteRedeLocal extends Banco {

	private PortaVozCliente portaVozCliente;
	private String host;
	private TipoBanco tipoBanco;

	public BancoClienteRedeLocal(String host, String user, String password, TipoBanco tipoBanco) {
		super(tipoBanco);
		this.host = host;
		this.tipoBanco = tipoBanco;

		if (tipoBanco != TipoBanco.DERBY) {
			throw new Error("Tipo de banco não suportado");
		}
	}

	private PortaVozCliente getPortaVozCliente() throws IOException {
		if (portaVozCliente == null) {
			portaVozCliente = new PortaVozCliente(host, ServidorRedeLocal.PORTA_PADRAO);
			portaVozCliente.enviarMensagem(ComandosRede.COMANDO_CONECTAR);
			String resposta = portaVozCliente.aguardarMensagem().trim();
			if (resposta.trim().isEmpty() || !resposta.equals(ComandosRede.OK)) {
				portaVozCliente = null;
				throw new IOException("Conexão não aceita pelo servidor");
			}
		}

		return portaVozCliente;
	}

	@Override
	public Tabela getTabela(String nome) {
		return new Tabela(nome, this);
	}

	@Override
	public void executarConsulta(String sql, ListenerConsulta listener) {
		new Thread(() -> {
			try {
				getPortaVozCliente().enviarMensagem(sql);
				String resposta = getPortaVozCliente().aguardarMensagem();
				if (resposta.trim().isEmpty() || !resposta.equals(ComandosRede.OK)) {
					listener.erro(new IOException("Comando não foi executado no servidor"));
				} else {
					listener.sucesso();
				}
			} catch (IOException e) {
				listener.erro(e);
			}
		}).start();
	}

	@Override
	public void consultaComResultado(String sql, ListenerConsultaComResposta<Registro> listener) {
		new Thread(() -> {
			try {
				getPortaVozCliente().enviarMensagem(ComandosRede.CONSULTA_SQL_COM_RESPOSTA);
				String status = getPortaVozCliente().aguardarMensagem();

				if (!status.equals(ComandosRede.OK)) {
					listener.erro(new IOException("Não foi possível iniciar consulta no servidor"));
					return;
				}

				getPortaVozCliente().enviarMensagem(sql);
				status = getPortaVozCliente().aguardarMensagem();
				
				if (!status.equals(ComandosRede.OK)) {
					listener.erro(new IOException("A consulta foi executada no servidor mas falhou"));
					return;
				}
				
				String resultado = getPortaVozCliente().aguardarMensagem();
				listener.resposta(Registro.listaRegistrosFromString(resultado, tipoBanco));
			} catch (IOException | ParseException e) {
				listener.erro(e);
			}
		}).start();
	}

}
