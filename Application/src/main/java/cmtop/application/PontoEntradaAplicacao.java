package cmtop.application;

import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.Banco.TipoConexao;
import javafx.application.Application;
import javafx.stage.Stage;

public class PontoEntradaAplicacao extends Application {

	public static void iniciarAplicacao() {
		Banco banco = new Banco("", "", TipoConexao.SERVIDOR);

		// new MenuPrincipal().show();
		// new TelaDeVenda().show();
		// new BuscaCarro(banco, carro -> {
		// if (carro == null) {
		// System.out.println("Nenhum carro escolhido");
		// } else {
		// System.out.println("Carro escolhido (marca): ");
		// System.out.println(carro.getMarca());
		// }
		// }).show();

		// new BuscaCarroComEdicao(banco, (carroAlterado, nomeCampo, valor) -> {
		// System.out.println(nomeCampo);
		// System.out.println(carroAlterado.getPlaca());
		// return true;
		// }, carro -> System.out.println("Carro apagado: " +
		// carro.getModelo())).show();

		new TelaLogin(banco).show();
	}

	@Override
	public void start(Stage _stage) throws Exception {
		iniciarAplicacao();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
