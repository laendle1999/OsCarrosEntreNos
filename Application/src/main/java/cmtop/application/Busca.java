package cmtop.application;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Busca extends TelaBase{

	private TableView tabela = new TableView();
	
	public Busca() {
		super("AutoManager", 700, 600);
		
		VBox conteudo = new VBox();
		
		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + 
				"-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);
		
		Text secao = new Text("Buscar Algo");
		secao.setTextAlignment(TextAlignment.LEFT);
		
		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");
		
		Text info = new Text("Informações");
		TextField busca = new TextField();
		Button botao = new Button("Buscar");
		
		conteudo.getChildren().add(nome);
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(menu);
		
		menu.add(info, 0, 0);
		menu.add(busca, 1, 0);
		menu.add(botao, 2, 0);
		
		definirConteudo(conteudo);
		
	}
	
	//Criar classes de modelo e ver como colocar as informacoes aqui
}
