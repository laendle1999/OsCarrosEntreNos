package cmtop.busca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import cmtop.application.model.ModelGenerico;
import cmtop.application.model.VendaModel;
import cmtop.busca.CamposBusca.Campo;
import cmtop.busca.CamposBusca.TipoCampo;
import cmtop.domain.entity.Carro;
import cmtop.domain.entity.Cliente;
import cmtop.domain.entity.Venda;
import cmtop.domain.entity.Vendedor;
import cmtop.domain.repository.CarroRepository;
import cmtop.domain.repository.ClienteRepository;
import cmtop.domain.repository.VendaRepository;
import cmtop.domain.repository.VendedorRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.valueobject.ListenerConsultaComResposta;

public class BuscarVendaACliente extends Busca<Venda> {

	private Banco banco;

	private static Campo CLIENTE = new Campo(TipoCampo.TEXTO, "Nome cliente", "nome", "");

	@SuppressWarnings("unused")
	private static Campo RENAVAN = new Campo(TipoCampo.TEXTO, "Renavan", "renavan", "");

	private Cliente cliente;

	public BuscarVendaACliente(Banco banco, Cliente cliente, Consumer<Venda> callback) {
		super("Venda", "Selecionar venda", callback);

		this.banco = banco;
		this.cliente = cliente;
		this.definirCamposBusca(CLIENTE);
	}

	@Override
	protected void buscar(ValoresCamposBusca camposBusca, int limite,
			Consumer<List<? extends ModelGenerico>> callbackListaModel, Consumer<List<Venda>> callbackListaOriginal)

			throws IOException {

		Carro carro = null;
		Vendedor vend = null;
		VendaRepository vendaRepository = new VendaRepository(banco);

		vendaRepository.consultarVendasDeCliente(cliente.getId(), limite, new ListenerConsultaComResposta<Venda>() {

			@Override
			public void erro(Exception e) {
			}

			@Override
			public void resposta(List<Venda> resultados) {
				int tempoLimiteSegundos = 20;

				callbackListaOriginal.accept(resultados);

				List<VendaModel> lista = new ArrayList<>();
				List<Carro> carros = new ArrayList<>();

				CountDownLatch latchCarros = new CountDownLatch(resultados.size());
				resultados.forEach(resultado -> {
					try {
						new CarroRepository(banco).obterCarroPorId(resultado.getCarro(),
								new ListenerConsultaComResposta<Carro>() {

									public void resposta(List<Carro> registros) {
										Carro carro = registros.get(0);
										carros.add(carro);
										latchCarros.countDown();
									}

									@Override
									public void erro(Exception e) {
										carros.add(null);
										latchCarros.countDown();
									}
								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				// Esperar carros serem lidos
				try {
					latchCarros.await(tempoLimiteSegundos, TimeUnit.SECONDS);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				List<Vendedor> vendedores = new ArrayList<>();

				CountDownLatch latchVendedores = new CountDownLatch(resultados.size());
				resultados.forEach(resultado -> {
					try {
						new VendedorRepository(banco).obterVendedorPorId(resultado.getVendedor(),
								new ListenerConsultaComResposta<Vendedor>() {

									public void resposta(List<Vendedor> registros) {
										Vendedor vendedor = registros.get(0);
										vendedores.add(vendedor);
										latchVendedores.countDown();
									}

									@Override
									public void erro(Exception e) {
										vendedores.add(null);
										latchVendedores.countDown();
									}

								});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				// Esperar vendedores serem lidos
				try {
					latchVendedores.await(tempoLimiteSegundos, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// List<VendaModel> lista = new ArrayList<>();
				for (int i = 0; i < resultados.size(); i++) {
					lista.add(new VendaModel(resultados.get(i), carros.get(i), vendedores.get(i)));
				}
				callbackListaModel.accept(lista);
				resultados.forEach(resultado -> lista.add(new VendaModel(resultado, carro, vend)));
				callbackListaModel.accept(lista);
			}
		});

	}

}
