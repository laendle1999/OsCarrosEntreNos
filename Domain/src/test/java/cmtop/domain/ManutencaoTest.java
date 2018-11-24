package cmtop.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Manutencao;
import cmtop.domain.valueobject.StatusCarro;

public class ManutencaoTest {

	private Manutencao manutencao;

	@Before
	public void criarManutencao() {
		this.manutencao = new Manutencao(null, 0, 0, null);
	}

	@Test
	public void testeDescricao() {

		manutencao.setDescricao("uma descrição qualquer");
		Assert.assertEquals("uma descrição qualquer", manutencao.getDescricao());
	}

	@Test
	public void testeData() {

		manutencao.setData(121);
		Assert.assertEquals(121, manutencao.getData());
	}

	@Test
	public void testeCusto() {

		manutencao.setCusto(1000);
		Assert.assertEquals(1000, manutencao.getCusto(), 0);
	}

	@Test
	public void testeCarro() {

		StatusCarro statusCarro;

		statusCarro = StatusCarro.DISPONIVEL;

		Carro carro, carro2;

		carro = new Carro(-1, "Numero", "EWI0392", "6596416", "Honda", "Honda", "Amarelo", 2012, 10000, 1000,
				"10-10-10", statusCarro);
		carro2 = new Carro(-1, "Numero", "EWI0392", "6596416", "Honda", "Honda", "Amarelo", 2012, 10000, 1000,
				"10-10-10", statusCarro);

		manutencao.setCarro(carro);
		Assert.assertEquals(carro2, manutencao.getCarro());
	}

}
