import java.util.List;

public interface MonedaDAO {
    void CrearMonedas(Moneda moneda);

    List<Moneda> listarMonedas();

    Moneda ListarPorNomenclatura(String nomenclatura);

    void generarStock();
}
