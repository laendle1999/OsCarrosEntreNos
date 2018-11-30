package cmtop.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cmtop.domain.entity.Cliente;
import cmtop.domain.entity.Compra;
import cmtop.domain.entity.Venda;
import cmtop.domain.repository.ClienteRepository;
import cmtop.domain.repository.CompraRepository;
import cmtop.domain.repository.VendaRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class RelatorioFinanceiro {

	private Banco banco;

	public RelatorioFinanceiro(Banco banco) {
		this.banco = banco;
	}

	public void gerarRelatorioFinanceiro(long dataInicio, int limiteRegistros,
			ListenerConsultaComResposta<String> listener) throws IOException {
		CompraRepository compraRepository = new CompraRepository(banco);
		compraRepository.obterComprasRealizadasApos(dataInicio, limiteRegistros,
				new ListenerConsultaComResposta<Compra>() {

					@Override
					public void erro(Exception e) {
						listener.erro(e);
					}

					@Override
					public void resposta(List<Compra> compras) {
						VendaRepository vendaRepository;
						try {
							vendaRepository = new VendaRepository(banco);
						} catch (IOException e) {
							listener.erro(e);
							return;
						}

						vendaRepository.obterVendasRealizadasApos(dataInicio, limiteRegistros,
								new ListenerConsultaComResposta<Venda>() {

									@Override
									public void erro(Exception e) {
										listener.erro(e);
									}

									@Override
									public void resposta(List<Venda> vendas) {
										StringBuilder s = new StringBuilder();

										s.append("<!DOCTYPE html><html><head><meta charset=\"utf-8\"/>");
										s.append("<title>Relatório financeiro</title>");
										s.append("<style>*{text-align:center; font-family: sans-serif;}</style>");
										s.append("<style>table{margin: 0 auto; width: 90%;}</style>");
										s.append("</head><body>");

										s.append("<h1>Relatório financeiro</h1>");

										s.append("<h2>Compras realizadas após "
												+ DateService.converterTimestampParaDataString(dataInicio) + "</h2>");
										adicionarTabelaCompras(s, compras);

										s.append("<h2>Vendas realizadas após "
												+ DateService.converterTimestampParaDataString(dataInicio) + "</h2>");
										try {
											adicionarTabelaVendas(s, vendas);
										} catch (IOException e) {
											listener.erro(e);
										}

										s.append("</body></html>");

										List<String> resposta = new ArrayList<String>();
										resposta.add(s.toString());
										listener.resposta(resposta);
									}
								});
					}
				});
	}

	private void adicionarTabelaCompras(StringBuilder s, List<Compra> compras) {
		s.append("<table border=\"1\">");
		s.append("<tr>");
		s.append("<th>Data</th><th>Custo</th><th>Fornecedor</th>");
		s.append("</tr>");
		for (int i = 0; i < compras.size(); i++) {
			s.append("<tr>");
			Compra compra = compras.get(i);
			s.append("<td>" + DateService.converterTimestampParaDataString(compra.getData()) + "</td>");
			s.append("<td>" + compra.getCusto() + "</td>");
			s.append("<td>" + compra.getNomeFornecedor() + "</td>");
			s.append("</tr>");
		}
		if (compras.isEmpty()) {
			s.append("<tr><td colspan=\"3\">Nenhuma compra</td></tr>");
		}
		s.append("</table>");
	}

	private void adicionarTabelaVendas(StringBuilder s, List<Venda> vendas) throws IOException {
		s.append("<table border=\"1\">");
		s.append("<tr>");
		s.append("<th>Data</th><th>Id cliente</th>");
		s.append("</tr>");
		for (int i = 0; i < vendas.size(); i++) {
			s.append("<tr>");
			Venda venda = vendas.get(i);
			s.append("<td>" + DateService.converterTimestampParaDataString(venda.getDataVenda()) + "</td>");

			CountDownLatch latch = new CountDownLatch(1);
			new ClienteRepository(banco).obterClientePorId(venda.getCliente(),
					new ListenerConsultaComResposta<Cliente>() {
						@Override
						public void resposta(List<Cliente> registros) {
							String nomeCliente = "";
							if (!registros.isEmpty()) {
								nomeCliente = registros.get(0).getNome();
							}
							s.append("<td>" + nomeCliente + "</td>");
							latch.countDown();
						}

						@Override
						public void erro(Exception e) {
							e.printStackTrace();
							latch.countDown();
						}
					});
			try {
				latch.await(3, TimeUnit.SECONDS);
			} catch (InterruptedException e1) {
			}
			s.append("</tr>");
		}
		if (vendas.isEmpty()) {
			s.append("<tr><td colspan=\"2\">Nenhuma venda</td></tr>");
		}
		s.append("</table>");
	}

}
