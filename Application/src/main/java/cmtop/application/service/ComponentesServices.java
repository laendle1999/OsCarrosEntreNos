package cmtop.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		alert.show();
	}

	public static void mostrarErro(String mensagem) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro");
		alert.setHeaderText(mensagem);
		alert.show();
	}

}
