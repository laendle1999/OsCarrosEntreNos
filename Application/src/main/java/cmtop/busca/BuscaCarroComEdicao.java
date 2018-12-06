package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.CarroModel;
import cmtop.application.model.ModelGenerico;
import cmtop.application.service.ComponentesServices;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import cmtop.domain.entity.Carro;
import cmtop.domain.repository.CarroRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscaCarroComEdicao extends BuscaComEdicao<Carro> {

	private Banco banco;

	private static Campo PLACA = new Campo(TipoCampo.TEXTO, "Placa", "placa", "");

	@SuppressWarnings("unused")
	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "renavan", "");

	public BuscaCarroComEdicao(Banco banco, String titulo) {
		super("Carro", titulo, new ListenerAlteracoes<Carro>() {

			@Override
			public boolean aceitarMudanca(Carro carro, String campo, String valorNovo) {
				if (campo.equals("Renavan") || campo.equals("Data de entrada"))
					return false;

				/// Campos da classe CarroModel
				switch (campo) {
				case "Marca":
					carro.setMarca(valorNovo);
					break;
				case "Ano":
					try {
						int ano = Integer.parseInt(valorNovo);
						carro.setAno(ano);
					} catch (NumberFormatException e) {
						return false;
					}
					break;
				case "Cor":
					carro.setCor(valorNovo);
					break;
				case "Modelo":
					carro.setModelo(valorNovo);
					break;
				case "Placa":
					carro.setPlaca(valorNovo);
					break;
				case "Valor de venda":
					try {
						float valor = Float.parseFloat(valorNovo);
						carro.setValorVenda(valor);
					} catch (NumberFormatException e) {
						return false;
					}
					break;
				}

				try {
					new CarroRepository(banco).alterarCarro(carro, new ListenerConsulta() {

						@Override
						public void sucesso(int resultadosAfetados, List<Long> chaves) {
							ComponentesServices.mostrarInformacao("Carro editado com sucesso");
						}

						@Override
						public void erro(Exception e) {
							ComponentesServices.mostrarErro("Falha ao editar o carro");
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					ComponentesServices.mostrarErro("Falha ao editar o carro");
					e.printStackTrace();
					return false;
				}
				return true;
			}
		}, carro -> {
			try {
				new CarroRepository(banco).removerCarro(carro, new ListenerConsulta() {
					@Override
					public void sucesso(int resultadosAfetados, List<Long> chaves) {
						ComponentesServices.mostrarInformacao("Carro removido do sistema com sucesso");
					}

					@Override
					public void erro(Exception e) {
						ComponentesServices.mostrarErro("Falha ao remover o carro");
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				ComponentesServices.mostrarErro("Falha ao remover o carro");
				e.printStackTrace();
			}
		});

		this.banco = banco;
		this.definirCamposBusca(PLACA);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Carro>> callbackListaOriginal)
			throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.obterCarrosPorPlaca(camposBusca.get(PLACA.getIdentificador()), limite,
				new ListenerConsultaComResposta<Carro>() {

					@Override
					public void erro(Exception e) {
					}

					@Override
					public void resposta(List<Carro> resultados) {
						callbackListaOriginal.accept(resultados);

						List<CarroModel> lista = new ArrayList<>();
						resultados.forEach(resultado -> lista.add(new CarroModel(resultado)));
						callbackListaModel.accept(lista);
					}
				});
	}

}
