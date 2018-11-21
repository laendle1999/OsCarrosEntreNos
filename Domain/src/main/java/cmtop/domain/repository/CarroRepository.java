package cmtop.domain.repository;

import java.io.IOException;

import cmtop.domain.entity.Carro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class CarroRepository {

	private Tabela tabela;

	public CarroRepository(Banco banco) {
		tabela = banco.getTabela("carro");
	}

	public void cadastrarCarro(Carro carro) throws IOException {
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

		tabela.inserir(registro);
	}

}
