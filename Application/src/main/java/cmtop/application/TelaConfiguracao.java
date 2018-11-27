package cmtop.application;

import cmtop.application.service.ComponentesServices;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TelaConfiguracao extends TelaBase {

	public TelaConfiguracao() {
		super("AutoManager - Configuração", 600, 500);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Configuração");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Senha Nova"), new Text("Confirmar Senha"), new Text("Tempo de Alerta"),
				new Text("Bakcup"), new Text("Campo 5"), new Text("Campo 6") };
		Button btn = new Button("Salvar Alterações");
		Button[] btnBackup = { new Button("Exportar Backup"), new Button("Importar Backup") };

		menu.add(labels[0], 0, 1);
		menu.add(campos[0], 1, 1, 2, 1);
		menu.add(labels[1], 0, 2);
		menu.add(campos[1], 1, 2, 2, 1);
		menu.add(labels[2], 0, 4);
		menu.add(campos[2], 1, 4, 2, 1);
		menu.add(labels[3], 0, 6);
		menu.add(btnBackup[0], 1, 6);
		menu.add(btnBackup[1], 2, 6);

		menu.setTranslateY(15);

		btn.setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
				+ "    -fx-text-fill: #242d35; -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);

		definirConteudo(conteudo);
	}

}
