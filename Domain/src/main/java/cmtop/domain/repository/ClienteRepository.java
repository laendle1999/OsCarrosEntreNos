package cmtop.domain.aggregate;

import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Cliente;

public class ClienteRepository {

	private Tabela tabela;

	public ClienteRepository(Banco 
banco) {
		tabela = new 
Tabela(banco);
	}

	public void 
cadastrarCliente(Cliente cliente) {
		Registro registro = new 
Registro();
		registro.add("nome", new 
ValorString(cliente.getNome());

		tabela.inserir(registro);
	}

}
