package cmtop.application;

import java.io.IOException;
import java.util.List;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TelaLogin extends TelaBase {

	private Banco banco;

	public TelaLogin(Banco banco) {
		super("AutoManager - Autenticação", 600, 500, TipoBotaoVoltar.NENHUM);
		this.banco = banco;

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Autentique-se");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 200));
		//conteudo.getChildren().add(secao);

		//conteudo.getChildren().add(new Label("usuario: funcionario, senha: 1234"));
		//conteudo.getChildren().add(new Label("usuario: gerente, senha: 1234"));

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new PasswordField() };
		Text[] labels = { new Text("Usuário"), new Text("Senha") };
		Button btn = new Button("Confirmar");

		for (int x = 0; x < campos.length; x++) {
			menu.add(labels[x], 0, x + 1);
			menu.add(campos[x], 1, x + 1);
		}

		campos[0].setOnAction(event -> campos[1].requestFocus());
		campos[1].setOnAction(event -> btn.fire());

		menu.setTranslateY(15);

		btn.setStyle("-fx-font-size: 14px; -fx-cursor: hand;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(130);
		btn.setTranslateY(30);

		btn.setOnAction(event -> {
			String login = campos[0].getText();
			String senha = campos[1].getText();

			try {
				LoginService.logar(banco, login, senha, new ListenerConsultaComResposta<Boolean>() {

					@Override
					public void resposta(List<Boolean> registros) {
						Boolean resposta = registros.get(0);
						if (!resposta) {
							ComponentesServices.mostrarErro("Falha ao fazer login, verifique o usuário e a senha");
						} else {
							abrirMenu();
						}
					}

					@Override
					public void erro(Exception e) {
						ComponentesServices.mostrarErro("Falha ao se comunicar com o banco de dados");
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				ComponentesServices.mostrarErro("Falha ao conectar ao banco de dados");
				e.printStackTrace();
			}
		});

		definirConteudo(conteudo);

		setOnCloseRequest(event -> {
			PontoEntradaAplicacao.finalizarAplicacao();
		});
	}

	private void abrirMenu() {
		Platform.runLater(() -> {
			if (LoginService.estaLogado()) {
				new MenuPrincipal(banco).show();
				close();
			}
		});
	}

}
