package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Cliente;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class ClienteRepository {

	private Tabela tabela;
	private Banco banco;

	public ClienteRepository(Banco banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("cliente");
	}

	private Cliente converterRegistroEmCliente(Registro registro) {
		int id = registro.get("id_cliente").getAsInt();

		String nome = registro.get("nome").getAsString();
		String rg = registro.get("rg").getAsString();
		String cpf = registro.get("cpf").getAsString();
		String endereco = registro.get("endereco").getAsString();
		String telefone1 = registro.get("telefone1").getAsString();
		String telefone2 = registro.get("telefone1").getAsString();
		String dataNascimento = registro.get("dt_nasc").getAsString();

		return new Cliente(id, nome, rg, cpf, endereco, telefone1, telefone2, dataNascimento);
	}

	private Registro converterClienteEmRegistro(Cliente cliente) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("rg", new ValorString(cliente.getRg()));
		registro.set("cpf", new ValorString(cliente.getCpf()));
		registro.set("nome", new ValorString(cliente.getNome()));
		registro.set("dt_nasc", new ValorString(cliente.getDataNascimento()));
		registro.set("endereco", new ValorString(cliente.getEndereco()));
		registro.set("telefone1", new ValorString(cliente.getTelefone1()));
		registro.set("telefone2", new ValorString(cliente.getTelefone2()));
		return registro;
	}

	private List<Cliente> converterRegistrosEmClientes(List<Registro> registros) {
		List<Cliente> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Cliente cliente = converterRegistroEmCliente(registro);
			resultado.add(cliente);
		}
		return resultado;
	}

	private ListenerConsultaComResposta<Registro> construirListenerRegistros(
			ListenerConsultaComResposta<Cliente> listener) {
		return new ListenerConsultaComResposta<Registro>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Registro> registros) {
				listener.resposta(converterRegistrosEmClientes(registros));
			}
		};
	}

	public void consultarClientePorRg(String valor, int limite, ListenerConsultaComResposta<Cliente> listener) {
		Condicao condicao = new Condicao();
		condicao.add("rg", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void consultarClientesPorNome(String valor, int limite, ListenerConsultaComResposta<Cliente> listener) {
		Condicao condicao = new Condicao();
		condicao.add("nome", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void consultarClientePorCpf(String valor, int limite, ListenerConsultaComResposta<Cliente> listener) {
		Condicao condicao = new Condicao();
		condicao.add("cpf", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void obterClientePorId(int id, ListenerConsultaComResposta<Cliente> listener) {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(id));
		tabela.buscar(condicao, 1, construirListenerRegistros(listener));
	}

	public void cadastrarCliente(Cliente cliente, ListenerConsulta listener) {
		Registro registro = converterClienteEmRegistro(cliente);
		tabela.inserir(registro, listener);
	}

	public void alterarCliente(Cliente cliente, ListenerConsulta listener) {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(cliente.getId()));

		Registro registro = converterClienteEmRegistro(cliente);
		tabela.atualizar(condicao, registro, listener);
	}

	public void excluirCliente(Cliente cliente, ListenerConsulta listener) {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(cliente.getId()));

		tabela.remover(condicao, listener);
	}

}
