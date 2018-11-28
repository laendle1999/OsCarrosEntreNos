package cmtop.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

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
		JOptionPane.showMessageDialog(null, mensagem, "Alerta", JOptionPane.WARNING_MESSAGE);
	}

	public static void mostrarErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public static void mostrarEntradaTexto(String mensagem, Consumer<String> listenerEntrada) {
		new Thread(() -> {
			String input = JOptionPane.showInputDialog(mensagem);
			listenerEntrada.accept(input);
		}).start();
	}

	public static void mostrarInformacao(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void mostrarConfirmacao(String mensagem, Consumer<Boolean> resultado) {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Executar como servidor?", "",
				JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			resultado.accept(true);
		} else if (dialogResult == JOptionPane.NO_OPTION) {
			resultado.accept(false);
		} else {
			resultado.accept(null);
		}
	}

}
