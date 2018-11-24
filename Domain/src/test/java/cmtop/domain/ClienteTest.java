package cmtop.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.Cliente;

public class ClienteTest {

	private Cliente cliente;

	@Before
	public void criarCliente() {
		this.cliente = new Cliente(-1, null, null, null, null, null, null, null);
	}

	@Test
	public void testeNome() {

		cliente.setNome("Eduardo");
		Assert.assertEquals("Eduardo", cliente.getNome());
	}

	@Test
	public void testeRG() {

		cliente.setRg("1234");
		Assert.assertEquals("1234", cliente.getRg());
	}

	@Test
	public void testeCPF() {

		cliente.setCpf("6543");
		Assert.assertEquals("6543", cliente.getCpf());
	}

	@Test
	public void testeEndereco() {

		cliente.setEndereco("Rua dos Bobos numero 0");
		Assert.assertEquals("Rua dos Bobos numero 0", cliente.getEndereco());
	}

	@Test
	public void testeTelefone() {

		cliente.setTelefone1("38447574");
		;
		Assert.assertEquals("38447574", cliente.getTelefone1());
	}

	@Test
	public void testeData() {
		cliente.setDataNascimento("12-03-04");
		Assert.assertEquals("12-03-04", cliente.getDataNascimento());
	}

}
