package cmtop.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author marcelo
 *
 */
public class TelaBase extends Application {

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
		
		
		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");
		
		conteudo.getChildren().add(nome);
		conteudo.setAlignment(Pos.CENTER);
		//vendedor.setTranslateX(conteudo.getWidth()/2);
		conteudo.setTranslateY(-100);
		
		
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
