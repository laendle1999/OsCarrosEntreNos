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
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class VendaRepository {

	private Tabela tabela;

	private Tabela tabelaNotaFiscal;

	private Banco banco;

	public VendaRepository(Banco banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("venda");
		tabelaNotaFiscal = banco.getTabela("nota_fiscal");
	}

	private Venda converterRegistroEmVenda(Registro registro) {
		int id = registro.get("id_venda").getAsInt();
		int id_cliente = registro.get("id_cliente").getAsInt();
		int id_carro = registro.get("id_carro").getAsInt();
		int id_funcionario = registro.get("id_funcionario").getAsInt();
		long dt_venda = registro.get("dt_venda").getAsLong();
		String num_venda = registro.get("num_venda").getAsString();

		Venda venda = new Venda(id, num_venda, dt_venda, id_cliente, id_funcionario);
		venda.setCarro(id_carro);
		return venda;
	}

	private Registro converterVendaEmRegistro(Venda venda) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("id_cliente", new ValorInt(venda.getCliente()));
		registro.set("id_carro", new ValorInt(venda.getCarro()));
		registro.set("id_funcionario", new ValorInt(venda.getVendedor()));
		registro.set("dt_venda", new ValorLong(venda.getDataVenda()));
		registro.set("num_venda", new ValorString(venda.getNumeroVenda()));
		return registro;
	}

	private List<Venda> converterRegistrosEmVendas(List<Registro> registros) {
		List<Venda> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Venda objeto = converterRegistroEmVenda(registro);
			resultado.add(objeto);
		}
		return resultado;
	}

	public void consultarVendaPorNumero(String valor, int limite, ListenerConsultaComResposta<Venda> listener) {
		Condicao condicao = new Condicao();
		condicao.add("num_venda", TipoCondicao.SIMILAR, new ValorString(valor));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void gravarVenda(Venda venda, ListenerConsulta listener) {
		Registro registro = converterVendaEmRegistro(venda);

		tabela.inserir(registro, listener);
	}

	public void consultarVendasDeCliente(int id_cliente, int limite, ListenerConsultaComResposta<Venda> listener) {
		Condicao condicao = new Condicao();
		condicao.add("id_cliente", TipoCondicao.IGUAL, new ValorInt(id_cliente));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	public void addNotaFiscal(Venda venda, NotaFiscal notaFiscal, ListenerConsulta listener) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("id_venda", new ValorInt(venda.getId()));
		registro.set("endereco_arquivo", new ValorString(notaFiscal.getArquivo()));

		tabelaNotaFiscal.inserir(registro, listener);
	}

	public void obterVendasRealizadasApos(long data, int limite, ListenerConsultaComResposta<Venda> listener) {
		Condicao condicao = new Condicao();
		condicao.add("dt_venda", TipoCondicao.MAIOR, new ValorLong(data));
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	private ListenerConsultaComResposta<Registro> construirListenerRegistros(
			ListenerConsultaComResposta<Venda> listener) {
		return new ListenerConsultaComResposta<Registro>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Registro> registros) {
				listener.resposta(converterRegistrosEmVendas(registros));
			}
		};
	}

}
