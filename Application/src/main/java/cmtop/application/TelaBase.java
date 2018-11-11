package cmtop.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

/**
 * @author marcelo
 *
 */
public class TelaBase extends Application {
	
	Group raiz = new Group();
	HBox linha = new HBox();
	HBox faixa = new HBox();
	VBox conteudo = new VBox();
	BorderPane borderPane = new BorderPane();
	Scene cena = new Scene(borderPane);
	GridPane menu = new GridPane();
	
	Button[] botoes = new Button[6];
	TextField[] campos = new TextField[20];
	Text[] labels = new Text[20];
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage tela) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		linha.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
		linha.setStyle("-fx-background-color: #fefefe;");
		
		
		VBox.setVgrow(faixa, Priority.ALWAYS);
		faixa.setStyle("-fx-background-color: #333333; -fx-pref-width: 40px; -fx-pref-height: 100%; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		linha.getChildren().add(faixa);
		
		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");
		
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);
		
		//Image logo = new Image("logo.png");
		//ImageView im = new ImageView(logo);
		
		//conteudo.getChildren().add(im);
		conteudo.setAlignment(Pos.CENTER);
		//vendedor.setTranslateX(conteudo.getWidth()/2);
		conteudo.setTranslateY(-100);
		
		
		linha.getChildren().add(conteudo);
		linha.setAlignment(Pos.CENTER);
		faixa.setTranslateX(-330);
		raiz.getChildren().add(linha);
		
		cena.setFill(Color.MEDIUMSEAGREEN);
		
		borderPane.setCenter(linha);
		
		tela.setTitle("AutoManager");
		tela.setScene(cena);
		tela.centerOnScreen();
		tela.show();
		
		tela.setWidth(700);
		tela.setHeight(600);
		
	}

	public void colocarBotao(String text) {
		
		int i = 0;
		int x = 0, y = 0;
		
		while(botoes[i] == null && i < 6) {
			i++;
		}
		if(i == 6) {
		//	throws limiteMaximo; Avisar q nao cabe mais botoes
		}else {
		botoes[i].setText(text);
			
			switch(i){
				case 0:	x = 0; y = 1; break;
				case 1:	x = 1; y = 1; break;
				case 2:	x = 2; y = 1; break;
				case 3:	x = 0; y = 2; break;
				case 4:	x = 1; y = 2; break;
				case 5:	x = 2; y = 2; break;
			}
			
			menu.add(botoes[i], x, y);
			
			botoes[i].setStyle(
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
	}
	
	public int colocarEntrada(String label) {
		int i = 0;
		
		while(labels[i] == null && i < 20) {
			i++;
		}
		if(i == 20) {
		//	throws limiteMaximo; Avisar q nao cabe mais botoes
		}else {
			labels[i].setText(label);
			menu.add(labels[i], 0, i + 1);
			menu.add(campos[i], 1, i + 1);
			
			return i;
		}
		return i;
	}
	
	public String getEntrada(int i) {
		return campos[i].getText();
	}
	
	

}
