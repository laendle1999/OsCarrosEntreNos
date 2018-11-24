package cmtop.application;

import cmtop.busca.BuscaCarro;
import cmtop.persistence.entity.Banco;
import javafx.application.Application;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	public static void iniciarAplicacao() {
		Banco banco = new Banco("localhost");

		// new MenuPrincipal().show();
		// new TelaDeVenda().show();
		new BuscaCarro(banco).show();
	}

	@Override
	public void start(Stage _stage) throws Exception {
		iniciarAplicacao();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
