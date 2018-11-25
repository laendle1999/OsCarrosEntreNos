package cmtop.domain.service;

import java.io.IOException;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Compra;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.CompraRepository;
import cmtop.persistence.entity.Banco;

public class CompraService {

	private Banco banco;

	public CompraService(Banco banco) {
		this.banco = banco;
	}

	public void cadastrarNovaCompraDeCarro(String localCompra, String nomeFornecedor, String data, float custo,
			Carro carro) throws IOException {
		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.cadastrarCarro(carro);

		// Recuperar carro
		Carro carroCadastrado = carroRepository.obterCarrosPorRenavan(carro.getRenavan(), 1).get(0);

		Compra compra = new Compra(localCompra, nomeFornecedor, data, custo, carroCadastrado.getId());

		CompraRepository compraRepository = new CompraRepository(banco);
		compraRepository.cadastrarCompra(compra);
	}

}
