package cmtop.application.model;

import cmtop.domain.entity.Vendedor;

public class VendedorModel extends ModelGenerico {

	public VendedorModel(Vendedor vendedor) {
		adicionarColuna("Nome", vendedor.getNome(), true);
		adicionarColuna("CPF", vendedor.getCpf(), false);
		adicionarColuna("RG", vendedor.getRg(), false);
		adicionarColuna("Login", vendedor.getLogin(), false);
		adicionarColuna("E-mail", vendedor.getEmail(), true);
	}

}
