package cmtop.domain.repository;

import java.io.IOException;

import cmtop.domain.entity.TrocaCarro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class TrocaCarroRepository {

	private Tabela tabela;

	public TrocaCarroRepository(Banco banco) {
		tabela = banco.getTabela("troca_carro");
	}

	@SuppressWarnings("unused")
	private TrocaCarro converterRegistroEmTrocaCarro(Registro registro) {
		int id = registro.get("id_troca").getAsInt();

		String placa = registro.get("placa").getAsString();
		String modelo = registro.get("modelo").getAsString();
		String marca = registro.get("marca").getAsString();
		String cor = registro.get("cor").getAsString();
		String local = registro.get("local").getAsString();
		int ano = registro.get("ano").getAsInt();
		float valor_carro = registro.get("valor_carro").getAsFloat();
		String data_entrada = registro.get("data_entrada").getAsString();
		int id_venda = registro.get("id_venda").getAsInt();

		return new TrocaCarro(id, placa, modelo, marca, cor, local, ano, valor_carro, data_entrada, id_venda);
	}

	private Registro converterTrocaCarroEmRegistro(TrocaCarro trocaCarro) {
		Registro registro = new Registro();
		registro.set("placa", new ValorString(trocaCarro.getPlaca()));
		registro.set("modelo", new ValorString(trocaCarro.getModelo()));
		registro.set("marca", new ValorString(trocaCarro.getMarca()));
		registro.set("cor", new ValorString(trocaCarro.getCor()));
		registro.set("local", new ValorString(trocaCarro.getLocal()));
		registro.set("ano", new ValorInt(trocaCarro.getAno()));
		registro.set("valor_carro", new ValorFloat(trocaCarro.getValorCarro()));
		registro.set("data_entrada", new ValorString(trocaCarro.getDataEntrada()));
		registro.set("id_venda", new ValorInt(trocaCarro.getIdVenda()));
		return registro;
	}

	public void adicionarCarroTroca(TrocaCarro trocaCarro) throws IOException {
		Registro registro = converterTrocaCarroEmRegistro(trocaCarro);
		tabela.inserir(registro);
	}

}
