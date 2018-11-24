package cmtop.domain.repository;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Cliente;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class ClienteRepository {

	private Tabela tabela;

	public ClienteRepository(Banco banco) {
		tabela = banco.getTabela("cliente");
	}

	private Cliente converterRegistroEmCliente(Registro registro) {
		if (registro.has("id_cliente") && registro.has("nome") && registro.has("rg") && registro.has("cpf")
				&& registro.has("endereco") && registro.has("telefone1") && registro.has("dt_nasc")) {
			int id = registro.get("id_cliente").getAsInt();

			String nome = registro.get("nome").getAsString();
			String rg = registro.get("rg").getAsString();
			String cpf = registro.get("cpf").getAsString();
			String endereco = registro.get("endereco").getAsString();
			String telefone1 = registro.get("telefone1").getAsString();
			String telefone2 = registro.get("telefone1").getAsString();
			String dataNascimento = registro.get("dt_nasc").getAsString();

			return new Cliente(id, nome, rg, cpf, endereco, telefone1, telefone2, dataNascimento);
		} else {
			throw new InvalidParameterException();
		}
	}

	private Registro converterClienteEmRegistro(Cliente cliente) {
		Registro registro = new Registro();
		registro.set("rg", new ValorString(cliente.getRg()));
		registro.set("cpf", new ValorString(cliente.getCpf()));
		registro.set("nome", new ValorString(cliente.getNome()));
		registro.set("dt_nasc", new ValorString(cliente.getDataNascimento()));
		registro.set("endereco", new ValorString(cliente.getEndereco()));
		registro.set("telefone1", new ValorString(cliente.getTelefone1()));
		registro.set("telefone2", new ValorString(cliente.getTelefone2()));
		return registro;
	}

	public List<Cliente> consultarClientePorRg(String valor, int limite) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("rg", TipoCondicao.SIMILAR, new ValorString(valor));

		List<Registro> registros = tabela.buscar(condicao, limite);
		List<Cliente> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Cliente cliente = converterRegistroEmCliente(registro);
			resultado.add(cliente);
		}
		return resultado;
	}

	public void cadastrarCliente(Cliente cliente) throws IOException {
		Registro registro = converterClienteEmRegistro(cliente);
		tabela.inserir(registro);
	}

	public void alterarCliente(Cliente cliente) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(cliente.getId()));

		Registro registro = converterClienteEmRegistro(cliente);
		tabela.atualizar(condicao, registro);
	}

	public void excluirCliente(Cliente cliente) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(cliente.getId()));

		tabela.remover(condicao);
	}

}
