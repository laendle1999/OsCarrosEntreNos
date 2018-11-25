package cmtop.busca;

import java.util.ArrayList;

public class CamposBusca extends ArrayList<CamposBusca.Campo> {

	private static final long serialVersionUID = 1L;

	public static enum TipoCampo {
		TEXTO
	}

	public static class Campo {
		private TipoCampo tipoCampo;
		private Object valorInicial;
		private String nome;

		public Campo(TipoCampo tipoCampo, String nome, Object valorInicial) {
			this.tipoCampo = tipoCampo;
			this.nome = nome;
			this.valorInicial = valorInicial;
		}

		public TipoCampo getTipoCampo() {
			return tipoCampo;
		}

		public String getNome() {
			return nome;
		}

		public Object getValorInicial() {
			return valorInicial;
		}
	}

	public CamposBusca() {
	}

	public CamposBusca(Campo... campos) {
		for (Campo c : campos) {
			add(c);
		}
	}
}
