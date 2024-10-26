import java.util.List;

public interface MonedaDao {
    void generarMoneda(Moneda moneda);

    List<Moneda> listarMonedas();

    Moneda ListarPorNomenclatura(String nomenclatura);

    void actualizarStock(String nomenclatura, double nuevoStock);

    void ListarStock(boolean porNomenclatura);

}
