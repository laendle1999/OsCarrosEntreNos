package cmtop.application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cmtop.application.PontoEntradaAplicacao.ConfiguracaoBanco;
import cmtop.application.service.ComponentesServices;
import cmtop.application.service.ConfiguracaoService;
import cmtop.application.service.LoginService;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsulta;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

public class TelaConfiguracao extends TelaBase {

	public TelaConfiguracao(Banco banco) {
		super("AutoManager - Configuração", 800, 500, TipoBotaoVoltar.VOLTAR);

		VBox conteudo = new VBox();

		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setStyle("-fx-background-fill: black, white ;\n" + "-fx-background-insets: 0, 1 ;");
		menu.setHgap(10);
		menu.setVgap(10);

		Text secao = new Text("Configuração");
		secao.setTextAlignment(TextAlignment.LEFT);

		conteudo.getChildren().add(ComponentesServices.obterLogoAplicacao(500, 177));
		conteudo.getChildren().add(secao);

		conteudo.getChildren().add(menu);
		conteudo.setAlignment(Pos.CENTER_LEFT);

		TextField[] campos = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
				new TextField() };
		Text[] labels = { new Text("Senha Nova"), new Text("Confirmar Senha"),
				new Text("Tempo de alerta de carro em estoque (dias)"), new Text("Backup"), new Text("Campo 5"),
				new Text("Campo 6") };
		Button btn = new Button("Salvar Alterações");
		Button[] btnBackup = { new Button("Exportar Backup"), new Button("Importar Backup") };

		menu.add(labels[0], 0, 1);
		menu.add(campos[0], 1, 1, 2, 1);
		menu.add(labels[1], 0, 2);
		menu.add(campos[1], 1, 2, 2, 1);
		menu.add(labels[2], 0, 4);
		menu.add(campos[2], 1, 4, 2, 1);
		menu.add(labels[3], 0, 6);
		menu.add(btnBackup[0], 1, 6);
		menu.add(btnBackup[1], 2, 6);

		try {
			campos[2].setText(ConfiguracaoService.obterTempoAlertaEstoque() + "");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		menu.setTranslateY(15);

		btn.setStyle("-fx-font-size: 14px; -fx-cursor: hand; -fx-background-radius: 5,5,4;"
				+ "    -fx-text-fill: #242d35; -fx-font-size: 14px;");

		conteudo.getChildren().add(btn);
		btn.setTranslateX(300);
		btn.setTranslateY(30);

		btn.setOnAction(event -> {
			if (!campos[0].getText().isEmpty()) {
				if (!campos[0].getText().equals(campos[1].getText())) {
					ComponentesServices.mostrarAlerta("As senhas estão diferentes");
				} else {
					alterarSenha(banco, campos[0].getText());
				}
			}

			if (!campos[2].getText().isEmpty()) {
				String valor = campos[2].getText();
				try {
					int dias;
					dias = Integer.parseInt(valor);
					ConfiguracaoService.gravarTempoAlertaEstoque(dias);
				} catch (NumberFormatException e) {
					ComponentesServices.mostrarErro("Digite um número no campo tempo");
				} catch (IOException e) {
					ComponentesServices.mostrarErro("Falha ao gravar tempo de carro em estoque");
					e.printStackTrace();
				}
			}

		});
		btnBackup[0].setOnMouseClicked(event -> {

			ConfiguracaoBanco configuracaoBanco = PontoEntradaAplicacao.getConfiguracaoBanco();
			if (configuracaoBanco == ConfiguracaoBanco.SERVIDOR_REDE_LOCAL) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Exportar Backup");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Todos os arquivos", "*.*"));
				File arquivo = fileChooser.showSaveDialog(null);
				ConfiguracaoService.backupBanco(arquivo);
			} else if (configuracaoBanco == ConfiguracaoBanco.CLIENTE_REDE_LOCAL) {
				ComponentesServices.mostrarErro("Não é possivel fazer Backup pelo computador do Cliente");
			} else {
				ComponentesServices.mostrarInformacao("Banco em Nuvem não necessita de Backup");
			}

		});

		btnBackup[1].setOnMouseClicked(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Importar Backup");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ZIP", "*.zip"),
					new FileChooser.ExtensionFilter("Todos os arquivos", "*.*"));
			File arquivo = fileChooser.showOpenDialog(null);

			System.out.println("Comecando o backup");

			ComponentesServices.mostrarConfirmacao("Se Continuar todos os Registros Atuais serão perdidos",
					resultado -> {
						if (!resultado)
							return;
						new Thread(() -> {
							System.out.println("Sou uma thread livre");
							ComponentesServices.mostrarAlerta("Por Favor Aguarde");
						}, "TelaConfiguracao importarBackup").start();
						ConfiguracaoService.importarBackup(arquivo);
					});
		});

		definirConteudo(conteudo);
	}

	private void alterarSenha(Banco banco, String senha) {
		Vendedor vendedor = LoginService.getFuncionarioLogado();
		try {
			new VendedorRepository(banco).alterarSenhaVendedor(vendedor, senha, new ListenerConsulta() {

				@Override
				public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
					ComponentesServices.mostrarInformacao("Senhas alterada com sucesso");
				}

				@Override
				public void erro(Exception e) {
					e.printStackTrace();
					ComponentesServices.mostrarErro("Falha ao alterar senha");
				}
			});
		} catch (IOException e) {
			ComponentesServices.mostrarErro("Falha ao conectar com o banco de dados");
			e.printStackTrace();
		}
	}
}
