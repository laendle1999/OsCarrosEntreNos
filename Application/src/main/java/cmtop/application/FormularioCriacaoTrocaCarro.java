package cmtop.application;

import java.util.Date;
import java.util.function.Consumer;

import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.service.DateService;
import cmtop.persistence.entity.Banco;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class FormularioCriacaoTrocaCarro extends TelaBase {

	public FormularioCriacaoTrocaCarro(Banco banco, Consumer<TrocaCarro> listener) {
		super("AutoManager - Recebimento de carro como pagamento", 700, 800, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Recebimento de carro como pagamento");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(), new TextField(), new TextField(), new TextField(), new TextField() };
		Text[] labels = { new Text("Modelo do carro"), new Text("Marca do carro"), new Text("Ano do carro"),
				new Text("Placa"), new Text("Cor"), new Text("Local da venda"), new Text("Valor do carro") };
		Button btn = new Button("Confirmar");

		for (int x = 0; x < labels.length; x++) {
			// campos[x].setStyle(
			// " -fx-background-color: \r\n" +
			// " rgba(0,0,0,0.08),\r\n" +
			// " linear-gradient(#9a9a9a, #909090),\r\n" +
			// " linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n" +
			// " -fx-background-insets: 0 0 -1 0,0,1;\r\n" +
			// " -fx-background-radius: 5,5,4;\r\n" +
			// " -fx-padding: 3 30 3 30;\r\n" +
			// " -fx-text-fill: #242d35;\r\n" +
			// " -fx-font-size: 14px;");
			menu.add(labels[x], 0, x + 1);
			menu.add(campos[x], 1, x + 1);
		}

		menu.setTranslateY(15);

		Button cancelar = new Button("Cancelar");
		cancelar.setOnAction(event -> {
			listener.accept(null);
			close();
		});

		GridPane linha = new GridPane();
		linha.setAlignment(Pos.CENTER);
		linha.add(cancelar, 0, 0);
		linha.add(btn, 2, 0);
		linha.setTranslateY(30);

		conteudo.getChildren().add(linha);
		btn.setOnAction(event -> {
			String placa = campos[3].getText();
			String modelo = campos[0].getText();
			String marca = campos[1].getText();
			String cor = campos[4].getText();
			String local = campos[5].getText();

			int ano;
			try {
				ano = Integer.parseInt(campos[2].getText());
			} catch (NumberFormatException e) {
				ComponentesServices.mostrarAlerta("O ano deve ser um número");
				return;
			}

			float valorCarro;
			try {
				valorCarro = Float.parseFloat(campos[6].getText());
			} catch (NumberFormatException e) {
				ComponentesServices.mostrarAlerta("O valor deve ser um número");
				return;
			}

			long dataEntradaAgora = DateService.converterDataEmTimestamp(new Date());

			TrocaCarro trocaCarro = new TrocaCarro(-1, placa, modelo, marca, cor, local, ano, valorCarro,
					dataEntradaAgora, -1);

			listener.accept(trocaCarro);

			close();
		});

		definirConteudo(conteudo);
	}

}
