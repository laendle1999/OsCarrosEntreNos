package cmtop.application;

import cmtop.application.model.TrocaCarroModel;
import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.Financiamento;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.entity.ValorEntrada;
import cmtop.domain.service.VendaService;
import cmtop.persistence.entity.Banco;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private TrocaCarro trocaCarro;
	private VendaService vendaService;
	private CheckBox[] checkBoxes;
	private TextField[] campos;
	private HBox valorEntrada = new HBox();
	private Text valorPagoLabel;

	public GerenciarPagamento(Banco banco, VendaService vendaService) {
		super("AutoManager - Gerenciar pagamento", 650, 600, TipoBotaoVoltar.VOLTAR);
		this.vendaService = vendaService;

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

		Text secao = new Text("Gerenciar pagamento");
		secao.setTextAlignment(TextAlignment.LEFT);

		valorPagoLabel = new Text("");
		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 200));
		try {
			conteudo.getChildren().add(new HBox(secao, new Text("  	 R$" + vendaService.getCarro().getValorVenda())));
		} catch (NullPointerException e) {
			// TODO
		}
		conteudo.getChildren().add(valorEntrada);
		valorEntrada.getChildren().add(valorPagoLabel);

		checkBoxes = new CheckBox[] { new CheckBox("Valor a Vista"), new CheckBox("Financiamento"),
				new CheckBox("Carro") };
		conteudo.getChildren().add(new HBox(checkBoxes[0], checkBoxes[1], checkBoxes[2]));

		conteudo.getChildren().add(menuVista);
		conteudo.getChildren().add(menuFinanciamento);
		conteudo.getChildren().add(menuCarro);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		checkBoxes[0].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				checkBoxes[0].setSelected(newValue);
				menuVista.setVisible(newValue);

			}
		});

		checkBoxes[1].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				checkBoxes[1].setSelected(newValue);
				menuFinanciamento.setVisible(newValue);

			}
		});

		checkBoxes[2].selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				checkBoxes[2].setSelected(newValue);
				menuCarro.setVisible(newValue);

			}
		});

		campos = new TextField[] { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Valor à Vista"), new Text("Valor Financiado"), new Text("Banco"), new Text("Carro"),
				new Text("Campo 5"), new Text("Campo 6") };
		Button[] btn = { new Button("Confirmar"), new Button("Carro recebido em troca") };

		menuVista.add(labels[0], 0, 0);
		menuVista.add(campos[0], 1, 0);

		menuFinanciamento.add(labels[1], 0, 0);
		menuFinanciamento.add(campos[1], 1, 0);
		menuFinanciamento.add(labels[2], 0, 1);
		menuFinanciamento.add(campos[2], 1, 1);

		if (!vendaService.getValoresEntrada().isEmpty()) {
			checkBoxes[0].setSelected(true);
			ValorEntrada valorEntrada = vendaService.getValoresEntrada().get(0);
			campos[0].setText(valorEntrada.getValor() + "");
		}

		if (!vendaService.getFinanciamentos().isEmpty()) {
			checkBoxes[1].setSelected(true);
			Financiamento financiamento = vendaService.getFinanciamentos().get(0);
			campos[1].setText(financiamento.getValorFinanciado() + "");
			campos[2].setText(financiamento.getBanco() + "");
			campos[1].textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					//setValorPago(Float.parseFloat(newValue));
					
				}
			});
		}

		if (!vendaService.getTrocasCarro().isEmpty()) {
			checkBoxes[2].setSelected(true);
			TrocaCarro trocaCarro = vendaService.getTrocasCarro().get(0);
			campos[3].setText(new TrocaCarroModel(trocaCarro).toString());
		}

		menuCarro.add(labels[3], 0, 0);
		menuCarro.add(btn[1], 1, 0);

		menuCarro.add(campos[3], 0, 1);
		GridPane.setColumnSpan(campos[3], 2);
		campos[3].setEditable(false);

		btn[1].setOnMouseClicked(event -> {
			new FormularioCriacaoTrocaCarro(banco, trocaCarro -> {
				if (trocaCarro == null) {
					return;
				}
				this.trocaCarro = trocaCarro;

				Platform.runLater(() -> atualizarTrocasCarro(trocaCarro));
			}).show();
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
			if (checkBoxes[0].isSelected()) {
				try {
					String valorStr = campos[0].getText();
					float valor = Float.parseFloat(valorStr);
					vendaService.adicionarValorEntrada(new ValorEntrada("", valor));
				} catch (NumberFormatException e) {
					ComponentesServices.mostrarAlerta("Valor inválido, digite um número");
					return;
				}
			}

			// financiamento
			vendaService.limparFinanciamentos();
			if (checkBoxes[1].isSelected()) {
				try {
					float valorFinanciado = Integer.parseInt(campos[1].getText());
					String bancoFinanc = campos[2].getText();
					vendaService.adicionarFinanciamento(new Financiamento(bancoFinanc, valorFinanciado, -1));
				} catch (NumberFormatException e) {
					ComponentesServices.mostrarAlerta("Valor financiado inválido, digite um número");
					return;
				}
			}

			// carro troca
			atualizarTrocasCarro(trocaCarro);
			if(Float.parseFloat(valorPagoLabel.getText()) < (vendaService.getCarro().getValorVenda())){
				ComponentesServices.mostrarErro("Valor faltante de: " + (Float.parseFloat(valorPagoLabel.getText()) - (vendaService.getCarro().getValorVenda())));
			}else {
				ComponentesServices.mostrarAlerta("Pagamento realizado com sucesso, Devolver: " + (Float.parseFloat(valorPagoLabel.getText()) - (vendaService.getCarro().getValorVenda())));
				
				close();
			}
			
		});

		definirConteudo(conteudo);

		atualizarView();
	}

	public void setTrocaCarro(TrocaCarro trocaCarro) {
		this.trocaCarro = trocaCarro;
	}

	private void atualizarTrocasCarro(TrocaCarro trocaCarro) {
		vendaService.limparTrocasCarro();
		if (checkBoxes[2].isSelected()) {
			if (trocaCarro == null)
				return;
			campos[3].setText(new TrocaCarroModel(trocaCarro).toString());
			vendaService.adicionarTrocaCarro(trocaCarro);
			atualizarView();
		}
	}

	private void atualizarView() {
		String valorPago = "R$ ";
		float valor = 0;
		if (!vendaService.getFinanciamentos().isEmpty()) {
			valor += vendaService.getFinanciamentos().get(0).getValorFinanciado();
		}
		if (!vendaService.getTrocasCarro().isEmpty()) {
			valor += vendaService.getTrocasCarro().get(0).getValorCarro();
		}
		if (!vendaService.getValoresEntrada().isEmpty()) {
			valor += vendaService.getValoresEntrada().get(0).getValor();
		}
		valorPago += valor;

		valorPagoLabel.setText(valorPago);
	}

}
