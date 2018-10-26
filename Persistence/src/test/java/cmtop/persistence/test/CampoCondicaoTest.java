package cmtop.persistence.test;

import org.junit.Test;

import cmtop.persistence.valueobject.CampoCondicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class CampoCondicaoTest {

	@Test
	public void igualTest() {
		CampoCondicao a = new CampoCondicao("a", TipoCondicao.IGUAL, new ValorInt(10));
		System.out.println("Igual: " + a.toSQL());
	}

	@Test
	public void diferenteTest() {
		CampoCondicao b = new CampoCondicao("b", TipoCondicao.DIFERENTE, new ValorInt(10));
		System.out.println("Diferente: " + b.toSQL());
	}

	@Test
	public void maiorTest() {
		CampoCondicao c = new CampoCondicao("c", TipoCondicao.MAIOR, new ValorInt(10));
		System.out.println("Maior: " + c.toSQL());
	}

	@Test
	public void similarTest() {
		CampoCondicao d = new CampoCondicao("d", TipoCondicao.SIMILAR, new ValorString("abc"));
		System.out.println("Similar: " + d.toSQL());
	}

}
