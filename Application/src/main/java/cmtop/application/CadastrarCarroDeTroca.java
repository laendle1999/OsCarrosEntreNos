package cmtop.application;

import java.io.IOException;
import java.util.List;

import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.TrocaCarroRepository;
import cmtop.domain.valueobject.StatusCarro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CadastrarCarroDeTroca extends TelaBase {

	public CadastrarCarroDeTroca(Banco banco, TrocaCarro troca) {
		super("AutoManager - Cadastro de compra", 700, 800, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Cadastro de compra");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(), new TextField() };
		Text[] labels = { new Text("Modelo"), new Text("Marca"), new Text("Ano"), new Text("Placa"),
				new Text("RENAVAN"), new Text("Cor"), new Text("Custo") };
		campos[0].setText(troca.getModelo());
		campos[1].setText(troca.getMarca());
		campos[2].setText(troca.getAno() + "");
		campos[3].setText(troca.getPlaca());
		campos[4].setText("");
		campos[5].setText(troca.getCor());
		campos[6].setText(troca.getValorCarro() + "");

		Button btn = new Button("Confirmar");

		for (int x = 0; x < labels.length; x++) {
			// campos[x].setStyle(
			// " -fx-background-color: \r\n" +
			// " rgba(0,0,0,0.08),\r\n" +
			// " linear-gradient(#9a9a9a, #909090),\r\n" +
			// " linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n" +
			// " -fx-background-insets: 0 0 -1 0,0,1;\r\n" +
			// " -fx-background-radius: 5,5,4;\r\n" +
			// " -fx-padding: 3 30 3 30;\r\n" +
			// " -fx-text-fill: #242d35;\r\n" +
			// " -fx-font-size: 14px;");
			menu.add(labels[x], 0, x + 1);
			menu.add(campos[x], 1, x + 1);
		}

		menu.setTranslateY(15);

		btn.setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
				+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;" + "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);
		btn.setOnAction(event -> {
			String numero = "";
			String placa = campos[3].getText();
			String renavan = campos[4].getText();
			String modelo = campos[0].getText();
			String marca = campos[1].getText();
			String cor = campos[5].getText();
			int ano;
			try {
				ano = Integer.parseInt(campos[2].getText());
			} catch (NumberFormatException e) {
				ComponentesServices.mostrarAlerta("O ano deve ser um número");
				return;
			}
			float valorVenda = troca.getValorCarro();
			float custo;
			try {
				custo = Float.parseFloat(campos[6].getText());
			} catch (NumberFormatException e) {
				ComponentesServices.mostrarAlerta("O custo deve ser um número");
				return;
			}
			long dataEntrada = troca.getDataEntrada();
			StatusCarro statusCarro = StatusCarro.DISPONIVEL;

			Carro carro = new Carro(-1, numero, placa, renavan, modelo, marca, cor, ano, valorVenda, custo, dataEntrada,
					statusCarro);

			try {
				new CarroRepository(banco).cadastrarCarro(carro, new ListenerConsulta() {
					@Override
					public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
						ComponentesServices.mostrarInformacao("Cadastrado com sucesso");
						Platform.runLater(() -> close());
						apagarTrocaCarro(banco, troca);
					}

					@Override
					public void erro(Exception e) {
						e.printStackTrace();
						ComponentesServices.mostrarErro(
								"Houve um erro no cadastro, verifique se não existe um carro com o mesmo RENAVAN ou com a mesma a placa");
					}
				});
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}

		});

		definirConteudo(conteudo);
	}

	private void apagarTrocaCarro(Banco banco, TrocaCarro troca) {
		try {
			new TrocaCarroRepository(banco).removerTrocaCarro(troca, new ListenerConsulta() {

				@Override
				public void sucesso(int resultadosAfetados, List<Long> chaves) {
					System.out.println("Troca carro antiga foi removida do banco");
				}

				@Override
				public void erro(Exception e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
