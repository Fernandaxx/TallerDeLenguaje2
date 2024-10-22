import java.util.List;

public interface ActivoDao {
    void GenerarActivo(String nomenclatura, double cantidad);

    List<Activo> listarActivos();

}
