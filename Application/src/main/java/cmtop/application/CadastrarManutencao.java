package cmtop.application;

import java.io.IOException;

import cmtop.application.service.ComponentesServices;
import cmtop.busca.BuscaCarro;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Manutencao;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.ManutencaoRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CadastrarManutencao extends TelaBase {

	private Carro carro;
	
	public CadastrarManutencao(Banco banco) {
		super("AutoManager - Cadastrar Manutenção", 600, 500, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Cadastro de Manutenção");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(300, 200));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Código do Carro"), new Text("Data da Manutenção"), new Text("Descrição"),
				new Text("Custo"), new Text("Campo 5"), new Text("Campo 6") };
		Button btn = new Button("Confirmar");
		Button[] botao = {new Button("Busca Carro")};

		for (int x = 0; x < 4; x++) {
			// campos[x].setStyle(
			// " -fx-background-color: \r\n" +
			// " rgba(0,0,0,0.08),\r\n" +
			// " linear-gradient(#9a9a9a, #909090),\r\n" +
			// " linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n" +
			// " -fx-background-insets: 0 0 -1 0,0,1;\r\n" +
			// " -fx-background-radius: 5,5,4;\r\n" +
			// " -fx-padding: 3 30 3 30;\r\n" +
			// " -fx-text-fill: #242d35;\r\n" +
			// " -fx-font-size: 14px;");
			menu.add(labels[x], 0, x + 1);
			menu.add(campos[x], 1, x + 1);
		}
		menu.add(botao[0], 2, 1);
		
		campos[2].setPrefHeight(100);
		menu.setTranslateY(15);

		btn.setStyle("    -fx-background-color: \r\n" + "        rgba(0,0,0,0.08),\r\n"
				+ "        linear-gradient(#9a9a9a, #909090),\r\n"
				+ "        linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%);\r\n"
				+ "    -fx-background-insets: 0 0 -1 0,0,1;\r\n" + "    -fx-background-radius: 5,5,4;\r\n"
				+ "    -fx-padding: 3 30 3 30;\r\n" + "    -fx-text-fill: #242d35;\r\n" + "    -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);
		
		botao[0].setOnMouseClicked(event->{
			new BuscaCarro(banco, carro->{
				if(carro == null)
					return;
				setCarro(carro);
				campos[0].setText(carro.getId()+"");
			}).show();
			
		});
		
		btn.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Manutencao manutencao = new Manutencao(0, campos[2].getText(), Long.parseLong(campos[1].getText()), 
						Float.parseFloat(campos[3].getText()), getCarro().getId());
//				try {
//					new ManutencaoRepository(banco).cadastrarManutencao(manutencao , new ListenerConsulta() {
//						@Override
//						public void sucesso(int resultadosAfetados) {
//							ComponentesServices.mostrarInformacao("Cadastrado com sucesso");
//						}
//						@Override
//						public void erro(Exception e){
//							e.printStackTrace();
//							ComponentesServices.mostrarErro("Houve um erro no Cadastro");
//						}
//						});
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					System.err.println("alou");
//					e.printStackTrace();
//				}
				close();
				
				
			}
		});

		definirConteudo(conteudo);
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	
	
}
