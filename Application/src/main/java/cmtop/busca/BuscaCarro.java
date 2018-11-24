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

public class BuscaCarro extends Busca {

	private Banco banco;

	private static Campo PLACA = new Campo(TipoCampo.TEXTO, "Placa", "");

	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "");

	public BuscaCarro(Banco banco) {
		super("Carro");

		this.banco = banco;
		this.definirCamposBusca(PLACA);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite, Consumer<List<? extends ModelGenerico>> callback)
			throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		List<Carro> resultados = carroRepository.obterCarrosPorPlaca(camposBusca.get(PLACA.getNome()), limite);

		List<CarroModel> lista = new ArrayList<>();
		resultados.forEach(resultado -> lista.add(new CarroModel(resultado)));
		callback.accept(lista);
	}

}
