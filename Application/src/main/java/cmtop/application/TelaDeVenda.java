package cmtop.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TelaDeVenda extends TelaBase {
	
	
	
	public TelaDeVenda() {
		super("AutoManager", 700, 600);

		VBox conteudo = new VBox();

		GridPane botoesCima = new GridPane();
		botoesCima.setAlignment(Pos.CENTER);
		botoesCima.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		botoesCima.setHgap(10);
		botoesCima.setVgap(10);

		GridPane botoesEmbaixo = new GridPane();
		botoesEmbaixo.setAlignment(Pos.CENTER);
		botoesEmbaixo.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		botoesEmbaixo.setHgap(10);
		botoesEmbaixo.setVgap(10);

		Text secao = new Text("Nova Venda");
		secao.setTextAlignment(TextAlignment.LEFT);

		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");

		Text info = new Text("Informações");
		
		TextField busca = new TextField();
		Button[] botao = { new Button("Carro"), new Button("Cliente"), new Button("Pagamento"),
				new Button("Nota Fiscal"), new Button("Finalizar Venda") };

		HBox interior = new HBox();
		interior.setStyle("-fx-border-color: black; -fx-pref-height: 150px;  -fx-pref-width: 20px;"
				+ "-fx-padding: 5px 0 1px 1px; -fx-border-insets: 20px; -fx-background-insets: 20px");
		
		VBox[] interiorE = {new VBox(),new VBox(),new VBox(),new VBox(),new VBox(),new VBox(),new VBox(),new VBox()};
		
		for(int x = 0; x < 8; x++) {
			interiorE[x].setStyle("-fx-border-color: black; -fx-pref-height: 100%; -fx-pref-width: 150px;"
				+ " -fx-padding:5px; -fx-border-insets: 0px; -fx-background-insets: 20px");
			interiorE[x].setAlignment(Pos.CENTER);
		}
		
		
		VBox interiorD = new VBox();
		interiorD.setStyle("-fx-border-color: black; -fx-pref-height: 100%;  -fx-pref-width: 200px;"
				+ "  -fx-border-insets: 20px; -fx-background-insets: 20px");
		interiorD.setAlignment(Pos.CENTER);
		
		GridPane tabela = new GridPane();
		tabela.setVgap(0.5);
		tabela.setHgap(0.5);
		tabela.add(interiorE[0],0,1);
		tabela.add(interiorE[1],0,2);
		tabela.add(interiorE[2],0,3);
		tabela.add(interiorE[3],0,4);
		tabela.add(interiorE[4],1,1);
		tabela.add(interiorE[5],1,2);
		tabela.add(interiorE[6],1,3);
		tabela.add(interiorE[7],1,4);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now();  
		
		//interiorE.getChildren().add(carroTexto);
		interiorE[0].getChildren().add(new Text("Carro:"));
		interiorE[1].getChildren().add(new Text("Cliente:"));
		interiorE[2].getChildren().add(new Text("Pagamento:"));
		interiorE[3].getChildren().add(new Text("Data da Venda:"));
		interiorE[7].getChildren().add(new Text(dtf.format(now)));
		conteudo.getChildren().add(nome);
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(botoesCima);
		//interior.getChildren().add(interiorE);
		interior.getChildren().add(tabela);
		conteudo.getChildren().add(interior);
		conteudo.getChildren().add(botoesEmbaixo);

		botoesCima.add(botao[0], 0, 0);
		botoesCima.add(botao[1], 1, 0);
		botoesCima.add(botao[2], 2, 0);

		botoesEmbaixo.add(botao[3], 0, 0);
		botoesEmbaixo.add(botao[4], 1, 0);

		definirConteudo(conteudo);

	}

}
