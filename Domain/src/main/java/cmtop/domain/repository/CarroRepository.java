package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Carro;
import cmtop.domain.valueobject.StatusCarro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class CarroRepository {

	private Tabela tabela;

	private Banco banco;

	public CarroRepository(Banco banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("carro");
	}

	private Carro converterRegistroEmCarro(Registro registro) {
		int id = registro.get("id_carro").getAsInt();
		String renavan = registro.get("renavan").getAsString();
		String numero = registro.get("numero").getAsString();
		String placa = registro.get("placa").getAsString();
		String modelo = registro.get("modelo").getAsString();
		int ano = registro.get("ano").getAsInt();
		String marca = registro.get("marca").getAsString();
		String cor = registro.get("cor").getAsString();
		float valor_carro = registro.get("valor_carro").getAsFloat();
		float custo = registro.get("custo").getAsFloat();
		long data_entrada = registro.get("data_entrada").getAsLong();
		int status = registro.get("status").getAsInt();

		StatusCarro statusCarro = StatusCarro.fromInt(status);

		return new Carro(id, numero, placa, renavan, modelo, marca, cor, ano, valor_carro, custo, data_entrada,
				statusCarro);
	}

	private Registro converterCarroEmRegistro(Carro carro) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("renavan", new ValorString(carro.getRenavan()));
		registro.set("numero", new ValorString(carro.getNumero()));
		registro.set("placa", new ValorString(carro.getPlaca()));
		registro.set("modelo", new ValorString(carro.getModelo()));
		registro.set("ano", new ValorInt(carro.getAno()));
		registro.set("marca", new ValorString(carro.getMarca()));
		registro.set("cor", new ValorString(carro.getCor()));
		registro.set("valor_carro", new ValorFloat(carro.getValorVenda()));
		registro.set("custo", new ValorFloat(carro.getCusto()));
		registro.set("data_entrada", new ValorLong(carro.getDataEntrada()));
		registro.set("status", new ValorInt(carro.getStatusCarro().asInt()));
		return registro;
	}

	private List<Carro> converterRegistrosEmCarros(List<Registro> registros) {
		List<Carro> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Carro carro = converterRegistroEmCarro(registro);
			resultado.add(carro);
		}
		return resultado;
	}

	public void cadastrarCarro(Carro carro, ListenerConsulta listener) {
		Registro registro = converterCarroEmRegistro(carro);
		tabela.inserir(registro, listener);
	}

	private ListenerConsultaComResposta<Registro> construirListenerRegistros(
			ListenerConsultaComResposta<Carro> listener) {
		return new ListenerConsultaComResposta<Registro>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Registro> registros) {
				listener.resposta(converterRegistrosEmCarros(registros));
			}
		};
	}

	public void obterCarrosPorPlaca(String valor, int limite, ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("placa", TipoCondicao.SIMILAR, new ValorString(valor));

		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void obterCarrosPorRenavan(String valor, int limite, ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("renavan", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void obterCarrosPorNumero(String valor, int limite, ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("numero", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void obterCarrosPorModelo(String valor, int limite, ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("modelo", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void obterCarrosPorCor(String valor, int limite, ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("cor", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void obterCarroPorId(int id, ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("id_carro", TipoCondicao.IGUAL, new ValorInt(id));
		tabela.buscar(condicao, 1, construirListenerRegistros(listener));
	}

	public void definirCarroVendido(Carro carro, ListenerConsulta listener) {
		carro.setStatusCarro(StatusCarro.VENDIDO);

		Condicao condicao = new Condicao();
		condicao.add("id_carro", TipoCondicao.IGUAL, new ValorInt(carro.getId()));

		Registro registro = converterCarroEmRegistro(carro);
		tabela.atualizar(condicao, registro, listener);
	}

	public void buscarCarrosComDataEntradaSuperiorA(long data, int limiteResultados,
			ListenerConsultaComResposta<Carro> listener) {
		Condicao condicao = new Condicao();
		condicao.add("data_entrada", TipoCondicao.MAIOR, new ValorLong(data));

		tabela.buscar(condicao, limiteResultados, construirListenerRegistros(listener));
	}

}
