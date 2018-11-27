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

public class BuscaCarroComEdicao extends BuscaComEdicao<Carro> {

	private Banco banco;

	private static Campo PLACA = new Campo(TipoCampo.TEXTO, "Placa", "");

	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "");

	public BuscaCarroComEdicao(Banco banco, Consumer<Carro> callback) {
		super("Carro", "Selecionar carro", (carroAlterado, nomeCampo, valor) -> {
			System.out.println(nomeCampo);
			System.out.println(carroAlterado.getPlaca());
			return true;
		});

		this.banco = banco;
		this.definirCamposBusca(PLACA);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Carro>> callbackListaOriginal)
			throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		List<Carro> resultados = carroRepository.obterCarrosPorPlaca(camposBusca.get(PLACA.getNome()), limite);
		callbackListaOriginal.accept(resultados);

		List<CarroModel> lista = new ArrayList<>();
		resultados.forEach(resultado -> lista.add(new CarroModel(resultado)));
		callbackListaModel.accept(lista);
	}

}
