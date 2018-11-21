package cmtop.domain.service;

import java.io.IOException;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Compra;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.CompraRepository;
import cmtop.persistence.entity.Banco;

public class CompraService {

	public static void comprarCarro(Banco banco, String localCompra, String nomeFornecedor, String data, float custo,
			Carro carro) throws IOException {
		Compra compra = new Compra(localCompra, nomeFornecedor, data, custo, carro);

		CompraRepository compraRepository = new CompraRepository(banco);
		compraRepository.cadastrarCompra(compra);

		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.cadastrarCarro(carro);
	}

}
