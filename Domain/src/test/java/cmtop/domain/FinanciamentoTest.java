package cmtop.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cmtop.domain.entity.Financiamento;

public class FinanciamentoTest {
	
	private Financiamento financiamento;

	@Before
	public void criarFinanciamento() {
		this.financiamento= new Financiamento(null,0,0);
	}

	@Test
	public void testeBanco() {
			
		financiamento.setBanco("couro"); 
		Assert.assertEquals("couro", financiamento.getBanco());
	}
	
	@Test
	public void testeValorFinanciado() {
			
		financiamento.setValorFinanciado(30000); 
		//System.out.println(financiamento.getValorFinanciado());
		Assert.assertEquals(30000, financiamento.getValorFinanciado(), 0);
	}
	
	@Test
	public void testeNumeroParcelas() {
			
		financiamento.setNumeroParcelas(100); 
		Assert.assertEquals(100, financiamento.getNumeroParcelas());
	}


}
