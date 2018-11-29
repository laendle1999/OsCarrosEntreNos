package cmtop.persistence.test;

import org.junit.Test;

import cmtop.persistence.valueobject.CampoCondicao;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class CondicaoTest {

	@Test
	public void test() {
		CampoCondicao a = new CampoCondicao("a", TipoCondicao.IGUAL, new ValorInt(10));
		CampoCondicao b = new CampoCondicao("b", TipoCondicao.DIFERENTE, new ValorInt(10));
		CampoCondicao c = new CampoCondicao("c", TipoCondicao.MAIOR, new ValorInt(10));
		CampoCondicao d = new CampoCondicao("d", TipoCondicao.SIMILAR, new ValorString("abc'"));
		
		Condicao condicao = new Condicao();
		condicao.add(a);
		condicao.add(b);
		condicao.add(c);
		condicao.add(d);
		
		System.out.println(condicao.toSQL());
	}

}
