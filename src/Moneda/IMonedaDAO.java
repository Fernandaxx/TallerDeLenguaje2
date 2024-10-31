package Moneda;

import java.sql.Connection;
import java.util.List;

public interface IMonedaDAO {
    boolean generarMoneda(Moneda moneda);

    List<Moneda> listarMonedas();

    void generarStock();

    List<Stock> listarStock();

    void borrarMoneda(String nomenclatura);

    boolean VerificarStock(Connection c, String nomenclatura, double cantidad);

    public double equivalenteDolar(Connection c, String nomenclatura);

    public boolean monedaExiste(Connection c, String nomenclatura);

}
