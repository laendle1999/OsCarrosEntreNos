package cmtop.application.model;

import cmtop.domain.entity.Carro;

public class CarroModel extends ModelGenerico {

	public CarroModel(Carro carro) {
		adicionarColuna("Renavan", carro.getRenavan(), false);
		adicionarColuna("Marca", carro.getMarca(), true);
		adicionarColuna("Ano", carro.getAno() + "", true);
		adicionarColuna("Cor", carro.getCor(), true);
		adicionarColuna("Modelo", carro.getModelo(), true);
		adicionarColuna("Placa", carro.getPlaca(), false);
	}

}
