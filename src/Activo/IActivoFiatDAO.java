package Activo;

import java.util.List;

public interface IActivoFiatDAO {

    boolean generarActivoFiat(ActivoFiat activoFiat);

    List<ActivoFiat> listarActivosFiat();

    void borrarActivoFiat(String nomenclatura);

}
