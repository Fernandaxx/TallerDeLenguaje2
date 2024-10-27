
public interface ActivoDAO {
    void generarActivo(Activo activo);

    void listarActivos(boolean esCripto, boolean ordenarPorNomenclatura);

}
