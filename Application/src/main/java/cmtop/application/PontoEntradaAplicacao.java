package cmtop.application;

import javafx.application.Application;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	public static void iniciarAplicacao() {
		// new MenuPrincipal().show();
		// new TelaDeVenda().show();
		new Busca().show();
	}

	@Override
	public void start(Stage _stage) throws Exception {
		iniciarAplicacao();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
