package cmtop.application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cmtop.application.service.ComponentesServices;
import cmtop.persistence.entity.Banco;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuPrincipal extends TelaBase {

	private Banco banco;

	public MenuPrincipal(Banco banco) {
		super("AutoManager - Menu principal", 700, 600);
		this.banco = banco;

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

		Button[] botoes = { new Button("Nova Venda"), new Button("Clientes"), new Button("Tabela FIPE"),
				new Button("Buscar Venda"), new Button("Gerencia "), new Button("Funcao 6") };

		for (int x = 0; x < 6; x++) {
			botoes[x].setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;\n"
					+ "    -fx-padding: 3 3 3 3;\n" + "    -fx-text-fill: #242d35;\n"
					+ "    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}

		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		menu.add(botoes[3], 0, 2);
		menu.add(botoes[4], 1, 2);
		//menu.add(botoes[5], 2, 2);

		botoes[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new TelaDeVenda().show();
			}
		});
		
		botoes[1].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new MenuGerenciamentoCliente().show();
			}
		});
		
		botoes[2].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
		            Desktop.getDesktop().browse(new URI("http://veiculos.fipe.org.br"));
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        } catch (URISyntaxException e1) {
		            e1.printStackTrace();
		        }
		        }
		    
			
		});
		
		botoes[3].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new MenuGerenciamentoCliente().show();
			}
		});
		
		botoes[4].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new MenuGerencia().show();
			}
		});

		definirConteudo(conteudo);
	}

}
