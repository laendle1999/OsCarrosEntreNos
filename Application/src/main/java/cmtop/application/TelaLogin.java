package cmtop.application;

import java.io.IOException;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.Banco;
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
		super("AutoManager - Autenticação", 600, 500);
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
		conteudo.getChildren().add(secao);
		
		conteudo.getChildren().add(new Label("usuario: funcionario, senha: 1234"));
		conteudo.getChildren().add(new Label("usuario: gerente, senha: 1234"));

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new PasswordField() };
		Text[] labels = { new Text("Usuário"), new Text("Senha") };
		Button btn = new Button("Confirmar");

		for (int x = 0; x < campos.length; x++) {
			menu.add(labels[x], 0, x + 1);
			menu.add(campos[x], 1, x + 1);
		}

		menu.setTranslateY(15);

		btn.setStyle("-fx-font-size: 14px; -fx-cursor: hand;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(130);
		btn.setTranslateY(30);

		btn.setOnAction(event -> {
			String login = campos[0].getText();
			String senha = campos[1].getText();
			try {
				if (!LoginService.logar(banco, login, senha)) {
					ComponentesServices.mostrarErro("Falha ao fazer login, verifique o usuário e a senha");
				} else {
					abrirMenu();
				}
			} catch (IOException e) {
				ComponentesServices.mostrarErro("Falha ao conectar ao banco de dados");
				e.printStackTrace();
			}
		});

		definirConteudo(conteudo);
	}

	private void abrirMenu() {
		if (LoginService.estaLogado()) {
			new MenuPrincipal(banco).show();
			close();
		}
	}

}
