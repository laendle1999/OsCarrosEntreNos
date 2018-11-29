package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.CarroModel;
import cmtop.application.model.ModelGenerico;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import cmtop.domain.entity.Carro;
import cmtop.domain.repository.CarroRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscarCarroPorTempo extends Busca<Carro> {

	private Banco banco;

	private static Campo DATA = new Campo(TipoCampo.TEXTO, "Data", "placa", "");

	@SuppressWarnings("unused")
	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "renavan", "");

	public BuscarCarroPorTempo(Banco banco, Consumer<Carro> callback) {
		super("Carro", "Selecionar carro", callback);

		this.banco = banco;
		this.definirCamposBusca(DATA);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Carro>> callbackListaOriginal)
			throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.buscarCarrosComDataEntradaSuperiorA(camposBusca.get(DATA.getIdentificador()), limite,
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
