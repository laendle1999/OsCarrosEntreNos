package cmtop.application;

import cmtop.application.service.ComponentesServices;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuGerenciamentoCliente extends TelaBase {

	public MenuGerenciamentoCliente() {
		super("AutoManager - Menu principal", 700, 600);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);

		Text vendedor = new Text("Ola [VENDEDOR]");
		vendedor.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER);
		menu.add(vendedor, 2, 0);

		Button[] botoes = { new Button("Cadastrar"), new Button("Alterar"), new Button("Buscar Vendas"),
				new Button("Buscar Venda"), new Button("Gerencia "), new Button("Funcao 6") };

		for (int x = 0; x < 6; x++) {
			botoes[x].setStyle("    -fx-background-color: \r\n" + "        rgba(0,0,0,0.08),\r\n"
					+ "        linear-gradient(#9a9a9a, #909090),\r\n"
					+ "        linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n"
					+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
					+ "    -fx-padding: 3 30 3 30;\r\n" + "    -fx-text-fill: #242d35;\r\n"
					+ "    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}

		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		//menu.add(botoes[3], 0, 2);
		//menu.add(botoes[4], 1, 2);
		//menu.add(botoes[5], 2, 2);

		botoes[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new CadastrarCliente().show();
			}
		});

		definirConteudo(conteudo);
	}

}
