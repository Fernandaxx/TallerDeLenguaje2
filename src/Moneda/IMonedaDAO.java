package Moneda;

import java.sql.Connection;
import java.util.List;

/**
 * La interfaz IMonedaDAO define los métodos necesarios para realizar
 * operaciones de acceso a datos sobre monedas (Fiat y Criptomonedas) en
 * la base de datos de la Billetera Virtual.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public interface IMonedaDAO {

    /**
     * Genera una nueva moneda (Fiat o Criptomoneda) en la base de datos.
     *
     * Si la moneda ya existe, se actualizará su stock.
     *
     * @param moneda La moneda a generar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    boolean generarMoneda(Moneda moneda);

    /**
     * Lista todas las monedas disponibles en la base de datos.
     *
     * @return Una lista de objetos Moneda (Fiat y Criptomonedas).
     */
    List<Moneda> listarMonedas();

    /**
     * Genera un stock aleatorio para las criptomonedas en la base de datos
     * y devuelve un informe de las actualizaciones realizadas.
     *
     * @return Una lista de strings con el informe de actualización de stock.
     */
    List<String> generarStock();

    /**
     * Lista el stock actual de las criptomonedas en la base de datos.
     *
     * @return Una lista de objetos Stock que contienen la nomenclatura y el stock.
     */
    List<Stock> listarStock();

    /**
     * Elimina una moneda de la base de datos según su nomenclatura.
     *
     * @param nomenclatura La nomenclatura de la moneda a eliminar.
     */
    void borrarMoneda(String nomenclatura);

    /**
     * Verifica si hay suficiente stock disponible para una moneda dada.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura de la moneda.
     * @param cantidad     La cantidad a verificar.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    boolean VerificarStock(Connection c, String nomenclatura, double cantidad);

    /**
     * Obtiene el valor en dólares de una moneda dada.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura de la moneda.
     * @return El valor en dólares de la moneda, o -1 si no se encuentra.
     */
    double equivalenteDolar(Connection c, String nomenclatura);

    /**
     * Verifica si una moneda existe en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura de la moneda a verificar.
     * @return true si la moneda existe, false en caso contrario.
     */
    boolean monedaExiste(Connection c, String nomenclatura);

    /**
     * Actualiza el stock de una moneda existente en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param stock        La cantidad de stock a agregar.
     * @param nomenclatura La nomenclatura de la moneda a actualizar.
     */
    void actualizarMoneda(Connection c, double stock, String nomenclatura);
}
