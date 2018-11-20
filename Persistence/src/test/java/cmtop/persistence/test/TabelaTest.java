package cmtop.persistence.test;

import org.junit.Test;

import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class TabelaTest {

	@Test
	public void inserirTest() {
		Registro registro = new Registro();
		registro.set("a", new ValorInt(10));
		registro.set("b", new ValorString("abc"));

		Tabela tabela = new Tabela("tabela");
		tabela.inserir(registro);

		// TODO buscar e verificar se resultado é igual
	}

	@Test
	public void removerTest() {
		Condicao condicao = new Condicao();
		condicao.add("a", TipoCondicao.IGUAL, new ValorInt(10));
		condicao.add("b", TipoCondicao.SIMILAR, new ValorString("xyz"));

		Tabela tabela = new Tabela("tabela");
		tabela.remover(condicao);

		// TODO buscar e verificar se determinado registro foi removido
	}

}
