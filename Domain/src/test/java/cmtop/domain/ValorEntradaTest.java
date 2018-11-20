package cmtop.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.ValorEntrada;

public class ValorEntradaTest {

	private ValorEntrada valorEntrada;

	@Before
	public void criarValorEntrada() {
		this.valorEntrada = new ValorEntrada(null, 0);
	}

	@Test
	public void testeDescricao() {
		valorEntrada.setDescricao("O valor da entrada");
		Assert.assertEquals("O valor da entrada", valorEntrada.getDescricao());
	}

	@Test
	public void testeValor() {

		valorEntrada.setValor(10000);
		Assert.assertEquals(10000, valorEntrada.getValor(), 0);
	}

}
