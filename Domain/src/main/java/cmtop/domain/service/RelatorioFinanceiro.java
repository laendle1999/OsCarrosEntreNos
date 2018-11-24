package cmtop.domain.service;

import java.io.IOException;
import java.util.List;

import cmtop.domain.entity.Compra;
import cmtop.domain.entity.Venda;
import cmtop.domain.repository.CompraRepository;
import cmtop.domain.repository.VendaRepository;
import cmtop.persistence.entity.Banco;

public class RelatorioFinanceiro {

	private Banco banco;

	public RelatorioFinanceiro(Banco banco) {
		this.banco = banco;
	}

	public String gerarRelatorioFinanceiro(String dataInicio, int limiteRegistros) throws IOException {
		CompraRepository compraRepository = new CompraRepository(banco);
		List<Compra> compras = compraRepository.obterComprasRealizadasApos(dataInicio, limiteRegistros);

		VendaRepository vendaRepository = new VendaRepository(banco);
		List<Venda> vendas = vendaRepository.obterVendasRealizadasApos(dataInicio, limiteRegistros);

		StringBuilder s = new StringBuilder();

		s.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\">");
		s.append("<title>Relatório financeiro</title>");
		s.append("<style>*{text-align:center; font-family: sans-serif;}</style>");
		s.append("</head><body>");

		s.append("<h1>Relatório financeiro</h1>");

		s.append("<h2>Compras realizadas após " + dataInicio + "</h2>");
		adicionarTabelaCompras(s, compras);

		s.append("<h2>Vendas realizadas após " + dataInicio + "</h2>");
		adicionarTabelaVendas(s, vendas);

		s.append("</body></html>");

		return null;
	}

	private void adicionarTabelaCompras(StringBuilder s, List<Compra> compras) {
		s.append("<table border=1>");
		s.append("<tr>");
		s.append("<th>Data</th><th>Custo</th><th>Fornecedor</th>");
		s.append("</tr>");
		for (int i = 0; i < compras.size(); i++) {
			s.append("<tr>");
			Compra compra = compras.get(i);
			s.append("<td>" + compra.getData() + "</td>");
			s.append("<td>" + compra.getCusto() + "</td>");
			s.append("<td>" + compra.getNomeFornecedor() + "</td>");
			s.append("</tr>");
		}
		if (compras.isEmpty()) {
			s.append("<tr><td colspan=3>Nenhuma compra</td></tr>");
		}
		s.append("</table>");
	}

	private void adicionarTabelaVendas(StringBuilder s, List<Venda> vendas) {
		s.append("<table border=1>");
		s.append("<tr>");
		s.append("<th>Data</th><th>Id cliente</th>");
		s.append("</tr>");
		for (int i = 0; i < vendas.size(); i++) {
			s.append("<tr>");
			Venda venda = vendas.get(i);
			s.append("<td>" + venda.getDataVenda() + "</td>");
			s.append("<td>" + venda.getCliente() + "</td>");
			s.append("</tr>");
		}
		if (vendas.isEmpty()) {
			s.append("<tr><td colspan=2>Nenhuma venda</td></tr>");
		}
		s.append("</table>");
	}

}
