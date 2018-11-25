package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Vendedor;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class VendedorRepository {

	private Tabela tabela;

	public VendedorRepository(Banco banco) {
		tabela = banco.getTabela("funcionario");
	}

	private Vendedor converterRegistroEmVendedor(Registro registro) {
		int id = registro.get("id_funcionario").getAsInt();
		String rg = registro.get("rg").getAsString();
		String cpf = registro.get("cpf").getAsString();
		String nome = registro.get("nome").getAsString();
		String dataNascimento = registro.get("dt_nasc").getAsString();
		String endereco = registro.get("endereco").getAsString();
		String telefone1 = registro.get("telefone1").getAsString();
		String telefone2 = registro.get("telefone1").getAsString();
		int tipo_acesso = registro.get("tipo_acesso").getAsInt();
		String login = registro.get("login").getAsString();
		String email = registro.get("email").getAsString();

		TipoAcesso tipoAcesso = TipoAcesso.fromInt(tipo_acesso);

		return new Vendedor(id, telefone1, telefone2, nome, dataNascimento, rg, cpf, endereco, login, email,
				tipoAcesso);

	}

	private Registro converterVendedorEmRegistro(Vendedor vendedor) {
		Registro registro = new Registro();
		registro.set("rg", new ValorString(vendedor.getRg()));
		registro.set("cpf", new ValorString(vendedor.getCpf()));
		registro.set("nome", new ValorString(vendedor.getNome()));
		registro.set("dt_nasc", new ValorString(vendedor.getDataNascimento()));
		registro.set("endereco", new ValorString(vendedor.getEndereco()));
		registro.set("telefone1", new ValorString(vendedor.getTelefone1()));
		registro.set("telefone2", new ValorString(vendedor.getTelefone2()));
		registro.set("tipo_acesso", new ValorInt(vendedor.getTipoAcesso().asInt()));
		registro.set("login", new ValorString(vendedor.getLogin()));
		registro.set("email", new ValorString(vendedor.getEmail()));
		return registro;
	}

	private List<Vendedor> converterRegistrosEmVendedores(List<Registro> registros) {
		List<Vendedor> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Vendedor vendedor = converterRegistroEmVendedor(registro);
			resultado.add(vendedor);
		}
		return resultado;
	}

	public Vendedor obterVendedorPorId(int id) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_funcionario", TipoCondicao.IGUAL, new ValorInt(id));

		List<Registro> registros = tabela.buscar(condicao, 1);
		return converterRegistroEmVendedor(registros.get(0));
	}

	public Vendedor obterVendedorPorLoginSenha(String login, String senha) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("login", TipoCondicao.IGUAL, new ValorString(login));
		condicao.add("senha", TipoCondicao.IGUAL, new ValorString(senha));

		List<Registro> registros = tabela.buscar(condicao, 1);
		if (registros.isEmpty()) {
			return null;
		}
		return converterRegistroEmVendedor(registros.get(0));
	}

	public void cadastrarVendedor(Vendedor vendedor) throws IOException {
		Registro registro = converterVendedorEmRegistro(vendedor);
		tabela.inserir(registro);
	}

	public void alterarVendedor(Vendedor vendedor) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_funcionario", TipoCondicao.IGUAL, new ValorInt(vendedor.getId()));

		Registro registro = converterVendedorEmRegistro(vendedor);
		tabela.atualizar(condicao, registro);
	}

	public void removerVendedor(Vendedor vendedor) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_funcionario", TipoCondicao.IGUAL, new ValorInt(vendedor.getId()));
		tabela.remover(condicao);
	}

	public List<Vendedor> listarVendedores(int limite) throws IOException {
		Condicao condicao = new Condicao();
		return converterRegistrosEmVendedores(tabela.buscar(condicao, limite));
	}

}
