package cmtop.domain.entity;

public class TrocaCarro {

	private String placa, modelo, marca, cor, local;

	private int ano;

	private float valorCarro;

	private String dataEntrada;

	private int id;

	private int idVenda;

	public TrocaCarro(int id, String placa, String modelo, String marca, String cor, String local, int ano,
			float valorCarro, String dataEntrada, int idVenda) {
		this.id = id;
		this.placa = placa;
		this.modelo = modelo;
		this.marca = marca;
		this.cor = cor;
		this.local = local;
		this.ano = ano;
		this.valorCarro = valorCarro;
		this.dataEntrada = dataEntrada;
		this.idVenda = idVenda;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public float getValorCarro() {
		return valorCarro;
	}

	public void setValorCarro(float valorCarro) {
		this.valorCarro = valorCarro;
	}

	public String getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public int getId() {
		return id;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

}
