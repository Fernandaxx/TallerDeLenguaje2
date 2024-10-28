import java.sql.*;
import java.time.LocalDate;

public class GestorSwap implements TransaccionDAO {
    private ActivoDaoImpl activoDAO;
    private MonedaDaoImpl monedaDAO;
    private TransaccionDaoImpl transaccionDAO;

    public void simularSwap(Criptomoneda criptoInicial, double cantidad, Criptomoneda criptoFinal) {
        Connection c = null;
        Statement stm = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.BilleteraVirtual.db");
            if (!activoDAO.activoExiste(c, criptoInicial.getNomenclatura(), true) ||
                    !activoDAO.activoExiste(c, criptoFinal.getNomenclatura(), true)) {
                System.out.println("No posee alguno de los activos solicitados");
                return;
            }

            // 3. Calcular el equivalente en la cripto destino
            double valorCripto1 = monedaDAO.equivalenteDolar(c, criptoInicial.getNomenclatura());
            double valorCripto2 = monedaDAO.equivalenteDolar(c, criptoFinal.getNomenclatura());
            double cantidadFinal = (cantidad * valorCripto1) / valorCripto2;

            if (!activoDAO.verificarStock(c, criptoInicial.getNomenclatura(), cantidad)
                    || !activoDAO.verificarStock(c, criptoFinal.getNomenclatura(), cantidadFinal)) {
                System.out.println("Stock insuficiente para realizar el swap");
                return;
            }

            // 4. Crear objeto Swap para representar la transacción
            Swap swap = new Swap(
                    criptoInicial,
                    cantidad,
                    criptoFinal,
                    cantidadFinal,
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
            activoDAO.actualizarActivo(c,
                    new ActivoCripto(-swap.getCantidad(), swap.getCriptoEnvio().getNomenclatura()), true);

            // 2. Actualizar saldo de cripto destino (sumar)
            activoDAO.actualizarActivo(c,
                    new ActivoCripto(swap.getCantidadRecepcion(), swap.getCriptoRecepcion().getNomenclatura()), true);
            // 3. Registrar la transacción
            String descripcion = String.format(
                    "SWAP: %f %s por %f %s",
                    swap.getCantidad(),
                    swap.getCriptoEnvio(),
                    swap.getCantidadRecepcion(),
                    swap.getCriptoRecepcion());

            transaccionDAO.registrarTransaccion(c, swap, descripcion);

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
