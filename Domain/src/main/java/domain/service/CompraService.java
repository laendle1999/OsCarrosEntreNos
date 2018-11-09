package domain.service;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Compra;

public class CompraService {

	public void comprarCarro(String localCompra, String nomeFornecedor, long data, float custo, Carro carro) {
		Compra compra = new Compra(localCompra, nomeFornecedor, data, custo, carro);

		// CompraRepository compraRepository = new CompraRepository();
		// compraRepository.cadastrarCompra(compra);

		// CarroRepository carroRepository = new CarroRepository();
		// carroRepository.cadastrarCarro(carro);
	}

}
