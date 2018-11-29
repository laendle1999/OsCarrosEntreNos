package cmtop.application.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.ini4j.Wini;
import org.zeroturnaround.zip.ZipUtil;

public class ConfiguracaoService {

	public static final String NOME_ARQUIVO_CONFIGURACAO = "configuracoes.ini";

	public static void backupBanco(File arquivoZipSaida) {
		ZipUtil.pack(new File("auto_manager"), arquivoZipSaida);
	}

	public static void gravarTempoAlertaEstoque(int dias) throws IOException {
		Wini ini = loadIni();
		ini.put("config", "tempoAlerta", dias);
		gravarIni(ini);
	}

	public static int obterTempoAlertaEstoque() throws IOException {
		Wini ini = loadIni();
		return ini.get("config", "tempoAlerta", int.class);
	}

	private static Wini loadIni() throws IOException {
		Wini ini = new Wini();

		File file = new File(NOME_ARQUIVO_CONFIGURACAO);
		if (file.exists()) {
			ini.load(new FileReader(file));
		} else {
			ini.store(file);
		}
		return ini;
	}

	private static void gravarIni(Wini ini) throws IOException {
		File file = new File(NOME_ARQUIVO_CONFIGURACAO);
		ini.store(file);
	}
}
