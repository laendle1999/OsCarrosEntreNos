package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.ClienteModel;
import cmtop.application.model.ModelGenerico;
import cmtop.application.service.ComponentesServices;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import cmtop.domain.entity.Cliente;
import cmtop.domain.repository.ClienteRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscarClienteComEdicao extends BuscaComEdicao<Cliente> {

	private Banco banco;

	private static Campo PLACA = new Campo(TipoCampo.TEXTO, "Nome", "nome", "");

	@SuppressWarnings("unused")
	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "renavan", "");

	public BuscarClienteComEdicao(Banco banco) {
		super("Cliente", "Selecionar Cliente", new ListenerAlteracoes<Cliente>() {

			@Override
			public boolean aceitarMudanca(Cliente cliente, String campo, String valorNovo) {
				if (!(campo.equals("Nome") || campo.equals("Telefone")))
					return false;

				/// Campos da classe ClienteModel
				switch (campo) {
				case "Nome":
					cliente.setNome(valorNovo);
					break;
				case "Telefone":
					cliente.setTelefone1(valorNovo);
					break;
				}

				try {
					new ClienteRepository(banco).alterarCliente(cliente, new ListenerConsulta() {

						@Override
						public void sucesso(int resultadosAfetados, List<Long> chaves) {
							System.out.println("Cliente editado no banco com sucesso");
						}

						@Override
						public void erro(Exception e) {
							ComponentesServices.mostrarErro("Falha ao editar o cliente");
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					ComponentesServices.mostrarErro("Falha ao editar o cliente");
					e.printStackTrace();
					return false;
				}
				return true;
			}
		}, carro -> {
			try {
				new ClienteRepository(banco).excluirCliente(carro, new ListenerConsulta() {
					@Override
					public void sucesso(int resultadosAfetados, List<Long> chaves) {
						ComponentesServices.mostrarInformacao("Cliente removido do sistema com sucesso");
					}

					@Override
					public void erro(Exception e) {
						ComponentesServices.mostrarErro("Falha ao remover o cliente");
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				ComponentesServices.mostrarErro("Falha ao remover o cliente");
				e.printStackTrace();
			}
		});

		this.banco = banco;
		this.definirCamposBusca(PLACA);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Cliente>> callbackListaOriginal)
			throws IOException {
		ClienteRepository clienteRepository = new ClienteRepository(banco);
		clienteRepository.consultarClientesPorNome(camposBusca.get(PLACA.getIdentificador()), limite,
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
