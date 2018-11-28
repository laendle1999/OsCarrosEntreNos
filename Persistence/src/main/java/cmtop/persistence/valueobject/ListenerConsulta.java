package cmtop.persistence.valueobject;

public interface ListenerConsulta {
	void sucesso(int resultadosAfetados);

	void erro(Exception e);
}