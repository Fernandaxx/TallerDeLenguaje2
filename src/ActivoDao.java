import java.sql.Connection;

public interface ActivoDAO {
    void generarActivo(Activo activo);

    void listarActivos(boolean esCripto, boolean ordenarPorNomenclatura);

}
