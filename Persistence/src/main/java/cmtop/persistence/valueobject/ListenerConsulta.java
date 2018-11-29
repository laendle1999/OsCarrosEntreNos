package cmtop.persistence.valueobject;

import java.util.List;

public interface ListenerConsulta {
	void sucesso(int resultadosAfetados, List<Long> chaves);

	void erro(Exception e);
}