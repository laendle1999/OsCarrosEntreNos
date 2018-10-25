package cmtop.persistence.test;

import org.junit.Test;

import cmtop.persistence.entity.Registro;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class RegistroTest {

	@Test
	public void test() {
		Registro registro = new Registro();
		registro.set("a", new ValorInt(10));
		registro.set("b", new ValorInt(10));
		registro.set("c", new ValorInt(10));
		registro.set("d", new ValorString("abc"));

		System.out.println(registro.toSQL());
	}

}
