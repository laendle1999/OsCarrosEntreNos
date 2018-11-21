package cmtop.domain.repository;

import java.io.IOException;

import cmtop.domain.entity.Cliente;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.ValorString;

public class ClienteRepository {

	private Tabela tabela;

	public ClienteRepository(Banco banco) {
		tabela = banco.getTabela("cliente");
	}

	public void cadastrarCliente(Cliente cliente) throws IOException {
		Registro registro = new Registro();
		registro.set("rg", new ValorString(cliente.getRg()));
		registro.set("cpf", new ValorString(cliente.getCpf()));
		registro.set("nome", new ValorString(cliente.getNome()));
		registro.set("dt_nasc", new ValorString(cliente.getNome()));
		registro.set("endereco", new ValorString(cliente.getEndereco()));
		registro.set("telefone1", new ValorString(cliente.getTelefone()));
		registro.set("telefone2", new ValorString(""));

		tabela.inserir(registro);
	}

}
