package Activo;

import java.util.List;

public interface IActivoCriptoDAO {

    void generarActivoCripto(ActivoCripto activoCripto);

    List<ActivoCripto> listarActivosCripto();

    void borrarActivoCripto(String dato);

}
