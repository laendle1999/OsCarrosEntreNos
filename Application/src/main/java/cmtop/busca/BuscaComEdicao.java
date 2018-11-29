package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cmtop.application.TelaBase;
import cmtop.application.model.ModelGenerico;
import cmtop.application.model.ModelGenerico.Coluna;
import cmtop.application.service.ComponentesServices;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class BuscaComEdicao<ObjetoBuscado> extends TelaBase {

	public static interface ListenerAlteracoes<T> {
		boolean aceitarMudanca(T objetoBuscado, String campo, String valorNovo);
	}

	private ObservableList<ModelGenerico> listaTabela = FXCollections.observableArrayList();

	private List<ObjetoBuscado> listaOriginal;

	private TableView<ModelGenerico> tabela;

	private GridPane listaCampos;

	private List<Node> componentesCampos = new ArrayList<>();

	private Campo[] camposBusca;

	private ListenerAlteracoes<ObjetoBuscado> listenerAlteracoes;

	private Consumer<ObjetoBuscado> listenerApagar;

	public BuscaComEdicao(String nomeObjetoParaBuscar, String mensagemBotaoEscolher,
			ListenerAlteracoes<ObjetoBuscado> listenerAlteracoes, Consumer<ObjetoBuscado> listenerApagar) {
		super("AutoManager", 700, 600, TipoBotaoVoltar.NENHUM);
		this.listenerAlteracoes = listenerAlteracoes;
		this.listenerApagar = listenerApagar;

		VBox conteudo = new VBox();

		Text secao = new Text("Buscar " + nomeObjetoParaBuscar);
		secao.setTextAlignment(TextAlignment.LEFT);

		tabela = new TableView<>(listaTabela);
		tabela.setEditable(true);
		atualizarColunasTabela();
		adicionarAcaoApagarNaTabela();

		Button botao = new Button("Buscar");
		botao.setOnAction(event -> {
			listaTabela.clear();

			// Buscando ...
			try {
				int maximoResultados = 300;
				buscar(obterValoresCampos(), maximoResultados, resutadoBusca -> {
					listaTabela.addAll(resutadoBusca);
					atualizarColunasTabela();
				}, lista -> listaOriginal = lista);
			} catch (IOException e) {
				// Falha ao buscar
				System.err.println("Falha ao buscar " + nomeObjetoParaBuscar);
				e.printStackTrace();
			}
		});

		listaCampos = new GridPane();
		listaCampos.setAlignment(Pos.CENTER);
		listaCampos.setStyle("-fx-background-fill: black, white; -fx-background-insets: 0, 1;");
		listaCampos.setHgap(10);
		listaCampos.setVgap(10);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 177));
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(listaCampos);

		HBox hBoxBotao = new HBox(botao);
		hBoxBotao.setStyle("-fx-padding: 5px;");
		hBoxBotao.setAlignment(Pos.CENTER);

		conteudo.getChildren().add(hBoxBotao);
		conteudo.getChildren().add(tabela);

		Button botaoEscolher = new Button(mensagemBotaoEscolher);
		botaoEscolher.setOnAction(event -> {
			List<ObjetoBuscado> itens = obterItensSelecionados();

			if (itens.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alerta");
				alert.setHeaderText("Por favor, selecione um item da lista");
				alert.show();
				return;
			}

			if (itens.size() > 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alerta");
				alert.setHeaderText("Por favor, selecione apenas um item da lista");
				alert.show();
				return;
			}

			// callback.accept(itens.get(0));
			close();
		});

		Button botaoCancelar = new Button("Cancelar");
		botaoCancelar.setOnAction(event -> {
			// callback.accept(null);
			close();
		});

		HBox linhaBotoesEscolherCancelar = new HBox();
		linhaBotoesEscolherCancelar.setStyle("-fx-padding: 10px;");
		linhaBotoesEscolherCancelar.setAlignment(Pos.BOTTOM_CENTER);
		// linhaBotoesEscolherCancelar.getChildren().add(botaoCancelar);
		// linhaBotoesEscolherCancelar.getChildren().add(botaoEscolher);
		conteudo.getChildren().add(linhaBotoesEscolherCancelar);

		definirConteudo(conteudo);
	}

	private List<ObjetoBuscado> obterItensSelecionados() {
		List<Integer> indices = tabela.getSelectionModel().getSelectedIndices();

		List<ObjetoBuscado> lista = new ArrayList<>();
		for (Integer indice : indices) {
			lista.add(listaOriginal.get(indice));
		}
		return lista;
	}

	private ValoresCamposBusca obterValoresCampos() {
		ValoresCamposBusca valoresCamposBusca = new ValoresCamposBusca();

		for (int i = 0; i < componentesCampos.size(); i++) {
			Node componente = componentesCampos.get(i);

			String nome = camposBusca[i].getIdentificador();
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

	private void adicionarAcaoApagarNaTabela() {
		if (listenerApagar == null)
			return;
		tabela.setRowFactory(tableView -> {
			final TableRow<ModelGenerico> row = new TableRow<>();
			final ContextMenu contextMenu = new ContextMenu();
			final MenuItem removeMenuItem = new MenuItem("Apagar item");
			removeMenuItem.setOnAction(event -> {
				ObjetoBuscado objetoApagado = obterItensSelecionados().get(0);

				listenerApagar.accept(objetoApagado);

				tabela.getItems().remove(row.getItem());
				listaOriginal.remove(objetoApagado);
			});
			contextMenu.getItems().add(removeMenuItem);
			// Set context menu on row, but use a binding to make it only show for non-empty
			// rows:
			row.contextMenuProperty()
					.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
			return row;
		});
	}

	private void atualizarColunasTabela() {
		Platform.runLater(() -> {
			List<String> nomesColunasTabela = obterNomesColunasDoModelo();

			tabela.getColumns().clear();

			for (int i = 0; i < nomesColunasTabela.size(); i++) {
				final int _i = i;
				TableColumn<ModelGenerico, String> coluna = new TableColumn<>(nomesColunasTabela.get(_i));

				coluna.setCellValueFactory(celula -> {
					return celula.getValue().getColunas().get(_i).getValor();
				});

				if (obterColunasDoModelo().get(_i).alteravel()) {
					coluna.setCellFactory(TextFieldTableCell.forTableColumn());

					coluna.setOnEditCommit(event -> {
						ObjetoBuscado objetoModificado = obterItensSelecionados().get(0);
						String nomeCampoAlterado = nomesColunasTabela.get(_i);
						String valorNovo = event.getNewValue();

						if (!listenerAlteracoes.aceitarMudanca(objetoModificado, nomeCampoAlterado, valorNovo)) {
							// Atualizar coluna voltando ao valor antigo
							coluna.setVisible(false);
							coluna.setVisible(true);
						}
					});
				}

				tabela.getColumns().add(coluna);
			}

			if (nomesColunasTabela.isEmpty()) {
				tabela.getColumns().add(new TableColumn<>(""));
			}
		});
	}

	private List<String> obterNomesColunasDoModelo() {
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

	private List<Coluna> obterColunasDoModelo() {
		if (listaTabela.isEmpty()) {
			return new ArrayList<>();
		} else {
			List<Coluna> colunas = listaTabela.get(0).getColunas();
			return colunas;
		}
	}

	protected abstract void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel,
			Consumer<List<ObjetoBuscado>> callbackListaOriginal) throws IOException;

}
