package Activo;

import java.util.List;

public interface IActivoFiatDAO {

    void generarActivoFiat(ActivoFiat activoFiat);

    List<ActivoFiat> listarActivosFiat();

    void borrarActivoFiat(String nomenclatura);

}
