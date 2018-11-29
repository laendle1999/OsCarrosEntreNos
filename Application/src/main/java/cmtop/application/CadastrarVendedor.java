package cmtop.application;

import java.io.IOException;
import java.text.ParseException;

import cmtop.application.service.ComponentesServices;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.domain.service.DateService;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CadastrarVendedor extends TelaBase {

	public CadastrarVendedor(Banco banco) {
		super("AutoManager - Formulário de entrada", 600, 700, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Cadastro de vendedor");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(), new TextField(), new TextField(), new TextField(), new TextField() };
		Text[] labels = { new Text("Nome"), new Text("E-mail"), new Text("RG"), new Text("CPF"), new Text("Endereço"),
				new Text("Login"), new Text("Senha"), new Text("Telefone 1"), new Text("Telefone 2"),
				new Text("Data de Nascimento") };
		Button btn = new Button("Confirmar");

		for (int x = 0; x < 10; x++) {
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
			menu.add(campos[x], 1, x + 1, 2, 1);
		}

		RadioButton[] tipoCad = { new RadioButton("Vendedor"), new RadioButton("Gerente") };
		menu.add(new Text("Tipo de Conta"), 0, 11);
		menu.add(tipoCad[0], 1, 11);
		menu.add(tipoCad[1], 2, 11);

		ToggleGroup toggleGroup = new ToggleGroup();
		tipoCad[0].setSelected(true);
		tipoCad[0].setToggleGroup(toggleGroup);
		tipoCad[1].setToggleGroup(toggleGroup);

		menu.setTranslateY(15);

		btn.setStyle("    -fx-background-color: \r\n" + "        rgba(0,0,0,0.08),\r\n"
				+ "        linear-gradient(#9a9a9a, #909090),\r\n"
				+ "        linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 3 30 3 30;\r\n" + "    -fx-text-fill: #242d35;\r\n" + "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);

		btn.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				long dataNascimento;
				try {
					dataNascimento = DateService.converterDataStringParaLong(campos[9].getText());
				} catch (ParseException e1) {
					ComponentesServices.mostrarAlerta(
							"A data de nascimento deve estar no formato dia/mês/ano, por exemplo 10/10/1990");
					return;
				}

				TipoAcesso tipoAcesso = TipoAcesso.VENDEDOR;
				if (tipoCad[1].isSelected()) {
					tipoAcesso = TipoAcesso.GERENTE;
				}

				Vendedor vendedor = new Vendedor(6, campos[7].getText(), campos[8].getText(), campos[0].getText(),
						dataNascimento, campos[2].getText(), campos[3].getText(), campos[4].getText(),
						campos[5].getText(), campos[1].getText(), tipoAcesso);
				try {
					new VendedorRepository(banco).cadastrarVendedor(vendedor, campos[5].getText(),
							new ListenerConsulta() {
								@Override
								public void sucesso(int resultadosAfetados) {
									ComponentesServices.mostrarInformacao("Cadastrado com sucesso");
								}

								@Override
								public void erro(Exception e) {
									e.printStackTrace();
									ComponentesServices.mostrarErro("Houve um erro no Cadastro");
								}
							});
				} catch (IOException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
				close();
			}
		});

		definirConteudo(conteudo);
	}

}
