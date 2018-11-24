package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Compra;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class CompraRepository {

	private Tabela tabela;

	public CompraRepository(Banco banco) {
		tabela = banco.getTabela("compra");
	}

	private Registro converterCompraEmRegistro(Compra compra) {
		Registro registro = new Registro();
		registro.set("id_carro", new ValorInt(compra.getCarro()));
		registro.set("dt_compra", new ValorString(compra.getData()));
		registro.set("local_compra", new ValorString(compra.getLocalCompra()));
		registro.set("custo", new ValorFloat(compra.getCusto()));
		registro.set("nome_do_fornecedor", new ValorString(compra.getNomeFornecedor()));
		return registro;
	}

	private Compra converterRegistroEmCompra(Registro registro) {
		int id_carro = registro.get("id_carro").getAsInt();
		String dt_compra = registro.get("dt_compra").getAsString();
		String local_compra = registro.get("local_compra").getAsString();
		float custo = registro.get("custo").getAsFloat();
		String nome_do_fornecedor = registro.get("nome_do_fornecedor").getAsString();

		return new Compra(local_compra, nome_do_fornecedor, dt_compra, custo, id_carro);
	}

	public void cadastrarCompra(Compra compra) throws IOException {
		Registro registro = converterCompraEmRegistro(compra);

		tabela.inserir(registro);
	}

	public List<Compra> obterComprasRealizadasApos(String data, int limite) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("dt_compra", TipoCondicao.MAIOR, new ValorString(data));

		List<Registro> registros = tabela.buscar(condicao, limite);
		List<Compra> resultado = new ArrayList<>();
		for (Registro registro : registros) {
			resultado.add(converterRegistroEmCompra(registro));
		}
		return resultado;
	}

}
