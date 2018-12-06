package cmtop.application;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.Cliente;
import cmtop.domain.repository.ClienteRepository;
import cmtop.domain.service.DateService;
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

public class CadastrarCliente extends TelaBase {

	public CadastrarCliente(Banco banco) {
		super("AutoManager - Cadastro de cliente", 600, 500, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Cadastro de Cliente");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(), new TextField() };
		Text[] labels = { new Text("Nome"), new Text("RG"), new Text("CPF"), new Text("Telefone 1"),
				new Text("Telefone 2"), new Text("Endereço"), new Text("Data de Nascimento") };
		Button btn = new Button("Confirmar");

		for (int x = 0; x < 7; x++) {
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

		btn.setStyle(" -fx-cursor: hand;  -fx-padding: 3 30 3 30;\r\n" + "    -fx-text-fill: #242d35;\r\n"
				+ "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);

		btn.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
					Cliente cliente = new Cliente(-1, campos[0].getText(), campos[1].getText(), campos[2].getText(),
							campos[3].getText(), campos[4].getText(), campos[5].getText(),
							DateService.converterDataStringParaLong(campos[6].getText()));
					try {
						new ClienteRepository(banco).cadastrarCliente(cliente, new ListenerConsulta() {
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
						ComponentesServices.mostrarErro("Houve um erro no cadastro");
						e.printStackTrace();
					}
					close();

				} catch (NumberFormatException | ParseException e) {
					ComponentesServices.mostrarAlerta("Verifique o formato da data, dia/mês/ano");
				}
			}
		});

		definirConteudo(conteudo);
	}

}
