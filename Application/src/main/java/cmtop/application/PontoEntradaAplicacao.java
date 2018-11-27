package cmtop.application;

import cmtop.busca.*;
import cmtop.persistence.entity.Banco;
import javafx.application.Application;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	public static void iniciarAplicacao() {
	//	Banco banco = new Banco("192.168.64.3");

		// new MenuPrincipal().show();
		// new TelaDeVenda().show();
//		new BuscaCliente(banco, carro -> {
//			if (carro == null) {
//				System.out.println("Nenhum carro escolhido");
//			} else {
//				System.out.println("Carro escolhido (marca): ");
//				System.out.println(carro.getId());
//			}
//		}).show();
		new CadastrarVendedor().show();
	}

	@Override
	public void start(Stage _stage) throws Exception {
		iniciarAplicacao();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
