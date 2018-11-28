package cmtop.application;

import cmtop.application.service.ComponentesServices;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GerarRelatorio extends TelaBase {

	public GerarRelatorio() {
		super("AutoManager - Gerar Relatório", 600, 500);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Gerar Relatório");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(),new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("De"), new Text("Até"), new Text("Ano"), new Text("Placa"),
				new Text("RENAVAN"), new Text("Cor"), new Text("Adicionais"), new Text("Custo"), new Text("Local da Compra"),
				new Text("Nome do Fornecedor"), new Text("Data da Compra") };
		Button[] btn = {new Button("Buscar"),new Button("Gerar PDF"),new Button("Imprimir")};

		for (int x = 0; x < 3; x++) {
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
			//menu.add(labels[x], 0, x + 1);
			//menu.add(campos[x], 1, x + 1);
			campos[x].setPrefWidth(30);
			
			btn[x].setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
				+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;"
				+ "    -fx-font-size: 14px;");
		}
		
		menu.add(labels[0],0,1);
		menu.add(campos[0],1,1);
		menu.add(labels[1],2,1);
		menu.add(campos[1],3,1);
		menu.add(btn[0],4,1);
		
		menu.add(btn[1], 0, 3);
		menu.add(btn[2], 3, 3);

		menu.setTranslateY(15);

		

		//conteudo.getChildren().add(btn);
		//btn[1].setTranslateX(300);
		//btn[1].setTranslateY(30);

		definirConteudo(conteudo);
	}

}
