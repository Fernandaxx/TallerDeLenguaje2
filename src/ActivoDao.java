import java.util.List;

public interface ActivoDao {
    void generarActivo(Activo activo);

    List<Activo> listarActivos();

}
