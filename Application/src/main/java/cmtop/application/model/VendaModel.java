package cmtop.application.model;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Venda;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.service.DateService;

public class VendaModel extends ModelGenerico {

	public VendaModel(Venda venda, Carro carro, Vendedor vend) {

		adicionarColuna("Venda", venda.getNumeroVenda(), false);
		adicionarColuna("Carro", new CarroModel(carro).toString(), false);
		adicionarColuna("Vendedor", vend.getNome(), false);
		adicionarColuna("Data", DateService.converterTimestampParaDataString(venda.getDataVenda()), false);
	}

}
