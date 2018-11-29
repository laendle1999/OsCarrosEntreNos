package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.ModelGenerico;
import cmtop.application.model.VendedorModel;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscaVendedor extends Busca<Vendedor> {

	private Banco banco;

	public BuscaVendedor(Banco banco, Consumer<Vendedor> callback) {
		super("Vendedor", "Selecionar vendedor", callback);

		this.banco = banco;
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Vendedor>> callbackListaOriginal)
			throws IOException {
		VendedorRepository repository = new VendedorRepository(banco);

		repository.listarVendedores(500, new ListenerConsultaComResposta<Vendedor>() {

			@Override
			public void erro(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void resposta(List<Vendedor> resultados) {
				callbackListaOriginal.accept(resultados);

				List<VendedorModel> lista = new ArrayList<>();
				resultados.forEach(resultado -> lista.add(new VendedorModel(resultado)));
				callbackListaModel.accept(lista);
			}
		});
	}

}
