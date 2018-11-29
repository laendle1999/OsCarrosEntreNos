package cmtop.application;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.LoginService;
import cmtop.busca.BuscaComEdicao.ListenerAlteracoes;
import cmtop.busca.BuscarClienteComEdicao;
import cmtop.busca.BuscarVendaACliente;
import cmtop.domain.entity.Cliente;
import cmtop.persistence.entity.Banco;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuGerenciamentoCliente extends TelaBase {

	public MenuGerenciamentoCliente(Banco banco) {
		super("AutoManager - Gerenciamento de clientes", 700, 600, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(10);

		String nomeVendedor = LoginService.getFuncionarioLogado().getNome().split("\\s")[0];
		Text vendedor = new Text("Olá " + nomeVendedor);
		vendedor.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER);
		menu.add(vendedor, 2, 0);

		Button[] botoes = { new Button("Cadastrar"), new Button("Alterar"), new Button("Buscar Vendas"),
				new Button("Buscar Venda"), new Button("Gerencia "), new Button("Funcao 6") };

		for (int x = 0; x < 6; x++) {
			botoes[x].setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
					+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;"
					+ "    -fx-font-size: 14px;-fx-pref-width: 150px; -fx-pref-height: 75px");
		}

		menu.add(botoes[0], 0, 1);
		menu.add(botoes[1], 1, 1);
		menu.add(botoes[2], 2, 1);
		// menu.add(botoes[3], 0, 2);
		// menu.add(botoes[4], 1, 2);
		// menu.add(botoes[5], 2, 2);

		botoes[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				new CadastrarCliente(banco).show();
			}
		});
		
		botoes[1].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Entrou no botao");
				new BuscarClienteComEdicao(banco, new ListenerAlteracoes<Cliente>() {
					@Override
					public boolean aceitarMudanca(Cliente objetoBuscado, String campo, String valorNovo) {
						return true; // << retornar se valorNovo é valido ou não
					}
				}, ClienteSendoApagado -> {
					/// apagar item do banco
				}).show();
				
			}
		});
		
		botoes[2].setOnMouseClicked(event -> new BuscarVendaACliente(banco, null).show());

		definirConteudo(conteudo);
	}

}
