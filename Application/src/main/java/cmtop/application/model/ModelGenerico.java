package cmtop.application.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public abstract class ModelGenerico {

	private List<Coluna> colunas = new ArrayList<>();

	public static class Coluna {
		private String nome;

		private Property<String> valor;

		private boolean alteravel;

		public Coluna(String nome, Property<String> valor, boolean alteravel) {
			super();
			this.nome = nome;
			this.valor = valor;
			this.alteravel = alteravel;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public Property<String> getValor() {
			return valor;
		}

		public void setValor(Property<String> valor) {
			this.valor = valor;
		}

		public boolean alteravel() {
			return alteravel;
		}
	}

	protected void adicionarColuna(String nome, String valor, boolean alteravel) {
		colunas.add(new Coluna(nome, new SimpleStringProperty(valor), alteravel));
	}

	public List<Coluna> getColunas() {
		return colunas;
	}

}
