package cmtop.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.Carro;

public class CarroTest {

	private Carro carro;

	@Before
	public void criarCarro() {
		this.carro = new Carro(-1, null, null, null, null, null, null, 0, 0, 0, null, null);
	}

	@Test
	public void mudarPlaca() {

		carro.setPlaca("x"); // Aqui estou testando se o setPlaca está funcionando

		Assert.assertNotNull("Não pode aceitar nulo!", carro.getPlaca()); // Não pode ser nulo
		Assert.assertEquals("x", carro.getPlaca());
	}

	@Test
	public void a() {
		carro.setAno(0);

		Assert.assertEquals(0, carro.getAno());
	}

	@After // Para executar algo depois de realizar cada um dos testes

	public void b() {

	}

}