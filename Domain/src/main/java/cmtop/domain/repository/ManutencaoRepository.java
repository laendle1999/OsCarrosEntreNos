package cmtop.domain.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Manutencao;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorFloat;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class ManutencaoRepository {

	private Tabela tabela;

	public ManutencaoRepository(Banco banco) {
		tabela = banco.getTabela("compra");
	}

	private Manutencao converterRegistroEmManutencao(Registro registro) {
		int id = registro.get("id_manutencao").getAsInt();

		int id_carro = registro.get("id_carro").getAsInt();
		String descricao = registro.get("descricao").getAsString();
		String dt_manutencao = registro.get("dt_manutencao").getAsString();
		float custo = registro.get("custo").getAsFloat();

		return new Manutencao(id, descricao, dt_manutencao, custo, id_carro);
	}

	private Registro converterManutencaoEmRegistro(Manutencao manutencao) {
		Registro registro = new Registro();
		registro.set("id_carro", new ValorInt(manutencao.getCarro()));
		registro.set("descricao", new ValorString(manutencao.getDescricao()));
		registro.set("dt_manutencao", new ValorString(manutencao.getData()));
		registro.set("custo", new ValorFloat(manutencao.getCusto()));

		return registro;
	}

	public void cadastrarManutencao(Manutencao manutencao) throws IOException {
		Registro registro = converterManutencaoEmRegistro(manutencao);

		tabela.inserir(registro);
	}

	public List<Manutencao> obterManutencoesDeCarro(Carro carro, int limite) throws IOException {
		Condicao condicao = new Condicao();
		condicao.add("id_carro", TipoCondicao.IGUAL, new ValorInt(carro.getId()));

		List<Registro> registros = tabela.buscar(condicao, limite);
		List<Manutencao> resultado = new ArrayList<>();

		for (Registro registro : registros) {
			Manutencao objeto = converterRegistroEmManutencao(registro);
			resultado.add(objeto);
		}
		return resultado;
	}

}
