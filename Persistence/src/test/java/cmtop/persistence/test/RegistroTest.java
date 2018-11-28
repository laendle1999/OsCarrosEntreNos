package cmtop.persistence.test;

import java.text.ParseException;

import org.junit.Test;

import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.valueobject.ValorInt;
import cmtop.persistence.valueobject.ValorString;

public class RegistroTest {

	@Test
	public void test() throws ParseException {
		Registro registro = new Registro(TipoBanco.DERBY);
		registro.set("a", new ValorInt(10));
		registro.set("b", new ValorInt(10));
		registro.set("c", new ValorInt(10));
		registro.set("d", new ValorString("abc,def"));

		System.out.println(registro.getSQLSET());

		String string = registro.toString();
		System.out.println(string);

		Registro registroRecuperado = Registro.fromString(string, TipoBanco.DERBY);
		System.out.println(registroRecuperado.toString());
	}

}
