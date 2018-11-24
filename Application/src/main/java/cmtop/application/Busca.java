package cmtop.application;

import cmtop.application.model.CarroModel;
import cmtop.domain.entity.Carro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Busca extends TelaBase {

	private ObservableList<CarroModel> listaTabela = FXCollections.observableArrayList();

	public Busca() {
		super("AutoManager", 700, 600);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
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
		conteudo.getChildren().add(construirTabela());

		menu.add(info, 0, 0);
		menu.add(busca, 1, 0);
		menu.add(botao, 2, 0);

		definirConteudo(conteudo);

		adicionarModelosExemplo();
	}

	private TableView<CarroModel> construirTabela() {
		TableView<CarroModel> tabela = new TableView<>(listaTabela);

		TableColumn<CarroModel, String> coluna1 = new TableColumn<>("Marca do carro");
		coluna1.setCellValueFactory(celula -> {
			return celula.getValue().getMarca();
		});

		TableColumn<CarroModel, String> coluna2 = new TableColumn<>("Cor do carro");
		coluna2.setCellValueFactory(celula -> {
			return celula.getValue().getCor();
		});

		tabela.getColumns().add(coluna1);
		tabela.getColumns().add(coluna2);

		return tabela;
	}

	private void adicionarModelosExemplo() {
		listaTabela.add(new CarroModel(new Carro(-1, "", "", "", "", "Ford", "Branco", 0, 0, 0, "", null)));
		listaTabela.add(new CarroModel(new Carro(-1, "", "", "", "", "Fiat", "Roxo", 0, 0, 0, "", null)));
	}

}
