
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class GestorCompra {
    private ActivoDaoImpl activoDAOImpl;
    private TransaccionDAO transaccionDAO;
    private MonedaDaoImpl monedaDAOImpl;

    public void simularCompra(String nomenclaturaCripto,String nomenclaturaFiat, double cantidad) {
        Connection c = null;
        Statement stm = null;
        Scanner s = new Scanner(System.in);
        try {
            c = DriverManager.getConnection("jdbc:sqlite:test.BilleteraVirtual.db");
            //verifica si la moneda existe en la billetera
            if (!monedaDAOImpl.monedaExiste(c, nomenclaturaCripto, true) || !monedaDAOImpl.monedaExiste(c, nomenclaturaFiat, false)){
                throw new Exception("Moneda no existente");
            }
            //verifica que h
            activoDAOImpl.verificarStock(c, nomenclaturaFiat, cantidad, false);

            double equivalenteDolarCripto=monedaDAOImpl.equivalenteDolar(c, nomenclaturaCripto);
            double equivalenteDolarFiat=monedaDAOImpl.equivalenteDolar(c, nomenclaturaFiat);
            
            double equivalente= (cantidad * equivalenteDolarFiat / equivalenteDolarCripto);

            System.out.println("\nResumen de criptomoneda a comprar:");
            System.out.println("Cantidad a comprar de " + nomenclaturaCripto +": "+ equivalente);
            System.out.println("Cantidad a gastar de " + nomenclaturaFiat + ": "+ cantidad);

            System.out.println("\n¿Está seguro que desea comprar este activo? (y/n)");
            String confirmacion = s.nextLine();
            if (confirmacion.equalsIgnoreCase("y")) {
                //modificar la base de datos
                System.out.println("Activo ingresado exitosamente");
            } 
            else {
                System.out.println("Operación cancelada");
            }
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
