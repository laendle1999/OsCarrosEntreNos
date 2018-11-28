package cmtop.domain.entity;

import cmtop.domain.valueobject.TipoAcesso;

public class Vendedor {
	private String telefone1, nome, rg, cpf, endereco, login, email;

	private TipoAcesso tipoAcesso;

	private int id;

	private long dataNascimento;

	private String telefone2;

	public Vendedor(int id, String telefone1, String telefone2, String nome, long dataNascimento, String rg,
			String cpf, String endereco, String login, String email, TipoAcesso tipoAcesso) {
		this.id = id;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.login = login;
		this.email = email;
		this.tipoAcesso = tipoAcesso;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(long dataNascimento) {
		this.dataNascimento = dataNascimento;
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
