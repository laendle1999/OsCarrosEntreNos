package cmtop.persistence.test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.Registro;
import cmtop.persistence.entity.Tabela;
import cmtop.persistence.entity.TipoBanco;
import cmtop.persistence.valueobject.Condicao;
import cmtop.persistence.valueobject.ListenerConsulta;
import cmtop.persistence.valueobject.TipoCondicao;
import cmtop.persistence.valueobject.ValorLong;
import cmtop.persistence.valueobject.ValorString;

public class TabelaTest {

	@Test
	public void inserirRemoverTest() throws IOException, InterruptedException {
		Registro registro = new Registro(TipoBanco.DERBY);
		registro.set("rg", new ValorString("10"));
		registro.set("cpf", new ValorString("15"));
		registro.set("dt_nasc", new ValorLong(1543344888115L));
		registro.set("endereco", new ValorString("endereco"));
		registro.set("telefone1", new ValorString("12345678"));
		registro.set("nome", new ValorString("abc"));

		CountDownLatch latchInserir = new CountDownLatch(1);
		Tabela tabela = getBanco().getTabela("cliente");
		tabela.inserir(registro, new ListenerConsulta() {
			@Override
			public void sucesso(int resultadosAfetados) {
				latchInserir.countDown();
			}

			@Override
			public void erro(Exception e) {
				e.printStackTrace();
				latchInserir.countDown();
			}
		});
		latchInserir.await();

		// TODO buscar e verificar se resultado é igual

		Condicao condicao = new Condicao();
		condicao.add("rg", TipoCondicao.IGUAL, new ValorString("10"));
		condicao.add("nome", TipoCondicao.SIMILAR, new ValorString("abc"));

		CountDownLatch latchRemover = new CountDownLatch(1);
		tabela.remover(condicao, new ListenerConsulta() {
			@Override
			public void sucesso(int resultadosAfetados) {
				latchRemover.countDown();
			}

			@Override
			public void erro(Exception e) {
				e.printStackTrace();
				latchRemover.countDown();
			}
		});
		latchRemover.await();

		// TODO buscar e verificar se determinado registro foi removido

	}

	private static BancoServidorRedeLocal getBanco() throws IOException {
		return new BancoServidorRedeLocal(TipoBanco.DERBY, 5);
	}

}
