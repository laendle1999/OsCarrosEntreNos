package cmtop.domain.repository;

import java.io.IOException;

import cmtop.domain.entity.Compra;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
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
		registro.set("id_carro", new ValorInt(compra.getCarro().getId()));
		registro.set("dt_compra", new ValorString(compra.getData()));
		registro.set("local_compra", new ValorString(compra.getLocalCompra()));
		registro.set("custo", new ValorFloat(compra.getCusto()));
		registro.set("nome_do_fornecedor", new ValorString(compra.getNomeFornecedor()));
		return registro;
	}

	public void cadastrarCompra(Compra compra) throws IOException {
		Registro registro = converterCompraEmRegistro(compra);

		tabela.inserir(registro);
	}

}
