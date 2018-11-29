package cmtop.domain;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Manutencao;
import cmtop.domain.service.DateService;
import cmtop.domain.valueobject.StatusCarro;

public class ManutencaoTest {

	private Manutencao manutencao;

	@Before
	public void criarManutencao() {
		this.manutencao = new Manutencao(-1, null, 0, 0, 0);
	}

	@Test
	public void testeDescricao() {

		manutencao.setDescricao("uma descrição qualquer");
		Assert.assertEquals("uma descrição qualquer", manutencao.getDescricao());
	}

	@Test
	public void testeData() {
		Date date = new Date();
		manutencao.setData(DateService.converterDataEmTimestamp(date));
		Assert.assertEquals(DateService.converterDataEmTimestamp(date), manutencao.getData());
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

		carro = new Carro(-1, "Numero", "EWI0392", "6596416", "Honda", "Honda", "Amarelo", 2012, 10000, 1000, 45454,
				statusCarro);
		carro2 = new Carro(-1, "Numero", "EWI0392", "6596416", "Honda", "Honda", "Amarelo", 2012, 10000, 1000, 12346,
				statusCarro);

		manutencao.setCarro(carro.getId());
		Assert.assertEquals(carro2.getId(), manutencao.getCarro());
	}

}
