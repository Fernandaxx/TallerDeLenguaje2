package Activo;

import java.util.List;

public interface IActivoCriptoDAO {

    boolean generarActivoCripto(ActivoCripto activoCripto);

    List<ActivoCripto> listarActivosCripto();

    void borrarActivoCripto(String dato);

}
