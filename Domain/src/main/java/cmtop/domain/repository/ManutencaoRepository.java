package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Manutencao;
import cmtop.persistence.entity.BancoServidorRedeLocal;
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

public class ManutencaoRepository {

	private Tabela tabela;
	private BancoServidorRedeLocal banco;

	public ManutencaoRepository(BancoServidorRedeLocal banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("compra");
	}

	private Manutencao converterRegistroEmManutencao(Registro registro) {
		int id = registro.get("id_manutencao").getAsInt();

		int id_carro = registro.get("id_carro").getAsInt();
		String descricao = registro.get("descricao").getAsString();
		long dt_manutencao = registro.get("dt_manutencao").getAsLong();
		float custo = registro.get("custo").getAsFloat();

		return new Manutencao(id, descricao, dt_manutencao, custo, id_carro);
	}

	private Registro converterManutencaoEmRegistro(Manutencao manutencao) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("id_carro", new ValorInt(manutencao.getCarro()));
		registro.set("descricao", new ValorString(manutencao.getDescricao()));
		registro.set("dt_manutencao", new ValorLong(manutencao.getData()));
		registro.set("custo", new ValorFloat(manutencao.getCusto()));

		return registro;
	}

	private List<Manutencao> converterRegistrosEmEntidades(List<Registro> registros) {
		List<Manutencao> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Manutencao entidade = converterRegistroEmManutencao(registro);
			resultado.add(entidade);
		}
		return resultado;
	}

	public void cadastrarManutencao(Manutencao manutencao, ListenerConsulta listener) {
		Registro registro = converterManutencaoEmRegistro(manutencao);

		tabela.inserir(registro, listener);
	}

	public void obterManutencoesDeCarro(Carro carro, int limite, ListenerConsultaComResposta<Manutencao> listener) {
		Condicao condicao = new Condicao();
		condicao.add("id_carro", TipoCondicao.IGUAL, new ValorInt(carro.getId()));

		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	private ListenerConsultaComResposta<Registro> construirListenerRegistros(
			ListenerConsultaComResposta<Manutencao> listener) {
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
