package cmtop.application;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import cmtop.application.service.LoginService;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Banco.TipoConexao;

public class LoginServiceTest {

	@Test
	public void testeLoginGerente() throws IOException {
		Banco banco = new Banco(TipoConexao.SERVIDOR_DERBY);

		boolean logado = LoginService.logar(banco, "gerente", "1234");

		assertTrue(logado);

		assertTrue(LoginService.estaLogado());

		assertTrue(LoginService.getFuncionarioLogado().getTipoAcesso() == TipoAcesso.GERENTE);
	}

	@Test
	public void testeLoginVendedor() throws IOException {
		Banco banco = new Banco(TipoConexao.SERVIDOR_DERBY);

		boolean logado = LoginService.logar(banco, "funcionario", "1234");

		assertTrue(logado);

		assertTrue(LoginService.estaLogado());

		assertTrue(LoginService.getFuncionarioLogado().getTipoAcesso() == TipoAcesso.VENDEDOR);
	}

}
