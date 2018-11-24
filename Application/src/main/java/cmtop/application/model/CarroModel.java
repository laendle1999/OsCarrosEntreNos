package cmtop.application.model;

import cmtop.domain.entity.Carro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CarroModel {
	private final SimpleStringProperty marca;
	private final SimpleIntegerProperty ano;
	private final SimpleStringProperty cor;
	private final SimpleStringProperty modelo;

	public CarroModel(Carro carro) {
		this.marca = new SimpleStringProperty(carro.getMarca());
		this.ano = new SimpleIntegerProperty(carro.getAno());
		this.cor = new SimpleStringProperty(carro.getCor());
		this.modelo = new SimpleStringProperty(carro.getModelo());
	}

	public SimpleStringProperty getMarca() {
		return marca;
	}

	public SimpleIntegerProperty getAno() {
		return ano;
	}

	public SimpleStringProperty getCor() {
		return cor;
	}

	public SimpleStringProperty getModelo() {
		return modelo;
	}

}
