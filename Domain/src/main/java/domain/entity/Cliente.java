package domain.entity;

public class Cliente {
	private String nome, rg, cpf, endereco, telefone;

	private long dataNascimento;

	public Cliente(String nome, String rg, String cpf, String endereco, String telefone, long dataNascimento) {
		super();
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public long getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(long dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
