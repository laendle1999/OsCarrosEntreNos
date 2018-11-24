package cmtop.application.model;

import cmtop.domain.entity.Carro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;

public class CarroModel {
	private final SimpleStringProperty marca;
	private final SimpleIntegerProperty ano;
	private final SimpleStringProperty cor;
	private final SimpleStringProperty modelo;
	private final SimpleFloatProperty preco;
	private final SimpleStringProperty placa;
	private final SimpleIntegerProperty codigo;
	

	public CarroModel(Carro carro) {
		this.marca = new SimpleStringProperty(carro.getMarca());
		this.ano = new SimpleIntegerProperty(carro.getAno());
		this.cor = new SimpleStringProperty(carro.getCor());
		this.modelo = new SimpleStringProperty(carro.getModelo());
		this.preco = new SimpleFloatProperty(carro.getCusto());
		this.placa = new SimpleStringProperty(carro.getPlaca());
		this.codigo = new SimpleIntegerProperty(carro.getId());
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

	public SimpleFloatProperty getPreco() {
		return preco;
	}

	public SimpleStringProperty getPlaca() {
		return placa;
	}

	public SimpleIntegerProperty getCodigo() {
		return codigo;
	}

	
}
