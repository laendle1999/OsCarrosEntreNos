package cmtop.application;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.Carro;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.service.DateService;
import cmtop.domain.valueobject.StatusCarro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CadastrarCompra extends TelaBase {

	public CadastrarCompra(Banco banco) {
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
				new TextField(), new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(), new TextField() };
		Text[] labels = { new Text("Modelo"), new Text("Marca"), new Text("Ano"), new Text("Placa"),
				new Text("RENAVAN"), new Text("Cor"), new Text("Adicionais"), new Text("Custo"),
				new Text("Local da Compra"), new Text("Nome do Fornecedor"), new Text("Data da Compra"),
				new Text("Codigo do Carro"), new Text("Valor de Venda") };
		Button btn = new Button("Confirmar");

		for (int x = 0; x < 11; x++) {
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
			menu.add(labels[x], 0, x + 3);
			menu.add(campos[x], 1, x + 3);
		}

		menu.setTranslateY(15);
		menu.add(labels[11], 0, 1);
		menu.add(campos[11], 1, 1);
		menu.add(labels[12], 0, 2);
		menu.add(campos[12], 1, 2);

		btn.setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
				+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;" + "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);
		btn.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Carro carro;
				try {
					carro = new Carro(0, campos[11].getText(), campos[3].getText(), campos[4].getText(),
							campos[0].getText(), campos[1].getText(), campos[5].getText(),
							Integer.parseInt(campos[2].getText()), Float.parseFloat(campos[12].getText()),
							Float.parseFloat(campos[7].getText()),
							DateService.converterDataStringParaLong(campos[10].getText()), StatusCarro.DISPONIVEL);
				} catch (NumberFormatException e1) {
					ComponentesServices.mostrarAlerta("Verifique o formato dos campos ano, custo e valor da venda");
					return;
				} catch (ParseException e) {
					ComponentesServices.mostrarAlerta("Verifique o formato da data, dia/mês/ano");
					return;
				}

				// Compra compra = new Compra(campos[8].getText(), campos[9].getText(),
				// Long.parseLong(campos[10].getText()), focusGrabCounter, focusGrabCounter);

				try {
					new CarroRepository(banco).cadastrarCarro(carro, new ListenerConsulta() {
						@Override
						public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
							ComponentesServices.mostrarInformacao("Cadastrado com sucesso");
						}

						@Override
						public void erro(Exception e) {
							e.printStackTrace();
							ComponentesServices.mostrarErro("Houve um erro no Cadastro");
						}
					});
				} catch (IOException e) {
					ComponentesServices.mostrarErro("Houve um erro no Cadastro");
					e.printStackTrace();
				}
				close();

			}
		});

		definirConteudo(conteudo);
	}

}
