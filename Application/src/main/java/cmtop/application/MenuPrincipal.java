package cmtop.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage tela) throws Exception {
		// TODO Auto-generated method stub
		Group raiz = new Group();
		
		
		
		HBox linha = new HBox();
		linha.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
		linha.setStyle("-fx-background-color: #fefefe;");
		
		HBox faixa = new HBox();
		VBox.setVgrow(faixa, Priority.ALWAYS);
		faixa.setStyle("-fx-background-color: #333333; -fx-pref-width: 40px; -fx-pref-height: 100%; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		linha.getChildren().add(faixa);
		
		VBox conteudo = new VBox();
		
		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);
		
		Text vendedor = new Text("Ola [VENDEDOR]");
		vendedor.setTextAlignment(TextAlignment.LEFT);
		
		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");
		
		conteudo.getChildren().add(nome);
		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER);
		menu.add(vendedor, 2, 0);
		//vendedor.setTranslateX(conteudo.getWidth()/2);
		conteudo.setTranslateY(-100);
		
		Button[] botoes = {new Button("Funcao 1"), new Button("Funcao 2"), new Button("Funcao 3"), new Button("Funcao 4"), new Button("Funcao 5"), new Button("Funcao 6")};
		
		for(int x = 0; x < 6; x++) {
			botoes[x].setStyle(
					"    -fx-background-color: \r\n" + 
					"        rgba(0,0,0,0.08),\r\n" + 
					"        linear-gradient(#9a9a9a, #909090),\r\n" + 
					"        linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n" + 
					"    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + 
					"    -fx-background-radius: 5,5,4;\r\n" + 
					"    -fx-padding: 3 30 3 30;\r\n" + 
					"    -fx-text-fill: #242d35;\r\n" + 
					"    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}
		
		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		menu.add(botoes[3], 0, 2);
		menu.add(botoes[4], 1, 2);
		menu.add(botoes[5], 2, 2);
		
		
		linha.getChildren().add(conteudo);
		linha.setAlignment(Pos.CENTER);
		faixa.setTranslateX(-95);
		raiz.getChildren().add(linha);
		
		BorderPane borderPane = new BorderPane();
		
		Scene cena = new Scene(borderPane);
		
		cena.setFill(Color.MEDIUMSEAGREEN);
		
		borderPane.setCenter(linha);
		
		tela.setTitle("AutoManager");
		tela.setScene(cena);
		tela.centerOnScreen();
		tela.show();
		
		tela.setWidth(700);
		tela.setHeight(600);
		
	}

}
