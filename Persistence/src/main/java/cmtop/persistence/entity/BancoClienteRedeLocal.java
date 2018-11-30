package cmtop.persistence.entity;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cmtop.persistence.service.ComandosRede;
import cmtop.persistence.service.MyThread;
import cmtop.persistence.service.PortaVozCliente;
import cmtop.persistence.service.ServidorRedeLocal;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BancoClienteRedeLocal extends Banco {

	private PortaVozCliente portaVozCliente;
	private String host;
	private TipoBanco tipoBanco;
	private int timeoutSegundos;

	public BancoClienteRedeLocal(String host, String user, String password, TipoBanco tipoBanco, int timeoutSegundos)
			throws IOException {
		super(tipoBanco);
		this.host = host;
		this.tipoBanco = tipoBanco;
		this.timeoutSegundos = timeoutSegundos;

		if (tipoBanco != TipoBanco.DERBY) {
			throw new Error("Tipo de banco não suportado");
		}

		getPortaVozCliente();
	}

	private PortaVozCliente getPortaVozCliente() throws IOException {
		if (portaVozCliente == null) {
			portaVozCliente = new PortaVozCliente(host, ServidorRedeLocal.PORTA_PADRAO, timeoutSegundos);
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
		new MyThread(() -> {
			try {
				getPortaVozCliente().enviarMensagem(ComandosRede.CONSULTA_SQL);
				String status = getPortaVozCliente().aguardarMensagem();
				if (status.trim().isEmpty() || !status.equals(ComandosRede.OK)) {
					listener.erro(new IOException("Não foi possível iniciar consulta no servidor"));
					return;
				}

				getPortaVozCliente().enviarMensagem(sql);
				String resposta = getPortaVozCliente().aguardarMensagem();
				if (resposta.trim().isEmpty() || !resposta.equals(ComandosRede.OK)) {
					listener.erro(new IOException("Comando não foi executado no servidor, resposta: " + resposta));
					return;
				}

				String resultadosAfetados = getPortaVozCliente().aguardarMensagem();
				String chavesGeradasStr = getPortaVozCliente().aguardarMensagem();
				String[] chavesGeradas = chavesGeradasStr.split("\\,");
				List<Long> chaves = new ArrayList<>();
				for (int i = 0; i < chavesGeradas.length; i++) {
					chaves.add(Long.parseLong(chavesGeradas[i]));
				}
				listener.sucesso(Integer.parseInt(resultadosAfetados), chaves);
			} catch (IOException | NumberFormatException e) {
				listener.erro(e);
			}
		}, "BancoClienteRedeLocal executarConsulta").start();
	}

	@Override
	public void consultaComResultado(String sql, ListenerConsultaComResposta<Registro> listener) {
		new MyThread(() -> {
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
				e.printStackTrace();
				listener.erro(e);
			}
		}, "BancoClienteRedeLocal consultaComResultado").start();
	}

}
