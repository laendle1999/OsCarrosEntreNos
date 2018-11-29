package cmtop.domain;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cmtop.domain.entity.Carro;
import cmtop.domain.repository.CarroRepository;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class CarroRepositoryTest {

	@Test
	public void test() throws IOException {
		BancoServidorRedeLocal banco = new BancoServidorRedeLocal(TipoBanco.DERBY, 5);

		CarroRepository carroRepository = new CarroRepository(banco);
		carroRepository.obterCarrosPorPlaca("a", 100, new ListenerConsultaComResposta<Carro>() {

			@Override
			public void erro(Exception e) {
			}

			@Override
			public void resposta(List<Carro> registros) {
			}
		});
	}

}
