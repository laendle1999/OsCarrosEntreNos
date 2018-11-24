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

public class BuscaCliente extends Busca {

	private Banco banco;

	private static Campo NOME = new Campo(TipoCampo.TEXTO, "Nome", "");

	private static Campo RG = new Campo(TipoCampo.TEXTO, "RG", "");

	public BuscaCliente(Banco banco) {
		super("Cliente");

		this.banco = banco;
		this.definirCamposBusca(NOME);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite, Consumer<List<? extends ModelGenerico>> callback)
			throws IOException {
		ClienteRepository clienteRepository = new ClienteRepository(banco);
		List<Cliente> resultados = clienteRepository.consultarClientesPorNome(camposBusca.get(NOME.getNome()), limite);

		List<ClienteModel> lista = new ArrayList<>();
		resultados.forEach(resultado -> lista.add(new ClienteModel(resultado)));
		callback.accept(lista);
	}

}
