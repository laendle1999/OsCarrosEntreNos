package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.model.ModelGenerico;
import cmtop.application.model.VendedorModel;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscarVendedorComEdicao extends BuscaComEdicao<Vendedor> {

	private Banco banco;

	private static Campo LOGIN = new Campo(TipoCampo.TEXTO, "Placa", "");

	@SuppressWarnings("unused")
	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "");

	public BuscarVendedorComEdicao(Banco banco, ListenerAlteracoes<Vendedor> listenerAlteracoes,
			Consumer<Vendedor> listenerApagar) {
		super("Vendedor", "Selecionar Vendedor", listenerAlteracoes, listenerApagar);

		this.banco = banco;
		this.definirCamposBusca(LOGIN);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Vendedor>> callbackListaOriginal)
			throws IOException {
		VendedorRepository vendedorRepository = new VendedorRepository(banco);
		vendedorRepository.obterVendedorPorLogin(camposBusca.get(LOGIN.getNome()),
				new ListenerConsultaComResposta<Vendedor>() {

					@Override
					public void erro(Exception e) {
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
