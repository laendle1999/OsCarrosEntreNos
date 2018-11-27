package cmtop.application.service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PortalService {

	public static boolean abrirProgramaNf() {
		String executavel = "C:\\Program Files\\Programa\\programa.exe";

		try {
			new ProcessBuilder(executavel).start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean abrirTabelaFipe() {
		URI uri;
		try {
			uri = new URI("https://www.icarros.com.br/tabela-fipe/index.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}

		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
