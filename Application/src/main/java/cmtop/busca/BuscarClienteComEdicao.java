package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.ClienteModel;
import cmtop.application.model.ModelGenerico;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import cmtop.domain.entity.Cliente;
import cmtop.domain.repository.ClienteRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscarClienteComEdicao extends BuscaComEdicao<Cliente> {

	private Banco banco;

	private static Campo PLACA = new Campo(TipoCampo.TEXTO, "Placa", "");

	@SuppressWarnings("unused")
	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "");

	public BuscarClienteComEdicao(Banco banco, ListenerAlteracoes<Cliente> listenerAlteracoes,
			Consumer<Cliente> listenerApagar) {
		super("Cliente", "Selecionar Cliente", listenerAlteracoes, listenerApagar);

		this.banco = banco;
		this.definirCamposBusca(PLACA);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Cliente>> callbackListaOriginal)
			throws IOException {
		ClienteRepository clienteRepository = new ClienteRepository(banco);
		clienteRepository.consultarClientesPorNome(camposBusca.get(PLACA.getNome()), limite,
				new ListenerConsultaComResposta<Cliente>() {

					@Override
					public void erro(Exception e) {
					}

					@Override
					public void resposta(List<Cliente> resultados) {
						callbackListaOriginal.accept(resultados);

						List<ClienteModel> lista = new ArrayList<>();
						resultados.forEach(resultado -> lista.add(new ClienteModel(resultado)));
						callbackListaModel.accept(lista);
					}
				});
	}

}
