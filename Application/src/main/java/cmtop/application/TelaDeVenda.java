package cmtop.application;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.busca.BuscaCarro;
import cmtop.busca.BuscaCliente;
import cmtop.domain.service.VendaService;
import cmtop.persistence.entity.Banco;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TelaDeVenda extends TelaBase {

	private VendaService vendaService;

	public TelaDeVenda(Banco banco) {
		super("AutoManager", 700, 600);

		this.vendaService = new VendaService(banco);

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

		Button[] botao = { new Button("Carro"), new Button("Cliente"), new Button("Pagamento"),
				new Button("Nota Fiscal"), new Button("Finalizar Venda") };

		for (Button b : botao) {
			b.setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
					+ "  -fx-text-fill: #242d35;");
		}

		HBox interior = new HBox();
		interior.setStyle("-fx-border-color: black; -fx-pref-height: 150px;  -fx-pref-width: 20px;"
				+ "-fx-padding: 5px 0 1px 1px; -fx-border-insets: 20px; -fx-background-insets: 20px");

		VBox[] interiorE = { new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(), new VBox(),
				new VBox() };

		for (int x = 0; x < 8; x++) {
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
		tabela.add(interiorE[0], 0, 1);
		tabela.add(interiorE[1], 0, 2);
		tabela.add(interiorE[2], 0, 3);
		tabela.add(interiorE[3], 0, 4);
		tabela.add(interiorE[4], 1, 1);
		tabela.add(interiorE[5], 1, 2);
		tabela.add(interiorE[6], 1, 3);
		tabela.add(interiorE[7], 1, 4);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();

		// interiorE.getChildren().add(carroTexto);
		interiorE[0].getChildren().add(new Text("Carro:"));
		interiorE[1].getChildren().add(new Text("Cliente:"));
		interiorE[2].getChildren().add(new Text("Pagamento:"));
		interiorE[3].getChildren().add(new Text("Data da Venda:"));
		interiorE[7].getChildren().add(new Text(dtf.format(now)));
		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);
		conteudo.getChildren().add(botoesCima);
		// interior.getChildren().add(interiorE);
		interior.getChildren().add(tabela);
		conteudo.getChildren().add(interior);
		conteudo.getChildren().add(botoesEmbaixo);

		botoesCima.add(botao[0], 0, 0);
		botoesCima.add(botao[1], 1, 0);
		botoesCima.add(botao[2], 2, 0);

		botoesEmbaixo.add(botao[3], 0, 0);
		botoesEmbaixo.add(botao[4], 1, 0);

		botao[0].setOnAction(event -> {
			new BuscaCarro(banco, carro -> {
				if (carro == null)
					return;
				vendaService.escolherCarro(carro);
			}).show();
		});

		botao[1].setOnAction(event -> {
			new BuscaCliente(banco, cliente -> {
				if (cliente == null)
					return;
				vendaService.setCliente(cliente);
			}).show();
		});

		botao[4].setOnAction(event -> {
			try {
				if (vendaService.getCliente() == null) {
					ComponentesServices.mostrarAlerta("Por favor, selecione o cliente");
				} else if (vendaService.getCarro() == null) {
					ComponentesServices.mostrarAlerta("Por favor, selecione o carro");
				} else {
					vendaService.finalizarVenda();
					ComponentesServices.mostrarInformacao("Venda finalizada com sucesso");
				}
			} catch (IOException e) {
				ComponentesServices.mostrarErro("Falha ao conectar ao banco de dados");
				e.printStackTrace();
			}
		});

		vendaService.setVendedor(LoginService.getFuncionarioLogado());

		definirConteudo(conteudo);

	}

}
