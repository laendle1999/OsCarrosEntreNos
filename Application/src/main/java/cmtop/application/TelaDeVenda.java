package cmtop.application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.busca.BuscaCarro;
import cmtop.busca.BuscaCliente;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
import cmtop.domain.service.VendaService;
import cmtop.persistence.entity.Banco;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TelaDeVenda extends TelaBase {

	private VendaService vendaService;

	private VBox[] celulasTabela;

	private static final String BUTTON_STYLE = "-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
			+ "  -fx-text-fill: #242d35;";

	public TelaDeVenda(Banco banco) {
		super("AutoManager", 700, 600, TipoBotaoVoltar.VOLTAR);
		this.vendaService = new VendaService(banco);

		VBox conteudo = new VBox();
		conteudo.setAlignment(Pos.CENTER);

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

		Button[] botao = { new Button("Carro"), new Button("Cliente"), new Button("Pagamento"),
				new Button("Nota Fiscal"), new Button("Finalizar Venda") };

		for (Button b : botao) {
			b.setStyle(BUTTON_STYLE);
		}

		celulasTabela = new VBox[] { new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(),
				new VBox() };

		for (int x = 0; x < 8; x++) {
			celulasTabela[x].setStyle("-fx-border-color: black; -fx-pref-height: 100%; -fx-pref-width: 150px;"
					+ " -fx-padding:5px; -fx-border-insets: 0px; -fx-background-insets: 20px");
			celulasTabela[x].setAlignment(Pos.CENTER);
		}

		GridPane tabela = new GridPane();
		tabela.setVgap(0.5);
		tabela.setHgap(0.5);
		tabela.add(celulasTabela[0], 0, 1);
		tabela.add(celulasTabela[1], 0, 2);
		tabela.add(celulasTabela[2], 0, 3);
		tabela.add(celulasTabela[3], 0, 4);
		tabela.add(celulasTabela[4], 1, 1);
		tabela.add(celulasTabela[5], 1, 2);
		tabela.add(celulasTabela[6], 1, 3);
		tabela.add(celulasTabela[7], 1, 4);

		celulasTabela[4].setMinWidth(300.0);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();

		// interiorE.getChildren().add(carroTexto);
		celulasTabela[0].getChildren().add(new Text("Carro:"));
		celulasTabela[1].getChildren().add(new Text("Cliente:"));
		celulasTabela[2].getChildren().add(new Text("Pagamento:"));
		celulasTabela[3].getChildren().add(new Text("Data da Venda:"));
		celulasTabela[7].getChildren().add(new Text(dtf.format(now)));
		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 177));
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(botoesCima);
		// interior.getChildren().add(interiorE);

		HBox interior = new HBox();
		interior.setStyle("-fx-pref-height: 150px;  -fx-pref-width: 20px;"
				+ "-fx-padding: 5px 0 1px 1px; -fx-border-insets: 20px; -fx-background-insets: 20px");

		interior.getChildren().add(tabela);
		conteudo.getChildren().add(interior);
		conteudo.getChildren().add(botoesEmbaixo);

		botoesCima.add(botao[0], 0, 0);
		botoesCima.add(botao[1], 1, 0);
		botoesCima.add(botao[2], 2, 0);

		Button botaoValoresEntradas = new Button();
		botaoValoresEntradas.setStyle(BUTTON_STYLE);
		botaoValoresEntradas.setText("Valores de entrada");

		botoesCima.add(botaoValoresEntradas, 3, 0);

		// botoesEmbaixo.add(botao[3], 0, 0);
		botoesEmbaixo.add(botao[4], 1, 0);

		botao[0].setOnAction(event -> {
			new BuscaCarro(banco, carro -> {
				if (carro == null)
					return;
				vendaService.escolherCarro(carro);
				atualizarView();
			}).show();
		});

		botao[1].setOnAction(event -> {
			new BuscaCliente(banco, cliente -> {
				if (cliente == null)
					return;
				vendaService.setCliente(cliente);
				atualizarView();
			}).show();
		});

		botao[2].setOnAction(event -> {
			new GerenciarPagamento(banco, vendaService).show();
		});

		botao[4].setOnAction(event -> {
			try {
				if (vendaService.getCliente() == null) {
					ComponentesServices.mostrarAlerta("Por favor, selecione o cliente");
				} else if (vendaService.getCarro() == null) {
					ComponentesServices.mostrarAlerta("Por favor, selecione o carro");
				} else {
					vendaService.finalizarVenda();
					close();
					// ComponentesServices.mostrarInformacao("Venda finalizada com sucesso");
				}
			} catch (IOException e) {
				ComponentesServices.mostrarErro("Falha ao conectar ao banco de dados");
				e.printStackTrace();
			}
		});

		vendaService.setVendedor(LoginService.getFuncionarioLogado());

		definirConteudo(conteudo);

	}

	private void atualizarView() {
		setCarroView(vendaService.getCarro());
		setClienteView(vendaService.getCliente());
	}

	private void setCarroView(Carro carro) {
		celulasTabela[4].getChildren().clear();

		if (carro != null) {
			Label label = new Label();
			label.setText(carro.getModelo() + " - " + carro.getMarca() + " - " + carro.getAno());
			celulasTabela[4].getChildren().add(label);
		}
	}

	private void setClienteView(Cliente cliente) {
		celulasTabela[5].getChildren().clear();

		if (cliente != null) {
			Label label = new Label();
			label.setText(cliente.getNome() + " - " + cliente.getCpf());
			celulasTabela[5].getChildren().add(label);
		}
	}

}
