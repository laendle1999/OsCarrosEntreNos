package cmtop.application;

import cmtop.application.service.ComponentesServices;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashScreen extends Stage {

	private Label label;

	public SplashScreen() {
		initStyle(StageStyle.UNDECORATED);

		setWidth(440);
		setHeight(190);

		VBox raiz = new VBox();
		raiz.setAlignment(Pos.CENTER);

		raiz.getChildren().add(ComponentesServices.obterLogoAplicacao(400, 200));

		label = new Label("Iniciando o AutoManager...");
		label.setStyle("-fx-padding: 10px;");
		raiz.getChildren().add(label);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(raiz);

		Scene cena = new Scene(borderPane);
		setScene(cena);
		centerOnScreen();
	}

	public void mostrarStatus(String mensagem) {
		Platform.runLater(() -> {
			label.setText(mensagem);
		});
	}

}
