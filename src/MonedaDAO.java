public interface MonedaDAO {
    void generarMoneda(Moneda moneda);

    void ListarMonedas(boolean ordenarPorNomenclatura);

    void GenerarStock();

    void ListarStock(boolean porNomenclatura);

}
