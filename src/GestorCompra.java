
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GestorCompra {
    private ActivoDaoImpl activoDAOImpl;
    private TransaccionDAO transaccionDAO;
    private MonedaDaoImpl monedaDAOImpl;

    public void simularCompra(String nomenclaturaCripto,String nomenclaturaFiat, double cantidad) {
        Connection c = null;
        Statement stm = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.BilleteraVirtual.db");
            if (!monedaDAOImpl.monedaExiste(c, nomenclaturaCripto, true) || !monedaDAOImpl.monedaExiste(c, nomenclaturaFiat, false)){
                throw new Exception("Moneda no existente");
            }

            double equivalenteDolarCripto=monedaDAOImpl.equivalenteDolar(c, nomenclaturaCripto);
            double equivalenteDolarFiat=monedaDAOImpl.equivalenteDolar(c, nomenclaturaFiat);

            double equivalente= (cantidad * equivalenteDolarFiat / equivalenteDolarCripto);

            
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
