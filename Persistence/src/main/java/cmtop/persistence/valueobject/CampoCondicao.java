package cmtop.persistence.valueobject;

public class CampoCondicao {

	private String nome;

	private TipoCondicao condicao;

	private Valor valor;

	public CampoCondicao(String nome, TipoCondicao tipoCondicao, Valor valor) {
		this.nome = nome;
		this.condicao = tipoCondicao;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Valor getValor() {
		return valor;
	}

	public void setValor(Valor valor) {
		this.valor = valor;
	}

	public TipoCondicao getCondicao() {
		return condicao;
	}

	public void setCondicao(TipoCondicao condicao) {
		this.condicao = condicao;
	}

	public String toSQL() {
		StringBuilder source = new StringBuilder();

		source.append(getNome() + " ");

		switch (condicao) {
		case IGUAL:
			source.append("=");
			break;
		case DIFERENTE:
			source.append("<>"); // Mesmo que !=
			break;
		case MAIOR:
			source.append(">");
			break;
		case MENOR:
			source.append("<");
			break;
		case MAIOR_OU_IGUAL:
			source.append(">=");
			break;
		case MENOR_OU_IGUAL:
			source.append("<=");
			break;
		case SIMILAR:
			source.append("LIKE");
			break;
		default:
			throw new Error("Condição desconhecida: " + condicao);
		}

		source.append(" ");

		if (valor.getTipo() == TipoValor.STRING || condicao == TipoCondicao.SIMILAR) {
			source.append("'");
		}

		if (condicao == TipoCondicao.SIMILAR) {
			source.append("%");
		}

		if (valor.getTipo() == TipoValor.STRING || condicao == TipoCondicao.SIMILAR) {
			source.append(limparString(valor.toString()).replace("'", "''"));
		} else {
			source.append(valor);
		}

		if (condicao == TipoCondicao.SIMILAR) {
			source.append("%");
		}

		if (valor.getTipo() == TipoValor.STRING || condicao == TipoCondicao.SIMILAR) {
			source.append("'");
		}

		return source.toString();
	}

	public static String limparString(String string) {
		return string.replaceAll("\\p{C}", "");
	}

}
