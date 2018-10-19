package cmtop.domain.entity;

import cmtop.domain.valueobject.StatusCarro;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ano;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + Float.floatToIntBits(custo);
		result = prime * result + (int) (dataEntrada ^ (dataEntrada >>> 32));
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		result = prime * result + ((renavan == null) ? 0 : renavan.hashCode());
		result = prime * result + ((statusCarro == null) ? 0 : statusCarro.hashCode());
		result = prime * result + Float.floatToIntBits(valorVenda);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carro other = (Carro) obj;
		if (ano != other.ano)
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (Float.floatToIntBits(custo) != Float.floatToIntBits(other.custo))
			return false;
		if (dataEntrada != other.dataEntrada)
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		if (renavan == null) {
			if (other.renavan != null)
				return false;
		} else if (!renavan.equals(other.renavan))
			return false;
		if (statusCarro != other.statusCarro)
			return false;
		if (Float.floatToIntBits(valorVenda) != Float.floatToIntBits(other.valorVenda))
			return false;
		return true;
	}
}
