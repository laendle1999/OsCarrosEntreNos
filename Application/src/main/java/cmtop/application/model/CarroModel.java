package cmtop.application.model;

import cmtop.domain.entity.Carro;

public class CarroModel extends ModelGenerico {

	public CarroModel(Carro carro) {
		adicionarColuna("Marca", carro.getMarca());
		adicionarColuna("Ano", carro.getAno() + "");
		adicionarColuna("Cor", carro.getCor());
		adicionarColuna("Modelo", carro.getModelo());
		adicionarColuna("Placa", carro.getPlaca());
  }
	
}
