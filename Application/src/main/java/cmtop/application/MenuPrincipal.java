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
		faixa.setStyle("-fx-background-color: #333333; -fx-pref-width: 40px; -fx-pref-height: 100%");
		linha.getChildren().add(faixa);
		
		GridPane menu = new GridPane();
		menu.setAlignment(Pos.TOP_CENTER);
		menu.setHgap(10);
		menu.setVgap(10);
		
		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");
		menu.add(nome, 0, 0,3,1);
		
		Button[] botoes = {new Button("Funcao 1"), new Button("Funcao 2"), new Button("Funcao 3"), new Button("Funcao 4"), new Button("Funcao 5"), new Button("Funcao 6")};
		
		for(int x = 0; x < 6; x++) {
			botoes[x].setStyle("-fx-pref-width: 150px; -fx-pref-height: 75px");
		}
		
		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		menu.add(botoes[3], 0, 2);
		menu.add(botoes[4], 1, 2);
		menu.add(botoes[5], 2, 2);
		
		
		linha.getChildren().add(menu);
		
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
