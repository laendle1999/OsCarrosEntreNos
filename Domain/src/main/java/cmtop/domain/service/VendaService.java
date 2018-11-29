package cmtop.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import cmtop.domain.aggregate.Pagamento;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
import cmtop.domain.entity.Financiamento;
import cmtop.domain.entity.TrocaCarro;
import cmtop.domain.entity.ValorEntrada;
import cmtop.domain.entity.Venda;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.FinanciamentoRepository;
import cmtop.domain.repository.TrocaCarroRepository;
import cmtop.domain.repository.ValorEntradaRepository;
import cmtop.domain.repository.VendaRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.service.MyThread;
import cmtop.persistence.valueobject.ListenerConsulta;

public class VendaService {

	private Pagamento pagamento;

	private Carro carro;

	private Cliente cliente;

	private Vendedor vendedor;

	private Banco banco;

	private String numeroVenda;

	public VendaService(Banco banco) {
		this.banco = banco;
		pagamento = new Pagamento();
	}

	public void definirNumero(String numeroVenda) {
		this.numeroVenda = numeroVenda;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Carro getCarro() {
		return carro;
	}

	public void adicionarFinanciamento(Financiamento financiamento) {
		pagamento.adicionarFinanciamento(financiamento);
	}

	public void adicionarTrocaCarro(TrocaCarro trocaCarro) {
		pagamento.adicionarTrocaCarro(trocaCarro);
	}

	public void adicionarValorEntrada(ValorEntrada valorEntrada) {
		pagamento.adicionarValorEntrada(valorEntrada);
	}

	public void limparValoresEntrada() {
		pagamento.getValoresEntrada().clear();
	}

	public void limparTrocasCarro() {
		pagamento.getTrocasCarro().clear();
	}

	public void limparFinanciamentos() {
		pagamento.getFinanciamentos().clear();
	}

	public List<ValorEntrada> getValoresEntrada() {
		return pagamento.getValoresEntrada();
	}

	public List<TrocaCarro> getTrocasCarro() {
		return pagamento.getTrocasCarro();
	}

	public List<Financiamento> getFinanciamentos() {
		return pagamento.getFinanciamentos();
	}

	public void escolherCarro(Carro carro) {
		this.carro = carro;
	}

	public void finalizarVenda(ListenerConsulta listener) {
		new MyThread(new Runnable() {
			private Exception erroOcorrido = null;

			private int idVenda;

			@Override
			public void run() {
				long dataVenda = DateService.converterDataEmTimestamp(new Date());

				Venda venda = new Venda(-1, numeroVenda, dataVenda, cliente.getId(), vendedor.getId());
				venda.setCarro(carro.getId());

				CountDownLatch finalizarVendaLatch = new CountDownLatch(1 + pagamento.getTrocasCarro().size()
						+ pagamento.getFinanciamentos().size() + pagamento.getValoresEntrada().size());

				try {
					CountDownLatch vendaLatch = new CountDownLatch(1);
					new VendaRepository(banco).gravarVenda(venda, new ListenerConsulta() {
						@Override
						public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
							vendaLatch.countDown();
							if (chavesCriadas.isEmpty()) {
								erroOcorrido = new Exception("Entidade Venda não foi gravada");
								return;
							}
							idVenda = chavesCriadas.get(0).intValue();
						}

						@Override
						public void erro(Exception e) {
							e.printStackTrace();
							erroOcorrido = e;
							vendaLatch.countDown();
						}
					});
					try {
						// Aguardar entidade venda ser gravada
						vendaLatch.await();
					} catch (InterruptedException e1) {
					}
				} catch (IOException e) {
					e.printStackTrace();
					listener.erro(e);
					return;
				}

				try {
					new CarroRepository(banco).definirCarroVendido(carro, new ListenerConsulta() {
						@Override
						public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
							finalizarVendaLatch.countDown();
						}

						@Override
						public void erro(Exception e) {
							e.printStackTrace();
							erroOcorrido = e;
							finalizarVendaLatch.countDown();
						}
					});
				} catch (IOException e1) {
					e1.printStackTrace();
					listener.erro(e1);
					return;
				}

				// Salvar trocas de carro
				for (TrocaCarro trocaCarro : pagamento.getTrocasCarro()) {
					trocaCarro.setIdVenda(idVenda);
					try {
						new TrocaCarroRepository(banco).adicionarCarroTroca(trocaCarro, new ListenerConsulta() {
							@Override
							public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
								finalizarVendaLatch.countDown();
							}

							@Override
							public void erro(Exception e) {
								e.printStackTrace();
								erroOcorrido = e;
								finalizarVendaLatch.countDown();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
						listener.erro(e);
						return;
					}
				}

				// Salvar financiamentos
				for (Financiamento financiamento : pagamento.getFinanciamentos()) {
					financiamento.setIdVenda(idVenda);
					try {
						new FinanciamentoRepository(banco).adicionarFinanciamento(financiamento,
								new ListenerConsulta() {
									@Override
									public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
										finalizarVendaLatch.countDown();
									}

									@Override
									public void erro(Exception e) {
										e.printStackTrace();
										erroOcorrido = e;
										finalizarVendaLatch.countDown();
									}
								});
					} catch (IOException e) {
						e.printStackTrace();
						listener.erro(e);
						return;
					}
				}

				// Salvar valores de entrada
				for (ValorEntrada valorEntrada : pagamento.getValoresEntrada()) {
					valorEntrada.setIdVenda(idVenda);
					try {
						new ValorEntradaRepository(banco).adicionarValorEntrada(valorEntrada, new ListenerConsulta() {
							@Override
							public void sucesso(int resultadosAfetados, List<Long> chavesCriadas) {
								finalizarVendaLatch.countDown();
							}

							@Override
							public void erro(Exception e) {
								e.printStackTrace();
								erroOcorrido = e;
								finalizarVendaLatch.countDown();
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
						listener.erro(e);
						return;
					}
				}

				try {
					finalizarVendaLatch.await();
				} catch (InterruptedException e) {
				}

				if (erroOcorrido == null) {
					listener.sucesso(1, new ArrayList<Long>());
				} else {
					listener.erro(erroOcorrido);
				}

				venda = null;
				carro = null;
				pagamento = null;
			}
		}, "VendaService finalizarVenda").start();
	}
}
