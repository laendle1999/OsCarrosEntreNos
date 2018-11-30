package cmtop.application.model;

import cmtop.domain.entity.Carro;
import cmtop.domain.service.DateService;

public class CarroModel extends ModelGenerico {

	private Carro carro = null;

	public CarroModel(Carro carro) {
		this.carro = carro;
		adicionarColuna("Renavan", carro.getRenavan(), false);
		adicionarColuna("Placa", carro.getPlaca(), true);
		adicionarColuna("Marca", carro.getMarca(), true);
		adicionarColuna("Ano", carro.getAno() + "", true);
		adicionarColuna("Data de entrada", DateService.converterTimestampParaDataString(carro.getDataEntrada()), false);
		adicionarColuna("Cor", carro.getCor(), true);
		adicionarColuna("Modelo", carro.getModelo(), true);
	}

	@Override
	public String toString() {

		return carro.getMarca() + " " + carro.getModelo() + " " + carro.getAno() + " - " + carro.getCor();
	}

}
