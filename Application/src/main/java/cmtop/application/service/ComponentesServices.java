package cmtop.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ComponentesServices {

	public static ImageView obterLogoAplicacao(double width, double height) {
		ClassLoader classLoader = ComponentesServices.class.getClassLoader();
		String imgPath = classLoader.getResource("img/AutoManagerLogo.png").getPath();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(imgPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Image img = new Image(fis, width, height, true, true);
		ImageView logo = new ImageView(img);

		return logo;
	}

	public static void mostrarAlerta(String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alerta");
		alert.setHeaderText(mensagem);
		alert.showAndWait();
	}

	public static void mostrarErro(String mensagem) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro");
		alert.setHeaderText(mensagem);
		alert.showAndWait();
	}

	public static void mostrarInformacao(String mensagem) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Informação");
		alert.setHeaderText(mensagem);
		alert.showAndWait();
	}

	public static void mostrarConfirmacao(String mensagem, Consumer<Boolean> resultado) {
		Alert alert = new Alert(AlertType.CONFIRMATION, mensagem, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			resultado.accept(true);
		} else if (alert.getResult() == ButtonType.NO) {
			resultado.accept(false);
		} else {
			resultado.accept(null);
		}
	}

}
