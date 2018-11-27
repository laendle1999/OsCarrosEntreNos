package cmtop.persistence.test;

import java.io.IOException;

import org.junit.Test;

import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Banco.TipoConexao;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class TabelaTest {

	@Test
	public void inserirRemoverTest() throws IOException {
		Registro registro = new Registro(TipoConexao.SERVIDOR_DERBY);
		registro.set("rg", new ValorString("10"));
		registro.set("cpf", new ValorString("15"));
		registro.set("dt_nasc", new ValorLong(1543344888115L));
		registro.set("endereco", new ValorString("endereco"));
		registro.set("telefone1", new ValorString("12345678"));
		registro.set("nome", new ValorString("abc"));

		Tabela tabela = getBanco().getTabela("cliente");
		tabela.inserir(registro);

		// TODO buscar e verificar se resultado é igual

		Condicao condicao = new Condicao();
		condicao.add("rg", TipoCondicao.IGUAL, new ValorString("10"));
		condicao.add("nome", TipoCondicao.SIMILAR, new ValorString("abc"));

		tabela.remover(condicao);

		// TODO buscar e verificar se determinado registro foi removido

	}

	private static Banco getBanco() {
		return new Banco(TipoConexao.SERVIDOR_DERBY);
	}

}
