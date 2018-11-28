package cmtop.application.model;

import cmtop.domain.entity.Carro;

public class CarroModel extends ModelGenerico {

	private Carro carro = null;
	
	public CarroModel(Carro carro) {
		this.carro = carro;
		adicionarColuna("Renavan", carro.getRenavan(), false);
		adicionarColuna("Marca", carro.getMarca(), true);
		adicionarColuna("Ano", carro.getAno() + "", true);
		adicionarColuna("Cor", carro.getCor(), true);
		adicionarColuna("Modelo", carro.getModelo(), true);
		adicionarColuna("Placa", carro.getPlaca(), false);
	}
	
	@Override
	public String toString() {
		
		return carro.getMarca() + " " + carro.getModelo() + " " + carro.getAno() + " - " + carro.getCor();
	}

}
