
public interface ActivoDao {
    void generarActivo(Activo activo);

    void listarActivos(boolean esCripto, boolean ordenarPorNomenclatura);

}
