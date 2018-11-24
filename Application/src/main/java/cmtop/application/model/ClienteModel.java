package cmtop.application.model;

import cmtop.domain.entity.Cliente;
import javafx.beans.property.SimpleStringProperty;

public class ClienteModel {
	private final SimpleStringProperty nome;
	private final SimpleStringProperty cpf;
	private final SimpleStringProperty rg;
	private final SimpleStringProperty endereco;
	private final SimpleStringProperty telefone1;

	public ClienteModel(Cliente cliente) {
		this.nome = new SimpleStringProperty(cliente.getNome());
		this.cpf = new SimpleStringProperty(cliente.getCpf());
		this.telefone1 = new SimpleStringProperty(cliente.getTelefone1());
		this.endereco = new SimpleStringProperty(cliente.getEndereco());
		this.rg = new SimpleStringProperty(cliente.getRg());
	}

	public SimpleStringProperty getRg() {
		return rg;
	}

	public SimpleStringProperty getEndereco() {
		return endereco;
	}

	public SimpleStringProperty getNome() {
		return nome;
	}

	public SimpleStringProperty getCpf() {
		return cpf;
	}

	public SimpleStringProperty getTelefone1() {
		return telefone1;
	}

}
