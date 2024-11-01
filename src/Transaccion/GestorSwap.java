package Transaccion;

import java.sql.*;
import java.time.LocalDateTime;
import Activo.ActivoCripto;
import Activo.ActivoCriptoDAO;
import Moneda.Criptomoneda;
import Moneda.MonedaDAO;

/**
 * La clase GestorSwap se encarga de gestionar las operaciones de intercambio
 * (swap)
 * entre criptomonedas. Proporciona métodos para simular swaps, realizar swaps y
 * calcular
 * el valor final de un swap.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class GestorSwap {
    private ActivoCriptoDAO activoDAO = new ActivoCriptoDAO();
    private MonedaDAO monedaDAO = new MonedaDAO();
    private TransaccionDAO transaccionDAO = new TransaccionDAO();

    /**
     * Simula un swap de criptomonedas, verificando la existencia de las
     * criptomonedas y la cantidad suficiente en los activos antes de realizar la
     * operación.
     *
     * @param criptoInicial La criptomoneda que se desea intercambiar.
     * @param cantidad      La cantidad de criptoInicial que se desea intercambiar.
     * @param criptoFinal   La criptomoneda que se desea recibir.
     * @param cantidadFinal La cantidad de criptoFinal que se espera recibir.
     * @return Un objeto Result que indica si la operación fue exitosa y un mensaje
     *         adicional.
     */
    public Result simularSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal,
            double cantidadFinal) {
        Result r = new Result(true, "Operacion exitosa");
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db")) {

            if (!activoDAO.activoExiste(c, criptoInicial.getNomenclatura())) {
                return new Result(false, "No posee " + criptoInicial.getNomenclatura() + " en sus activos");
            }
            if (!activoDAO.activoExiste(c, criptoFinal.getNomenclatura())) {
                activoDAO.generarActivoCripto(new ActivoCripto(0, criptoFinal.getNomenclatura()));
            }
            if (!activoDAO.verificarCantidad(c, criptoInicial.getNomenclatura(), cantidad)) {
                return new Result(false, "No posee cantidad suficiente de " + criptoInicial.getNomenclatura());
            }
            if (!monedaDAO.VerificarStock(c, criptoFinal.getNomenclatura(), cantidadFinal)) {
                return new Result(false, "No hay suficiente stock disponible de " + criptoFinal.getNomenclatura());
            }
            String resumen = String.format(
                    "SWAP: %.2f %s por %.2f %s",
                    cantidad,
                    criptoInicial.getNomenclatura(),
                    cantidadFinal,
                    criptoFinal.getNomenclatura());
            Swap swap = new Swap(criptoInicial, cantidad, criptoFinal, cantidadFinal, LocalDateTime.now(), resumen);
            realizarSwap(c, swap, r);
        } catch (SQLException e) {
            return new Result(false, "Error en la conexión a la base de datos: " + e.getMessage());
        }
        return r;
    }

    /**
     * Realiza el swap entre las criptomonedas, actualizando los activos y
     * registrando la transacción en la base de datos.
     *
     * @param c    La conexión a la base de datos.
     * @param swap El objeto Swap que contiene la información del intercambio.
     * @param res  El objeto Result que indicará el resultado de la operación.
     */
    private void realizarSwap(Connection c, Swap swap, Result res) {
        try {
            activoDAO.actualizarActivo(c,
                    new ActivoCripto(-swap.getCantidad(), swap.getCriptoEnvio().getNomenclatura()));

            monedaDAO.actualizarMoneda(c, -swap.getCantidadRecepcion(), swap.getCriptoRecepcion().getNomenclatura());

            activoDAO.actualizarActivo(c,
                    new ActivoCripto(swap.getCantidadRecepcion(), swap.getCriptoRecepcion().getNomenclatura()));
            transaccionDAO.registrarTransaccion(c, swap);

        } catch (Exception e) {
            new Result(false, e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Calcula el valor final de un swap en función del valor en dolar entre dos
     * criptomonedas.
     *
     * @param criptoInicial La criptomoneda que se desea intercambiar.
     * @param cantidad      La cantidad de criptoInicial que se desea intercambiar.
     * @param criptoFinal   La criptomoneda que se desea recibir.
     * @return El valor equivalente de la cantidad de criptoInicial en términos de
     *         criptoFinal.
     * @throws RuntimeException Si ocurre un error al calcular el valor final del
     *                          swap.
     */
    public double valorFinalSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db")) {
            double valorCripto1 = monedaDAO.equivalenteDolar(c, criptoInicial.getNomenclatura());
            double valorCripto2 = monedaDAO.equivalenteDolar(c, criptoFinal.getNomenclatura());
            return (cantidad * valorCripto1) / valorCripto2;
        } catch (Exception e) {
            throw new RuntimeException("Error al calcular valor final del swap: " + e.getMessage());
        }
    }
}
