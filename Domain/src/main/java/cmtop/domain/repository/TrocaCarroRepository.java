package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.TrocaCarro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class TrocaCarroRepository {

	private Tabela tabela;
	private Banco banco;

	public TrocaCarroRepository(Banco banco) throws IOException {
		this.banco = banco;
		tabela = banco.getTabela("troca_carro");
	}

	private TrocaCarro converterRegistroEmTrocaCarro(Registro registro) {
		int id = registro.get("id_troca").getAsInt();

		String placa = registro.get("placa").getAsString();
		String modelo = registro.get("modelo").getAsString();
		String marca = registro.get("marca").getAsString();
		String cor = registro.get("cor").getAsString();
		String local = registro.get("local").getAsString();
		int ano = registro.get("ano").getAsInt();
		float valor_carro = registro.get("valor_carro").getAsFloat();
		long data_entrada = registro.get("data_entrada").getAsLong();
		int id_venda = registro.get("id_venda").getAsInt();

		return new TrocaCarro(id, placa, modelo, marca, cor, local, ano, valor_carro, data_entrada, id_venda);
	}

	private List<TrocaCarro> converterRegistrosEmTrocasCarro(List<Registro> registros) {
		List<TrocaCarro> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			TrocaCarro entidade = converterRegistroEmTrocaCarro(registro);
			resultado.add(entidade);
		}
		return resultado;
	}

	private Registro converterTrocaCarroEmRegistro(TrocaCarro trocaCarro) {
		Registro registro = new Registro(banco.getTipoConexao());
		registro.set("placa", new ValorString(trocaCarro.getPlaca()));
		registro.set("modelo", new ValorString(trocaCarro.getModelo()));
		registro.set("marca", new ValorString(trocaCarro.getMarca()));
		registro.set("cor", new ValorString(trocaCarro.getCor()));
		registro.set("local", new ValorString(trocaCarro.getLocal()));
		registro.set("ano", new ValorInt(trocaCarro.getAno()));
		registro.set("valor_carro", new ValorFloat(trocaCarro.getValorCarro()));
		registro.set("data_entrada", new ValorLong(trocaCarro.getDataEntrada()));
		registro.set("id_venda", new ValorInt(trocaCarro.getIdVenda()));
		return registro;
	}

	public void adicionarCarroTroca(TrocaCarro trocaCarro, ListenerConsulta listener) {
		Registro registro = converterTrocaCarroEmRegistro(trocaCarro);
		tabela.inserir(registro, listener);
	}

	public void listarTrocasCarro(int limite, ListenerConsultaComResposta<TrocaCarro> listener) {
		Condicao condicao = new Condicao();
		tabela.buscar(condicao, limite, construirListenerRegistros(listener));
	}

	private ListenerConsultaComResposta<Registro> construirListenerRegistros(
			ListenerConsultaComResposta<TrocaCarro> listener) {
		return new ListenerConsultaComResposta<Registro>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Registro> registros) {
				listener.resposta(converterRegistrosEmTrocasCarro(registros));
			}
		};
	}
}
