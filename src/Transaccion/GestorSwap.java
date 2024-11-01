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

    public void simularSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");
            if (!activoDAO.activoExiste(c, criptoInicial.getNomenclatura())
                    || !activoDAO.verificarCantidad(c, criptoInicial.getNomenclatura(), cantidad)) {
                throw new RuntimeException("No posee cantidad suficiente de " + criptoInicial.getNomenclatura());

            }

            // Calcular el equivalente en la cripto destino
            double cantidadFinal = valorFinalSwap(criptoInicial, cantidad, criptoFinal);
            if (!monedaDAO.VerificarStock(c, criptoFinal.getNomenclatura(), cantidadFinal))
                ;

            // 4. Crear objeto Swap para representar la transacción
            Swap swap = new Swap(
                    criptoInicial,
                    cantidad,
                    criptoFinal,
                    cantidadFinal,
                    LocalDateTime.now());
            // 5. Realizar la transacción
            realizarSwap(c, swap);
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": este error" + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    private void realizarSwap(Connection c, Swap swap) throws Exception {
        try {
            // Actualizar saldo de cripto origen (restar)
            activoDAO.actualizarActivo(c,
                    new ActivoCripto(-swap.getCantidad(), swap.getCriptoEnvio().getNomenclatura()));

            // Actualizar saldo de cripto destino (sumar)
            activoDAO.actualizarActivo(c,
                    new ActivoCripto(swap.getCantidadRecepcion(), swap.getCriptoRecepcion().getNomenclatura()));
            // 3. Registrar la transacción
            String descripcion = String.format(
                    "SWAP: %f %s por %f %s",
                    swap.getCantidad(),
                    swap.getCriptoEnvio(),
                    swap.getCantidadRecepcion(),
                    swap.getCriptoRecepcion());

            transaccionDAO.registrarTransaccion(swap, descripcion);
            System.out.println("Swap realizado exitosamente");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(1);
        }
    }

    public double valorFinalSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal) {
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            double valorCripto1 = monedaDAO.equivalenteDolar(c, criptoInicial.getNomenclatura());
            double valorCripto2 = monedaDAO.equivalenteDolar(c, criptoFinal.getNomenclatura());
            double cantidadFinal = (cantidad * valorCripto1) / valorCripto2;
            return cantidadFinal;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
}
