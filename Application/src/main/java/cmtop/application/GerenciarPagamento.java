package cmtop.application;

import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.ValorEntrada;
import cmtop.domain.service.VendaService;
import cmtop.persistence.entity.Banco;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GerenciarPagamento extends TelaBase {

	public GerenciarPagamento(Banco banco, VendaService vendaService) {
		super("AutoManager - Cadastrar Pagamento", 600, 500, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menuVista = new GridPane();
		menuVista.setAlignment(Pos.CENTER);
		menuVista.setStyle(
				"-fx-background-fill: black, white ; -fx-padding: 20px; \n" + "-fx-background-insets: 0, 1 ;");
		menuVista.setHgap(10);
		menuVista.setVgap(10);
		menuVista.setVisible(false);

		GridPane menuFinanciamento = new GridPane();
		menuFinanciamento.setAlignment(Pos.CENTER);
		menuFinanciamento
				.setStyle("-fx-background-fill: black, white ; -fx-padding: 20px;\n" + "-fx-background-insets: 0, 1 ;");
		menuFinanciamento.setHgap(10);
		menuFinanciamento.setVgap(10);
		menuFinanciamento.setVisible(false);

		GridPane menuCarro = new GridPane();
		menuCarro.setAlignment(Pos.CENTER);
		menuCarro
				.setStyle("-fx-background-fill: black, white ; -fx-padding: 20px;\n" + "-fx-background-insets: 0, 1 ;");
		menuCarro.setHgap(10);
		menuCarro.setVgap(10);
		menuCarro.setVisible(false);

		Text secao = new Text("Cadastro Pagamento");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 200));
		conteudo.getChildren().add(new HBox(secao, new Text("  	 R$" + vendaService)));

		CheckBox[] cb = { new CheckBox("Valor a Vista"), new CheckBox("Financiamento"), new CheckBox("Carro") };
		conteudo.getChildren().add(new HBox(cb[0], cb[1], cb[2]));

		conteudo.getChildren().add(menuVista);
		conteudo.getChildren().add(menuFinanciamento);
		conteudo.getChildren().add(menuCarro);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		cb[0].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				cb[0].setSelected(newValue);
				menuVista.setVisible(newValue);

			}
		});

		cb[1].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				cb[1].setSelected(newValue);
				menuFinanciamento.setVisible(newValue);

			}
		});

		cb[2].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				cb[2].setSelected(newValue);
				menuCarro.setVisible(newValue);

			}
		});

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Valor à Vista"), new Text("Valor Financiado"), new Text("Banco"), new Text("Carro"),
				new Text("Campo 5"), new Text("Campo 6") };
		Button[] btn = { new Button("Confirmar"), new Button("Cadastrar Carro") };

		menuVista.add(labels[0], 0, 0);
		menuVista.add(campos[0], 1, 0);

		menuFinanciamento.add(labels[1], 0, 0);
		menuFinanciamento.add(campos[1], 1, 0);
		menuFinanciamento.add(labels[2], 0, 1);
		menuFinanciamento.add(campos[2], 1, 1);

		menuCarro.add(labels[3], 0, 0);
		menuCarro.add(btn[1], 1, 0);

		btn[1].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new CadastrarCompra(banco).show();
			}
		});

		// menu.setTranslateY(15);

		btn[0].setStyle("    -fx-background-color: \r\n" + "        rgba(0,0,0,0.08),\r\n"
				+ "        linear-gradient(#9a9a9a, #909090),\r\n"
				+ "        linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 3 30 3 30;\r\n" + "    -fx-text-fill: #242d35;\r\n" + "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn[0]);
		btn[0].setTranslateX(300);
		btn[0].setTranslateY(30);

		btn[0].setOnAction(event -> {
			// valor a vista
			vendaService.limparValoresEntrada();
			if (cb[0].isSelected()) {
				try {
					float valor = Float.parseFloat(campos[0].getText());
					vendaService.adicionarValorEntrada(new ValorEntrada("", valor));
				} catch (NumberFormatException e) {
					ComponentesServices.mostrarAlerta("Valor inválido, digite um número");
				}
			}

			// TODO financiamento

			// TODO carro troca
		});

		definirConteudo(conteudo);

	}

}
