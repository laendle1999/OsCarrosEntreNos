package cmtop.domain.repository;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Carro;
import cmtop.domain.valueobject.StatusCarro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class CarroRepository {

	private Tabela tabela;

	public CarroRepository(Banco banco) {
		tabela = banco.getTabela("carro");
	}

	private Carro converterRegistroEmCarro(Registro registro) {
		if (registro.has("id_carro") && registro.has("renavan") && registro.has("numero") && registro.has("placa")
				&& registro.has("modelo") && registro.has("ano") && registro.has("marca") && registro.has("cor")
				&& registro.has("valor_carro") && registro.has("custo") && registro.has("data_entrada")
				&& registro.has("status")) {
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
			String data_entrada = registro.get("data_entrada").getAsString();
			int status = registro.get("status").getAsInt();

			StatusCarro statusCarro = StatusCarro.fromInt(status);

			return new Carro(id, numero, placa, renavan, modelo, marca, cor, ano, valor_carro, custo, data_entrada,
					statusCarro);
		} else {
			throw new InvalidParameterException();
		}
	}

	private Registro converterCarroEmRegistro(Carro carro) {
		Registro registro = new Registro();
		registro.set("renavan", new ValorString(carro.getRenavan()));
		registro.set("numero", new ValorString(carro.getNumero()));
		registro.set("placa", new ValorString(carro.getPlaca()));
		registro.set("modelo", new ValorString(carro.getModelo()));
		registro.set("ano", new ValorInt(carro.getAno()));
		registro.set("marca", new ValorString(carro.getMarca()));
		registro.set("cor", new ValorString(carro.getCor()));
		registro.set("valor_carro", new ValorFloat(carro.getValorVenda()));
		registro.set("custo", new ValorFloat(carro.getCusto()));
		registro.set("data_entrada", new ValorString(carro.getDataEntrada()));
		registro.set("status", new ValorInt(carro.getStatusCarro().asInt()));
		return registro;
	}

	public void cadastrarCarro(Carro carro) throws IOException {
		Registro registro = converterCarroEmRegistro(carro);
		tabela.inserir(registro);
	}

	public List<Carro> obterCarrosPorPlaca(String valor, int limite) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("placa", TipoCondicao.SIMILAR, new ValorString(valor));

		List<Registro> registros = tabela.buscar(condicao, limite);
		List<Carro> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Carro carro = converterRegistroEmCarro(registro);
			resultado.add(carro);
		}
		return resultado;
	}

	public Carro obterCarroPorId(int id) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_carro", TipoCondicao.IGUAL, new ValorInt(id));

		List<Registro> registros = tabela.buscar(condicao, 1);
		return converterRegistroEmCarro(registros.get(0));
	}

	public void definirCarroVendido(Carro carro) throws IOException {
		carro.setStatusCarro(StatusCarro.VENDIDO);

		Condicao condicao = new Condicao();
		condicao.add("id_carro", TipoCondicao.IGUAL, new ValorInt(carro.getId()));

		Registro registro = converterCarroEmRegistro(carro);
		tabela.atualizar(condicao, registro);
	}

	public List<Carro> buscarCarrosComDataEntradaSuperiorA(String data, int limiteResultados) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("data_entrada", TipoCondicao.MAIOR, new ValorString(data));

		List<Registro> registros = tabela.buscar(condicao, limiteResultados);
		List<Carro> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Carro carro = converterRegistroEmCarro(registro);
			resultado.add(carro);
		}
		return resultado;
	}

}
