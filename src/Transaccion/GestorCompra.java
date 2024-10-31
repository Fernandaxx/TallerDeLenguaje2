package Transaccion;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import Activo.ActivoFiatDAO;
import Moneda.MonedaDAOnew;

public class GestorCompra {
    private ActivoFiatDAO activoFiatDAO; // nose si necesita activo fiat o cripto
    private TransaccionDAO transaccionDAO;
    private MonedaDAOnew monedaDAO;

    private void generarCompra(Connection c) {

    }

    public void simularCompra(String nomenclaturaCripto, String nomenclaturaFiat, double cantidad) {
        Connection c = null;
        Scanner s = new Scanner(System.in);
        try {
            c = DriverManager.getConnection("jdbc:sqlite:test.BilleteraVirtual.db");

            // verifica si la moneda existe en la billetera
            if (!monedaDAO.monedaExiste(c, nomenclaturaCripto)
                    || !monedaDAO.monedaExiste(c, nomenclaturaFiat)) {
                throw new Exception("Moneda no existente");
            }
            // verifica si se tiene sufuciente activoFiat
            if (!activoDAOImpl.verificarStock(c, nomenclaturaFiat, cantidad, false)) {
                throw new Exception("Saldo insuficiente");
            }

            double equivalenteDolarCripto = monedaDAO.equivalenteDolar(c, nomenclaturaCripto);
            double equivalenteDolarFiat = monedaDAO.equivalenteDolar(c, nomenclaturaFiat);

            // verifica si hay suficiente stock en la billetera
            double equivalente = (cantidad * equivalenteDolarFiat / equivalenteDolarCripto);
            if (!monedaDAO.VerificarStock(c, nomenclaturaCripto, equivalente)) {
                throw new Exception("No hay suficiente stock de " + nomenclaturaCripto);
            }

            System.out.println("\nResumen de criptomoneda a comprar:");
            System.out.println("Cantidad a comprar de " + nomenclaturaCripto + ": " + equivalente);
            System.out.println("Cantidad a gastar de " + nomenclaturaFiat + ": " + cantidad);

            System.out.println("\n¿Está seguro que desea comprar este activo? (y/n)");
            String confirmacion = s.nextLine();
            if (confirmacion.equalsIgnoreCase("y")) {
                generarCompra(c);
                System.out.println("Moneda comprada exitosamente");
            } else {
                System.out.println("Operación cancelada");
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        s.close();
    }
}
