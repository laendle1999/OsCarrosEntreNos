package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.ModelGenerico;
import cmtop.application.model.TrocaCarroModel;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.repository.TrocaCarroRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscaTrocaCarro extends Busca<TrocaCarro> {

	private Banco banco;

	public BuscaTrocaCarro(Banco banco, Consumer<TrocaCarro> callback) {
		super("Carro", "Selecionar troca de carro", callback);

		this.banco = banco;
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel,
			Consumer<List<TrocaCarro>> callbackListaOriginal) throws IOException {
		TrocaCarroRepository repository = new TrocaCarroRepository(banco);
		repository.listarTrocasCarro(500, new ListenerConsultaComResposta<TrocaCarro>() {

			@Override
			public void erro(Exception e) {
			}

			@Override
			public void resposta(List<TrocaCarro> resultados) {
				callbackListaOriginal.accept(resultados);

				List<TrocaCarroModel> lista = new ArrayList<>();
				resultados.forEach(resultado -> lista.add(new TrocaCarroModel(resultado)));
				callbackListaModel.accept(lista);
			}
		});
	}

}
