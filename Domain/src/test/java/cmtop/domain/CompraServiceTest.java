	package cmtop.domain;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cmtop.domain.service.CompraService;
//import cmtop.persistence.entity.Banco;
import cmtop.domain.entity.Carro;
import cmtop.domain.valueobject.StatusCarro;
import cmtop.domain.repository.CompraRepository;
import cmtop.persistence.entity.Banco;
import cmtop.persistence.entity.BancoServidorRedeLocal;
import cmtop.persistence.entity.TipoBanco;

public class CompraServiceTest {
	
	private CompraService cs;
	private BancoServidorRedeLocal banco;
	private Carro carro;
	private StatusCarro statusCarro;
	private CompraRepository cr;
	
	@Before
	public void CriarCompraServiceTest() throws IOException {
		
		BancoServidorRedeLocal banco = new BancoServidorRedeLocal(TipoBanco.DERBY, 5);
		this.cs = new CompraService(banco);
		
		
		this.carro = new Carro(12773, "1", "11", "11", "teste", "teste", "amarelo", 2000, 10000, 10000, "01/01/2000", statusCarro.DISPONIVEL);
		
		System.out.println("oi2");
		cs.cadastrarNovaCompraDeCarro("Paulínia", "Benjamin Carros", "05/05/2000", 10000, carro);
		System.out.println("oi3");
		
		ESSE TA ERRADOOO blz
	}
	
	@Test
	public void testeBanco() throws IOException {
	
		
		
		System.out.println(cr.obterComprasRealizadasApos("01/01/2000", 15000));
		//Assert.assertEquals(,);
	}


}