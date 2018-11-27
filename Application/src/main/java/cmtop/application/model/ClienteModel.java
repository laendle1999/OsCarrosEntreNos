package cmtop.application.model;

import cmtop.domain.entity.Cliente;

public class ClienteModel extends ModelGenerico {

	public ClienteModel(Cliente cliente) {
		adicionarColuna("Nome", cliente.getNome(), true);
		adicionarColuna("CPF", cliente.getCpf(), false);
		adicionarColuna("Telefone 1", cliente.getTelefone1(), true);
	}

}
