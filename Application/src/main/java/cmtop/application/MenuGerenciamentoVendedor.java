package cmtop.application;

import java.io.IOException;
import java.util.List;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.busca.BuscaComEdicao.ListenerAlteracoes;
import cmtop.busca.BuscaVendedor;
import cmtop.busca.BuscarVendedorComEdicao;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuGerenciamentoVendedor extends TelaBase {

	String log, pass;

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public MenuGerenciamentoVendedor(Banco banco) {
		super("AutoManager - Gerenciamento de vendedores", 700, 600, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);

		String nomeVendedor = LoginService.getFuncionarioLogado().getNome().split("\\s")[0];
		Text textVendedor = new Text("Olá " + nomeVendedor);
		textVendedor.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(400, 177));
		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER);
		menu.add(textVendedor, 2, 0);

		Button[] botoes = { new Button("Cadastrar"), new Button("Alterar"), new Button("Nova Senha"),
				new Button("Buscar Venda"), new Button("Gerencia "), new Button("Funcao 6") };

		for (int x = 0; x < 6; x++) {
			botoes[x].setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
					+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;"
					+ "    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}

		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		// menu.add(botoes[3], 0, 2);
		// menu.add(botoes[4], 1, 2);
		// menu.add(botoes[5], 2, 2);

		botoes[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new CadastrarVendedor(banco).show();
			}
		});

		botoes[1].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new BuscarVendedorComEdicao(banco, new ListenerAlteracoes<Vendedor>() {
					@Override
					public boolean aceitarMudanca(Vendedor objetoBuscado, String campo, String valorNovo) {
						return true; // << retornar se valorNovo é valido ou não
					}
				}, VendedorSendoApagado -> {
					/// apagar item do banco
				}).show();
			}
		});
		botoes[2].setOnMouseClicked(event -> {
			new BuscaVendedor(banco, vendedorEscolhido -> {
				if (vendedorEscolhido == null)
					return;
				ComponentesServices.mostrarEntradaSenha("Digite uma nova senha para o vendedor", senha -> {
					if (senha == null)
						return;
					VendedorRepository repository;
					try {
						repository = new VendedorRepository(banco);
					} catch (IOException e1) {
						ComponentesServices.mostrarInformacao("Falha ao se conectar ao banco");
						e1.printStackTrace();
						return;
					}

					repository.alterarSenhaVendedor(vendedorEscolhido, senha, new ListenerConsulta() {
						@Override
						public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
							ComponentesServices.mostrarInformacao("Senha alterada com sucesso");
						}

						@Override
						public void erro(Exception e) {
							ComponentesServices.mostrarInformacao("Falha ao alterar senha");
							e.printStackTrace();
						}
					});

				});
			}).show();
		});

		definirConteudo(conteudo);
	}

}
