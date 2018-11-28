package cmtop.domain.repository;

import java.io.IOException;

import cmtop.domain.entity.ValorEntrada;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class ValorEntradaRepository {

	private Tabela tabela;
	private Banco banco;

	public ValorEntradaRepository(Banco banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("finaciamento");
	}

	@SuppressWarnings("unused")
	private ValorEntrada converterRegistroEmValorEntrada(Registro registro) {
		int id_venda = registro.get("id_venda").getAsInt();
		String descricao = registro.get("descricao").getAsString();
		float valor_entrada = registro.get("valor_entrada").getAsFloat();

		ValorEntrada valorEntrada = new ValorEntrada(descricao, valor_entrada);
		valorEntrada.setIdVenda(id_venda);
		return valorEntrada;
	}

	private Registro converterValorEntradaEmRegistro(ValorEntrada valorEntrada) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("id_venda", new ValorInt(valorEntrada.getIdVenda()));
		registro.set("descricao", new ValorString(valorEntrada.getDescricao()));
		registro.set("valor_entrada", new ValorFloat(valorEntrada.getValor()));
		return registro;
	}

	public void adicionarValorEntrada(ValorEntrada valorEntrada, ListenerConsulta listener) {
		Registro registro = converterValorEntradaEmRegistro(valorEntrada);
		tabela.inserir(registro, listener);
	}

}
