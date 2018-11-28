package cmtop.domain.service;

import java.io.IOException;
import java.util.List;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Compra;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.CompraRepository;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class CompraService {

	private BancoServidorRedeLocal banco;

	public CompraService(BancoServidorRedeLocal banco) {
		this.banco = banco;
	}

	public void cadastrarNovaCompraDeCarro(String localCompra, String nomeFornecedor, String data, float custo,
			Carro carro, ListenerConsulta listener) throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.cadastrarCarro(carro, listener);

		// Recuperar carro
		carroRepository.obterCarrosPorRenavan(carro.getRenavan(), 1, new ListenerConsultaComResposta<Carro>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Carro> registros) {
				Carro carroCadastrado = registros.get(0);

				Compra compra = new Compra(localCompra, nomeFornecedor, data, custo, carroCadastrado.getId());

				try {
					CompraRepository compraRepository = new CompraRepository(banco);
					compraRepository.cadastrarCompra(compra, listener);
				} catch (IOException e) {
					listener.erro(e);
				}
			}
		});
	}

}
