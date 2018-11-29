package cmtop.application.model;

import cmtop.domain.entity.TrocaCarro;

public class TrocaCarroModel extends ModelGenerico {

	private TrocaCarro troca = null;

	public TrocaCarroModel(TrocaCarro troca) {
		this.troca = troca;
		adicionarColuna("Marca", troca.getMarca(), true);
		adicionarColuna("Ano", troca.getAno() + "", true);
		adicionarColuna("Cor", troca.getCor(), true);
		adicionarColuna("Modelo", troca.getModelo(), true);
		adicionarColuna("Placa", troca.getPlaca(), false);
	}

	@Override
	public String toString() {
		return troca.getMarca() + " " + troca.getModelo() + " " + troca.getAno() + " - " + troca.getCor();
	}

}
