package cmtop.application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TelaConfiguracao extends TelaBase {

	public TelaConfiguracao() {
		super("AutoManager - Formulário de entrada", 600, 500);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Cadastro de[ISSO AI MSMO]");
		secao.setTextAlignment(TextAlignment.LEFT);

		Text nome = new Text("Auto Management");
		nome.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 45));
		nome.setTextAlignment(TextAlignment.CENTER);
		nome.setStyle("-fx-border-color:red;");

		conteudo.getChildren().add(nome);
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Senha Nova"), new Text("Confirmar Senha"), new Text("Tempo de Alerta"), new Text("Bakcup"),
				new Text("Campo 5"), new Text("Campo 6") };
		Button btn = new Button("Salvar Alterações");
		Button[] btnBackup = {new Button("Exportar Backup"),new Button("Importar Backup")};

		
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

		btn.setStyle("    -fx-background-color: \r\n" + "        rgba(0,0,0,0.08),\r\n"
				+ "        linear-gradient(#9a9a9a, #909090),\r\n"
				+ "        linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 3 30 3 30;\r\n" + "    -fx-text-fill: #242d35;\r\n" + "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);

		definirConteudo(conteudo);
	}

}
