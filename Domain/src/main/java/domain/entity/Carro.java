package domain.entity;

import domain.valueobject.StatusCarro;

public class Carro {

	private String numero, placa, renavan, modelo, marca, cor;

	private int ano;

	private float valorVenda, custo;

	private long dataEntrada;

	private StatusCarro statusCarro;

	public Carro(String numero, String placa, String renavan, String modelo, String marca, String cor, int ano,
			float valorVenda, float custo, long dataEntrada, StatusCarro statusCarro) {
		this.numero = numero;
		this.placa = placa;
		this.renavan = renavan;
		this.modelo = modelo;
		this.marca = marca;
		this.cor = cor;
		this.ano = ano;
		this.valorVenda = valorVenda;
		this.custo = custo;
		this.dataEntrada = dataEntrada;
		this.statusCarro = statusCarro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavan() {
		return renavan;
	}

	public void setRenavan(String renavan) {
		this.renavan = renavan;
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public float getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(float valorVenda) {
		this.valorVenda = valorVenda;
	}

	public float getCusto() {
		return custo;
	}

	public void setCusto(float custo) {
		this.custo = custo;
	}

	public long getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(long dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public StatusCarro getStatusCarro() {
		return statusCarro;
	}

	public void setStatusCarro(StatusCarro statusCarro) {
		this.statusCarro = statusCarro;
	}

}
