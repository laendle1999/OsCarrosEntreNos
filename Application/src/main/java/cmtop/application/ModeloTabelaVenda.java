package cmtop.application;

import javafx.beans.property.*;

public static class ModeloTabelaVenda{
	private final SimpleStringProperty definicao;
	private final SimpleStringProperty explicacao;
	
	private ModeloTabelaVenda(String def, String exp) {
		this.definicao =  new SimpleStringProperty(def);
		this.explicacao = new SimpleStringProperty(exp);
	}

	public SimpleStringProperty getDefinicao() {
		return definicao;
	}

	public SimpleStringProperty getExplicacao() {
		return explicacao;
	}
	
	
	
}
