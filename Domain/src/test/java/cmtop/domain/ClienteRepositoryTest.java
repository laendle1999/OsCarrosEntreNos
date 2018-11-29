package cmtop.domain;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import cmtop.application.service.LoginService;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.service.ServidorRedeLocal;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import cmtop.domain.repository.ClienteRepository;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
import cmtop.persistence.valueobject.ListenerConsulta;

public class ClienteRepositoryTest {

	ListenerConsulta lc = null;

	ClienteRepository cr = null;

	Cliente cliente;

	@Before

	public void criarCliente() throws IOException {

		BancoServidorRedeLocal banco = new BancoServidorRedeLocal(TipoBanco.DERBY, 5);

		cliente = new Cliente(1232, "Eduardo", "1234", "1234", "Rua Maria de Lurdes", "989893832", "989342834",
				01012000);
		

		cr.cadastrarCliente(cliente, lc);
	}

	@Test

	public void inserirCaractereErrado() throws IOException {

		cr.consultarClientePorRg("1234", 100, new ListenerConsultaComResposta<Cliente>() {

			@Override
			public void resposta(List<Cliente> registros) {
				if (registros.isEmpty()) {
					System.err.println("Nenhum cliente obtido");
					return;
				}
				Cliente cliente = registros.get(0);
				String rg = cliente.getRg();
				assert rg.equals("1234");
			}

			@Override
			public void erro(Exception e) {
				e.printStackTrace();
			}
		});

		// system.out.println();

		// 'carro.setPlaca("x"); // Aqui estou testando se o setPlaca está funcionando
		// Assert.assertNotNull("Não pode aceitar nulo!", carro.getPlaca()); // Não pode
		// ser nulo
		// 'Assert.assertEquals("x", carro.getPlaca());

	}

}
