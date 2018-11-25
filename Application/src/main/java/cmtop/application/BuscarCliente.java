package cmtop.application;

import cmtop.application.model.CarroModel;
import cmtop.application.model.ClienteModel;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
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

public class BuscarCliente extends TelaBase {

	private ObservableList<ClienteModel> listaTabela = FXCollections.observableArrayList();

	public BuscarCliente() {
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

	private TableView<ClienteModel> construirTabela() {
		TableView<ClienteModel> tabela = new TableView<>(listaTabela);

		TableColumn<ClienteModel, String> coluna1 = new TableColumn<>("Nome");
		coluna1.setCellValueFactory(celula -> {
			return celula.getValue().getNome();
		});

		TableColumn<ClienteModel, String> coluna2 = new TableColumn<>("CPF");
		coluna2.setCellValueFactory(celula -> {
			return celula.getValue().getCpf();
		});
		TableColumn<ClienteModel, String> coluna3 = new TableColumn<>("RG");
		coluna3.setCellValueFactory(celula -> {
			return celula.getValue().getRg();
		});

		TableColumn<ClienteModel, String> coluna4 = new TableColumn<>("Endereco");
		coluna4.setCellValueFactory(celula -> {
			return celula.getValue().getEndereco();
		});
		TableColumn<ClienteModel, String> coluna5 = new TableColumn<>("Telefone");
		coluna5.setCellValueFactory(celula -> {
			return celula.getValue().getTelefone1();
		});

		

		tabela.getColumns().add(coluna1);
		tabela.getColumns().add(coluna2);
		tabela.getColumns().add(coluna3);
		tabela.getColumns().add(coluna4);
		tabela.getColumns().add(coluna5);

		return tabela;
	}

	private void adicionarModelosExemplo() {
		listaTabela.add(new ClienteModel(new Cliente(-1, "Marcao", "12345678", "32165498711", "ads", "Ford", "Branco", "")));
		listaTabela.add(new ClienteModel(new Cliente(-1, "Josue", "987654321", "91827364533", "asd", "Fiat", "Roxo", "")));
	}

}
