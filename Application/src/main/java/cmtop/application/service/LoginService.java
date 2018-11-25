package cmtop.application.service;

import java.io.IOException;

import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;

public class LoginService {

	private static Vendedor funcionarioLogado;

	public static boolean logar(Banco banco, String login, String senha) throws IOException {
		VendedorRepository vendedorRepository = new VendedorRepository(banco);
		Vendedor vendedor = vendedorRepository.obterVendedorPorLoginSenha(login, senha);

		if (vendedor == null) {
			return false;
		}

		funcionarioLogado = vendedor;
		return true;
	}

	public static boolean estaLogado() {
		return funcionarioLogado != null;
	}

	public static void deslogar() {
		funcionarioLogado = null;
	}

	public static Vendedor getFuncionarioLogado() {
		return funcionarioLogado;
	}

}
