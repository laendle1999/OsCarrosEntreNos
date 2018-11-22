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

public class TelaDeVenda extends TelaBase{

	private TableView tabela = new TableView();
	private String carro, cliente, pagamento;
	
	public TelaDeVenda() {
		super("AutoManager", 700, 600);
		
		VBox conteudo = new VBox();
		
		GridPane botoesCima = new GridPane();
		botoesCima.setAlignment(Pos.CENTER);
		botoesCima.setStyle("-fx-background-fill: black, white ;\n" + 
				"-fx-background-insets: 0, 1 ;");
		botoesCima.setHgap(10);
		botoesCima.setVgap(10);
		
		GridPane botoesEmbaixo = new GridPane();
		botoesEmbaixo.setAlignment(Pos.CENTER);
		botoesEmbaixo.setStyle("-fx-background-fill: black, white ;\n" + 
				"-fx-background-insets: 0, 1 ;");
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
		Button[] botao = {new Button("Carro"),new Button("Cliente"),new Button("Pagamento"),new Button("Nota Fiscal"),new Button("Finalizar Venda")};
		
		tabela.setLayoutY(300);
		
		conteudo.getChildren().add(nome);
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(botoesCima);
		conteudo.getChildren().add(tabela);
		conteudo.getChildren().add(botoesEmbaixo);
	
		botoesCima.add(botao[0], 0, 0);
		botoesCima.add(botao[1], 1, 0);
		botoesCima.add(botao[2], 2, 0);
		
		botoesEmbaixo.add(botao[3], 0, 0);
		botoesEmbaixo.add(botao[4], 1, 0);
		
		definirConteudo(conteudo);
		
	}
	
	//Criar classes de modelo e ver como colocar as informacoes aqui
}
