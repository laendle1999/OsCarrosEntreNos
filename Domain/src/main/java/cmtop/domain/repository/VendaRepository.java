package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.NotaFiscal;
import cmtop.domain.entity.Venda;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class VendaRepository {

	private Tabela tabela;

	private Tabela tabelaNotaFiscal;

	public VendaRepository(Banco banco) {
		tabela = banco.getTabela("carro");
		tabelaNotaFiscal = banco.getTabela("nota_fiscal");
	}

	private Venda converterRegistroEmVenda(Registro registro) {
		int id = registro.get("id_venda").getAsInt();
		int id_cliente = registro.get("id_cliente").getAsInt();
		int id_carro = registro.get("id_carro").getAsInt();
		int id_funcionario = registro.get("id_funcionario").getAsInt();
		String dt_venda = registro.get("dt_venda").getAsString();
		String num_venda = registro.get("num_venda").getAsString();

		Venda venda = new Venda(id, num_venda, dt_venda, id_cliente, id_funcionario);
		venda.setCarro(id_carro);
		return venda;
	}

	private Registro converterVendaEmRegistro(Venda venda) {
		Registro registro = new Registro();
		registro.set("id_cliente", new ValorInt(venda.getCliente()));
		registro.set("id_carro", new ValorInt(venda.getCarro()));
		registro.set("id_funcionario", new ValorInt(venda.getVendedor()));
		registro.set("dt_venda", new ValorString(venda.getDataVenda()));
		registro.set("num_venda", new ValorString(venda.getNumeroVenda()));
		return registro;
	}

	public List<Venda> consultarVendaPorNumero(String valor, int limite) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("num_venda", TipoCondicao.SIMILAR, new ValorString(valor));

		List<Registro> registros = tabela.buscar(condicao, limite);
		List<Venda> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Venda objeto = converterRegistroEmVenda(registro);
			resultado.add(objeto);
		}
		return resultado;
	}

	public void gravarVenda(Venda venda) throws IOException {
		Registro registro = converterVendaEmRegistro(venda);

		tabela.inserir(registro);
	}

	public List<Venda> consultarVendasDeCliente(int id_cliente, int limite) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(id_cliente));

		List<Registro> registros = tabela.buscar(condicao, limite);
		List<Venda> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Venda objeto = converterRegistroEmVenda(registro);
			resultado.add(objeto);
		}
		return resultado;
	}

	public void addNotaFiscal(Venda venda, NotaFiscal notaFiscal) throws IOException {
		Registro registro = new Registro();
		registro.set("id_venda", new ValorInt(venda.getId()));
		registro.set("endereco_arquivo", new ValorString(notaFiscal.getArquivo()));

		tabelaNotaFiscal.inserir(registro);
	}

}
