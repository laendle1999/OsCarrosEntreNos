package cmtop.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class LoginService {

	private static Vendedor funcionarioLogado;

	public static void logar(Banco banco, String login, String senha, ListenerConsultaComResposta<Boolean> listener)
			throws IOException {
		VendedorRepository vendedorRepository = new VendedorRepository(banco);
		vendedorRepository.obterVendedorPorLoginSenha(login, senha, new ListenerConsultaComResposta<Vendedor>() {

			@Override
			public void erro(Exception e) {
				listener.erro(e);
			}

			@Override
			public void resposta(List<Vendedor> registros) {
				Vendedor vendedor = registros.get(0);
				List<Boolean> resposta = new ArrayList<>();
				if (vendedor == null) {
					resposta.add(false);
					listener.resposta(resposta);
					return;
				}

				funcionarioLogado = vendedor;
				resposta.add(true);
				listener.resposta(resposta);
			}
		});
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
