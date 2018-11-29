package cmtop.application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class TelaBase extends Stage {

	private VBox parteDireita = new VBox();
	
	public TelaBase(String title, double width, double height) {
		Group raiz = new Group();
		
		HBox linha = new HBox();
		linha.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
		linha.setStyle("-fx-background-color: #fefefe;");
		
		HBox faixa = new HBox(0);
		VBox.setVgrow(faixa, Priority.ALWAYS);
		faixa.setStyle("-fx-background-color: #333333; -fx-pref-width: 40px; -fx-pref-height: 100%; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		linha.getChildren().add(faixa);
		
		parteDireita.setAlignment(Pos.CENTER);
		
		Button btn = new Button("Voltar");
		linha.getChildren().add(btn);
		btn.setTranslateX(10);
		btn.setTranslateY(-(height/2)+30);
		
		linha.getChildren().add(parteDireita);
		linha.setAlignment(Pos.CENTER_LEFT);
		parteDireita.setTranslateX(80);
		raiz.getChildren().add(linha);
		
		BorderPane borderPane = new BorderPane();
		Scene cena = new Scene(borderPane);
		cena.setFill(Color.MEDIUMSEAGREEN);
		borderPane.setCenter(linha);
		
		setScene(cena);

		setTitle(title);
		setWidth(width);
		setHeight(height);
		
		centerOnScreen();
		
		btn.setOnMouseClicked(event->close());
		
	}
	
	public void definirConteudo(VBox conteudo) {
		this.parteDireita.getChildren().clear();
		this.parteDireita.getChildren().add(conteudo);
		
		centerOnScreen();
	}

}
