package cmtop.application;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.application.service.PortalService;
import cmtop.busca.BuscarVendas;
import cmtop.domain.valueobject.TipoAcesso;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.service.MyThread;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuPrincipal extends TelaBase {

	public MenuPrincipal(Banco banco) {
		super("AutoManager - Menu principal", 700, 600, TipoBotaoVoltar.SAIR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);

		String nomeVendedor = LoginService.getFuncionarioLogado().getNome().split("\\s")[0];

		Text vendedor = new Text("Olá " + nomeVendedor);
		vendedor.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(400, 177));
		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER);
		menu.add(vendedor, 2, 0);

		Button[] botoes = { new Button("Nova Venda"), new Button("Clientes"), new Button("Tabela FIPE"),
				new Button("Buscar Venda"), new Button("Gerência"), new Button("Funcao 6") };

		for (Button b : botoes) {
			b.setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
					+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;"
					+ "    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}

		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		menu.add(botoes[3], 0, 2);

		if (LoginService.getFuncionarioLogado().getTipoAcesso() == TipoAcesso.GERENTE) {
			menu.add(botoes[4], 1, 2);
		}

		// menu.add(botoes[5], 2, 2);

		botoes[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new TelaDeVenda(banco).show();
			}
		});

		botoes[1].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new MenuGerenciamentoCliente(banco).show();
			}
		});

		botoes[2].setOnMouseClicked(
				event -> new MyThread(() -> PortalService.abrirTabelaFipe(), "MenuPrincipalFipeClick").start());

		botoes[3].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new BuscarVendas(banco, null).show();
			}
		});

		botoes[4].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new MenuGerencia(banco).show();
			}
		});

		definirConteudo(conteudo);

		setOnCloseRequest(event -> {
			PontoEntradaAplicacao.finalizarAplicacao();
		});
	}

}
