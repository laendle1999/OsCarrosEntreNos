package cmtop.domain;

import java.io.IOException;

import org.junit.Test;

import cmtop.domain.repository.CarroRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Banco.TipoConexao;

public class CarroRepositoryTest {

	@Test
	public void test() throws IOException {
		Banco banco = new Banco(TipoConexao.SERVIDOR_DERBY);

		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.obterCarrosPorPlaca("a", 100);
	}

}
