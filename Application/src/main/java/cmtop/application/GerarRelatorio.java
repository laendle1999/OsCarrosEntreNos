package cmtop.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cmtop.application.service.ComponentesServices;
import cmtop.application.service.ImpressoraService;
import cmtop.domain.service.RelatorioFinanceiro;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GerarRelatorio extends TelaBase {

	private String htmlRelatorio = null;
	private File saidaPdf = null;
	
	public GerarRelatorio(Banco banco) {
		super("AutoManager - Gerar Relatório", 600, 500, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Gerar Relatório");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField(),new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Apartir de"), new Text("Até"), new Text("Ano"), new Text("Placa"),
				new Text("RENAVAN"), new Text("Cor"), new Text("Adicionais"), new Text("Custo"), new Text("Local da Compra"),
				new Text("Nome do Fornecedor"), new Text("Data da Compra") };
		Button[] btn = {new Button("Buscar"),new Button("Gerar PDF"),new Button("Imprimir")};

		for (int x = 0; x < 3; x++) {
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
			//menu.add(labels[x], 0, x + 1);
			//menu.add(campos[x], 1, x + 1);
			
			
			btn[x].setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
				+ "    -fx-padding: 3 3 3 3; -fx-text-fill: #242d35;"
				+ "    -fx-font-size: 14px;");
		}
		
		menu.add(labels[0],0,1);
		menu.add(campos[0],1,1);
		menu.add(btn[0],3,1);
		
		btn[0].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
	
					try {
						new RelatorioFinanceiro(banco).gerarRelatorioFinanceiro(Long.parseLong(campos[0].getText()), 30, new ListenerConsultaComResposta<String>() {
									
									@Override
									public void resposta(List<String> registros) {
										setHtmlRelatorio(registros.get(0));
										
										setSaidaPdf(new File("relatorio.pdf")); // << pedir para o usuário escolher um local para salvar
										
									}
									
									@Override
									public void erro(Exception e) {
										// mostrar mensagem
									}
								});
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					btn[1].setVisible(true);
					btn[2].setVisible(true);

			}
		});
		
		menu.add(btn[1], 0, 3);
		menu.add(btn[2], 3, 3);

		btn[1].setVisible(false);
		btn[2].setVisible(false);
		
		btn[1].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
					ImpressoraService.salvarHtmlComoPdf(getHtmlRelatorio(), getSaidaPdf());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btn[2].setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
					ImpressoraService.imprimirHtml(getHtmlRelatorio());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		menu.setTranslateY(15);

		

		//conteudo.getChildren().add(btn);
		//btn[1].setTranslateX(300);
		//btn[1].setTranslateY(30);

		definirConteudo(conteudo);
	}

	public String getHtmlRelatorio() {
		return htmlRelatorio;
	}

	public void setHtmlRelatorio(String htmlRelatorio) {
		this.htmlRelatorio = htmlRelatorio;
	}

	public File getSaidaPdf() {
		return saidaPdf;
	}

	public void setSaidaPdf(File saidaPdf) {
		this.saidaPdf = saidaPdf;
	}

	
	
}
