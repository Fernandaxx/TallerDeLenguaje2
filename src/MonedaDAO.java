public interface MonedaDao {
    void generarMoneda(Moneda moneda);

    void ListarMonedas(boolean ordenarPorNomenclatura);

    Moneda ListarPorNomenclatura(String nomenclatura);

    void actualizarStock(String nomenclatura, double nuevoStock);

    void ListarStock(boolean porNomenclatura);

}
