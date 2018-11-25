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

		public Coluna(String nome, Property<String> valor) {
			super();
			this.nome = nome;
			this.valor = valor;
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
	}

	protected void adicionarColuna(String nome, String valor) {
		colunas.add(new Coluna(nome, new SimpleStringProperty(valor)));
	}

	public List<Coluna> getColunas() {
		return colunas;
	}

}
