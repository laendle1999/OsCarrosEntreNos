package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Compra;
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

public class CompraRepository {

	private Tabela tabela;
	private Banco banco;

	public CompraRepository(Banco banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("compra");
	}

	private Registro converterCompraEmRegistro(Compra compra) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("id_carro", new ValorInt(compra.getCarro()));
		registro.set("dt_compra", new ValorLong(compra.getData()));
		registro.set("local_compra", new ValorString(compra.getLocalCompra()));
		registro.set("custo", new ValorFloat(compra.getCusto()));
		registro.set("nome_do_fornecedor", new ValorString(compra.getNomeFornecedor()));
		return registro;
	}

	private Compra converterRegistroEmCompra(Registro registro) {
		int id_carro = registro.get("id_carro").getAsInt();
		long dt_compra = registro.get("dt_compra").getAsLong();
		String local_compra = registro.get("local_compra").getAsString();
		float custo = registro.get("custo").getAsFloat();
		String nome_do_fornecedor = registro.get("nome_do_fornecedor").getAsString();

		return new Compra(local_compra, nome_do_fornecedor, dt_compra, custo, id_carro);
	}

	private List<Compra> converterRegistrosEmEntidades(List<Registro> registros) {
		List<Compra> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Compra entidade = converterRegistroEmCompra(registro);
			resultado.add(entidade);
		}
		return resultado;
	}

	public void cadastrarCompra(Compra compra, ListenerConsulta listener) {
		Registro registro = converterCompraEmRegistro(compra);

		tabela.inserir(registro, listener);
	}

	public void obterComprasRealizadasApos(long data, int limite, ListenerConsultaComResposta<Compra> listener) {
		Condicao condicao = new Condicao();
		condicao.add("dt_compra", TipoCondicao.MAIOR, new ValorLong(data));

		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	private ListenerConsultaComResposta<Registro> construirListenerRegistros(
			ListenerConsultaComResposta<Compra> listener) {
		return new ListenerConsultaComResposta<Registro>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Registro> registros) {
				listener.resposta(converterRegistrosEmEntidades(registros));
			}
		};
	}

}
