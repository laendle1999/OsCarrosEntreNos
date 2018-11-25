package cmtop.application.service;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class ImpressoraService {

	private static Path criarPastaTemporaria() throws IOException {
		return Files.createTempDirectory("ImpressoraService_" + System.nanoTime());
	}

	private static void salvarTextoEmArquivo(String texto, File saida) throws IOException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saida), "utf-8"))) {
			writer.write(texto);
		}
	}

	public static void salvarHtmlComoPdf(String html, File saidaPdf) throws IOException {
		Path pastaTemporaria = criarPastaTemporaria();
		File arquivoHtml = new File(pastaTemporaria.toFile().getAbsolutePath(), "arquivo.html");
		salvarTextoEmArquivo(html, arquivoHtml);

		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(arquivoHtml);
		renderer.layout();
		OutputStream out = new FileOutputStream(saidaPdf);
		try {
			renderer.createPDF(out);
		} catch (DocumentException e) {
			throw new IOException(e);
		}
	}

	public static void imprimirHtml(String html) throws IOException {
		Path pastaTemporaria = criarPastaTemporaria();
		File arquivoPdf = new File(pastaTemporaria.toFile().getAbsolutePath(), "arquivo.pdf");
		salvarHtmlComoPdf(html, arquivoPdf);

		PDDocument document = PDDocument.load(arquivoPdf);
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(document));
		try {
			job.print();
		} catch (PrinterException e) {
			throw new IOException(e);
		}
	}

}
