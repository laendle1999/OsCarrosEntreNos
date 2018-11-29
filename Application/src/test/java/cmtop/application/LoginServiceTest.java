package cmtop.application;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import cmtop.application.service.LoginService;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.service.ServidorRedeLocal;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class LoginServiceTest {

	private boolean logado = false;

	@Test
	public void testeLoginGerente() throws IOException, InterruptedException {
		BancoServidorRedeLocal banco = new BancoServidorRedeLocal(TipoBanco.DERBY, 5);

		CountDownLatch latch = new CountDownLatch(1);

		LoginService.logar(banco, "gerente", "1234", new ListenerConsultaComResposta<Boolean>() {

			@Override
			public void resposta(List<Boolean> registros) {
				logado = (boolean) registros.get(0);
				latch.countDown();
			}

			@Override
			public void erro(Exception e) {
				latch.countDown();
			}
		});

		latch.await();

		assertTrue(logado);

		assertTrue(LoginService.estaLogado());

		assertTrue(LoginService.getFuncionarioLogado().getTipoAcesso() == TipoAcesso.GERENTE);

		ServidorRedeLocal.fecharConexoes();
	}

	@Test
	public void testeLoginVendedor() throws IOException, InterruptedException {
		BancoServidorRedeLocal banco = new BancoServidorRedeLocal(TipoBanco.DERBY, 5);

		CountDownLatch latch = new CountDownLatch(1);

		LoginService.logar(banco, "funcionario", "1234", new ListenerConsultaComResposta<Boolean>() {

			@Override
			public void resposta(List<Boolean> registros) {
				logado = (boolean) registros.get(0);
				latch.countDown();
			}

			@Override
			public void erro(Exception e) {
				latch.countDown();
			}
		});

		latch.await();

		assertTrue(logado);

		assertTrue(LoginService.estaLogado());

		assertTrue(LoginService.getFuncionarioLogado().getTipoAcesso() == TipoAcesso.VENDEDOR);

		ServidorRedeLocal.fecharConexoes();
	}

}
