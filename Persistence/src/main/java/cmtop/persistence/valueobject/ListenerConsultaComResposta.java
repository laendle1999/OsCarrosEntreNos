package cmtop.persistence.valueobject;

import java.util.List;

public interface ListenerConsultaComResposta<TipoRequisitado> {
	void erro(Exception e);

	void resposta(List<TipoRequisitado> registros);
}