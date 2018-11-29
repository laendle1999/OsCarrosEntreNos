package cmtop.domain;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.Cliente;
import cmtop.domain.repository.ClienteRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class ClienteRepositoryTest {

	ListenerConsulta lc = null;

	ClienteRepository cr = null;

	Cliente cliente;

	@Test

	public void criarCliente() throws IOException {

		Banco banco = new BancoServidorRedeLocal(TipoBanco.DERBY, 5);

		cr = new ClienteRepository(banco);

		cliente = new Cliente(1232, "Eduardo", "1234", "1234", "Rua Maria de Lurdes", "989893832", "989342834",
				01012000);

		System.out.println("cadastrando");
		cr.cadastrarCliente(cliente, new ListenerConsulta() {
			
			@Override
			public void sucesso(int resultadosAfetados, List<Long> chaves) {
				System.out.println("cadastrado");
				cr.consultarClientePorRg("1234", 100, new ListenerConsultaComResposta<Cliente>() {

					@Override
					public void resposta(List<Cliente> registros) {
						if (registros.isEmpty()) {
							System.err.println("Nenhum cliente obtido");
							return;
						}
						Cliente cliente = registros.get(0);
						String rg = cliente.getRg();
						assertEquals(rg, "1234");
						System.out.println(cliente.getNome());
					}

					@Override
					public void erro(Exception e) {
						System.out.println("a");
						e.printStackTrace();
					}
				});
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
