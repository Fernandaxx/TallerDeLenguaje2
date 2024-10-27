import java.sql.*;
import java.time.LocalDate;

public class GestorSwap {
    private ActivoDAO activoDAO;
    private TransaccionDAO transaccionDAO;
    private MonedaDAO monedaDAO;

    @Override
    public void simularSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal) {
        Connection c = null;
        Statement stm = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.BilleteraVirtual.db");
            if (!activoDAO.verificarActivoExistente(criptoInicial.getNomenclatura()) ||
                    !activoDAO.verificarActivoExistente(criptoFinal.getNomenclatura())) {
                throw new Exception("No posee alguno de los activos solicitados");
            }

            if (!activoDAO.verificarStockDisponible(criptoInicial.getNomenclatura(), cantidad)) {
                throw new Exception("Stock insuficiente para realizar el swap");
            }
            // 3. Calcular el equivalente en la cripto destino
            double valorCripto1 = monedaDAO.obtenerValorCripto(criptoInicial);
            double valorCripto2 = monedaDAO.obtenerValorCripto(criptoFinal);
            double cantidadDestino = (cantidad * valorCripto1) / valorCripto2;

            // 4. Crear objeto Swap para representar la transacción
            Swap swap = new Swap(
                    criptoInicial,
                    cantidad,
                    criptoFinal,
                    cantidadDestino,
                    LocalDate.now());
            // 5. Realizar la transacción
            realizarSwap(c, swap);
        } catch (

        Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    private void realizarSwap(Connection c, Swap swap) throws Exception {
        try {
            // 1. Actualizar saldo de cripto origen (restar)
            activoDAO.actualizarSaldo(
                    c,
                    swap.getCriptoEnvio(),
                    -swap.getCantidad());

            // 2. Actualizar saldo de cripto destino (sumar)
            activoDAO.actualizarSaldo(
                    c,
                    swap.getCriptoEnvio(),
                    swap.getCantidadRecepcion());

            // 3. Registrar la transacción
            String descripcion = String.format(
                    "SWAP: %f %s por %f %s",
                    swap.getCantidad(),
                    swap.getCriptoEnvio()
                    swap.getCantidadRecepcion(),
                    swap.getCriptoRecepcion());

            transaccionDAO.registrarTransaccion(c, "SWAP", descripcion);

            c.commit(); // Confirmar transacción
            System.out.println("Swap realizado exitosamente");

        } catch (Exception e) {
            if (c != null) {
                c.rollback(); // Deshacer cambios si hay error
            }
            throw e;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

}
