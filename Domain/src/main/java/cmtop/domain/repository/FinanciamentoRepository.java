package cmtop.domain.repository;

import java.io.IOException;

import cmtop.domain.entity.Financiamento;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class FinanciamentoRepository {

	private Tabela tabela;
	private Banco banco;

	public FinanciamentoRepository(Banco banco) {
		this.banco = banco;
		tabela = banco.getTabela("finaciamento");
	}

	@SuppressWarnings("unused")
	private Financiamento converterRegistroEmFinanciamento(Registro registro) {
		int id_venda = registro.get("id_venda").getAsInt();
		String banco = registro.get("banco").getAsString();
		float valorFinanciamento = registro.get("valorFinanciamento").getAsFloat();
		int nParcelas = registro.get("nParcelas").getAsInt();

		Financiamento financiamento = new Financiamento(banco, valorFinanciamento, nParcelas);
		financiamento.setIdVenda(id_venda);
		return financiamento;
	}

	private Registro converterFinanciamentoEmRegistro(Financiamento financiamento) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("id_venda", new ValorInt(financiamento.getIdVenda()));
		registro.set("banco", new ValorString(financiamento.getBanco()));
		registro.set("valorFinanciamento", new ValorFloat(financiamento.getValorFinanciado()));
		registro.set("nParcelas", new ValorInt(financiamento.getNumeroParcelas()));
		return registro;
	}

	public void adicionarFinanciamento(Financiamento financiamento) throws IOException {
		Registro registro = converterFinanciamentoEmRegistro(financiamento);
		tabela.inserir(registro);
	}

}
