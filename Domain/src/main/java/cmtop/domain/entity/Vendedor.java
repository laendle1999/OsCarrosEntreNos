package cmtop.domain.entity;

import cmtop.domain.valueobject.TipoAcesso;

public class Vendedor {
	private String telefone, nome, rg, cpf, endereco, login, email;

	private TipoAcesso tipoAcesso;

	private int id;

	public Vendedor(int id, String telefone, String nome, String rg, String cpf, String endereco, String login,
			String email, TipoAcesso tipoAcesso) {
		this.id = id;
		this.telefone = telefone;
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.login = login;
		this.email = email;
		this.tipoAcesso = tipoAcesso;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoAcesso getTipoAcesso() {
		return tipoAcesso;
	}

	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}

	public int getId() {
		return id;
	}

}
