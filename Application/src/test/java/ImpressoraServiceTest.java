import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cmtop.application.service.ImpressoraService;

public class ImpressoraServiceTest {

	private static String obterHtml() {
		StringBuilder s = new StringBuilder();

		s.append("<!DOCTYPE html>");
		s.append("<html><head><meta charset=\"utf-8\"/>");
		s.append("<style>*{font-family: sans-serif; text-align: center;}</style>");
		s.append("</head><body>");
		s.append("<h1>Teste conversão HTML → PDF</h1>");
		s.append("</body></html>");

		String html = s.toString();
		return html;
	}

	@Test
	public void testeSalvarPdf() throws IOException {
		String html = obterHtml();
		File saidaPdf = new File(getClass().getName() + ".pdf");
		ImpressoraService.salvarHtmlComoPdf(html, saidaPdf);

		assertTrue(saidaPdf.exists());
	}

	public void testeImprimirPdf() throws IOException {
		String html = obterHtml();
		ImpressoraService.imprimirHtml(html);
	}

}
