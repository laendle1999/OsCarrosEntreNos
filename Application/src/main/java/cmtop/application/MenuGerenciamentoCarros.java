package cmtop.application;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.busca.BuscaCarroComEdicao;
import cmtop.busca.BuscaTrocaCarro;
import cmtop.busca.BuscarCarroPorTempo;
import cmtop.persistence.entity.Banco;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuGerenciamentoCarros extends TelaBase {

	public MenuGerenciamentoCarros(Banco banco) {
		super("AutoManager - Gerenciamento de carros", 700, 600, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);

		String nomeVendedor = LoginService.getFuncionarioLogado().getNome().split("\\s")[0];
		Text vendedor = new Text("Olá " + nomeVendedor);
		vendedor.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(400, 177));
		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER);
		menu.add(vendedor, 2, 0);

		Button[] botoes = { new Button("Compra"), new Button("Manutenção"), new Button("Aprovar trocas"),
				new Button("Tempo Parado"), new Button("Consultar e editar"), new Button("Funcao 6") };

		for (int x = 0; x < botoes.length; x++) {
			botoes[x].setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
					+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;"
					+ "    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}

		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		menu.add(botoes[3], 0, 2);
		menu.add(botoes[4], 1, 2);
		// menu.add(botoes[5], 2, 2);

		botoes[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new CadastrarCompra(banco).show();
			}
		});

		botoes[1].setOnMouseClicked(event -> new CadastrarManutencao(banco).show());
		botoes[2].setOnMouseClicked(event -> {
			new BuscaTrocaCarro(banco, trocaCarro -> {
				if (trocaCarro == null)
					return;

				ComponentesServices.mostrarConfirmacao("Deseja aprovar esta troca e inserir o carro no banco de dados?",
						resultado -> {
							if (resultado == null || resultado == false)
								return;
							new CadastrarCarroDeTroca(banco, trocaCarro).show();
						});
			}).show();
		});

		botoes[3].setOnMouseClicked(event -> new BuscarCarroPorTempo(banco, null).show());

		botoes[4].setOnAction(
				event -> new BuscaCarroComEdicao(banco, "Consultar carros").show());

		definirConteudo(conteudo);
	}

}
