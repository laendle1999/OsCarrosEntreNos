package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.TelaBase;
import cmtop.application.model.ModelGenerico;
import cmtop.application.model.ModelGenerico.Coluna;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class Busca extends TelaBase {

	private ObservableList<ModelGenerico> listaTabela = FXCollections.observableArrayList();

	private TableView<ModelGenerico> tabela;

	private GridPane listaCampos;

	private List<Node> componentesCampos = new ArrayList<>();

	private Campo[] camposBusca;

	public Busca(String nomeObjetoParaBuscar) {
		super("AutoManager", 700, 600);

		VBox conteudo = new VBox();

		Text secao = new Text("Buscar " + nomeObjetoParaBuscar);
		secao.setTextAlignment(TextAlignment.LEFT);

		Text titulo = new Text("Auto Management");
		titulo.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		titulo.setTextAlignment(TextAlignment.CENTER);
		titulo.setStyle("-fx-border-color:red;");

		tabela = new TableView<>(listaTabela);
		atualizarColunasTabela();

		Button botao = new Button("Buscar");
		botao.setOnAction(event -> {
			listaTabela.clear();

			// Buscando ...
			try {
				int maximoResultados = 300;
				buscar(obterValoresCampos(), maximoResultados, resutadoBusca -> {
					listaTabela.addAll(resutadoBusca);
					atualizarColunasTabela();
				});
			} catch (IOException e) {
				// Falha ao buscar
				System.err.println("Falha ao buscar " + nomeObjetoParaBuscar);
				e.printStackTrace();
			}
		});

		listaCampos = new GridPane();
		listaCampos.setAlignment(Pos.CENTER);
		listaCampos.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		listaCampos.setHgap(10);
		listaCampos.setVgap(10);

		conteudo.getChildren().add(titulo);
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(listaCampos);

		HBox hBoxBotao = new HBox(botao);
		hBoxBotao.setAlignment(Pos.CENTER);

		conteudo.getChildren().add(hBoxBotao);
		conteudo.getChildren().add(tabela);

		definirConteudo(conteudo);
	}

	private ValoresCamposBusca obterValoresCampos() {
		ValoresCamposBusca valoresCamposBusca = new ValoresCamposBusca();

		for (int i = 0; i < componentesCampos.size(); i++) {
			Node componente = componentesCampos.get(i);

			String nome = camposBusca[i].getNome();
			String valor = null;

			if (componente instanceof TextField) {
				valor = ((TextField) componente).getText();
			}

			valoresCamposBusca.adicionarCampo(nome, valor);
		}
		return valoresCamposBusca;
	}

	public void definirCamposBusca(Campo... campos) {
		this.camposBusca = campos;

		componentesCampos.clear();

		for (int i = 0; i < camposBusca.length; i++) {
			Campo campo = camposBusca[i];

			if (campo.getTipoCampo() == TipoCampo.TEXTO) {
				TextField field = new TextField();
				field.setText((String) campo.getValorInicial());
				componentesCampos.add(field);
			}
		}

		listaCampos.getChildren().clear();
		for (int i = 0; i < componentesCampos.size(); i++) {
			String nome = camposBusca[i].getNome();
			Node componente = componentesCampos.get(i);

			Text info = new Text(nome);

			listaCampos.add(info, 0, i);
			listaCampos.add(componente, 1, i);
		}
	}

	private void atualizarColunasTabela() {
		List<String> colunasTabela = obterColunasDoModelo();

		tabela.getColumns().clear();

		for (int i = 0; i < colunasTabela.size(); i++) {
			final int _i = i;
			TableColumn<ModelGenerico, String> coluna = new TableColumn<>(colunasTabela.get(_i));
			coluna.setCellValueFactory(celula -> {
				return celula.getValue().getColunas().get(_i).getValor();
			});

			tabela.getColumns().add(coluna);
		}
	}

	private List<String> obterColunasDoModelo() {
		if (listaTabela.isEmpty()) {
			return new ArrayList<>();
		} else {
			List<Coluna> colunas = listaTabela.get(0).getColunas();
			List<String> resultado = new ArrayList<>();
			for (int i = 0; i < colunas.size(); i++) {
				resultado.add(colunas.get(i).getNome());
			}
			return resultado;
		}
	}

	protected abstract void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callback) throws IOException;

}
