package Transaccion;

import java.sql.*;
import java.time.LocalDateTime;

import Activo.ActivoCripto;
import Activo.ActivoCriptoDAO;
import Moneda.Criptomoneda;
import Moneda.MonedaDAO;

public class GestorSwap {
    private ActivoCriptoDAO activoDAO = new ActivoCriptoDAO();
    private MonedaDAO monedaDAO = new MonedaDAO();
    private TransaccionDAO transaccionDAO = new TransaccionDAO();

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
