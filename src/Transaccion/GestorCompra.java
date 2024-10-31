package Transaccion;

import Activo.ActivoCripto;
import Activo.ActivoCriptoDAO;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

import Activo.ActivoFiatDAO;
import Moneda.Criptomoneda;
import Moneda.Fiat;
import Moneda.MonedaDAO;

//hay que arreglar porque ingresa por teclado y modifica la base en la misma clase
public class GestorCompra {
    private ActivoFiatDAO activoFiatDAO;
    private ActivoCriptoDAO activoCriptoDAO;
    private TransaccionDAO transaccionDAO;
    private MonedaDAO monedaDAO;

    private void generarCompra(Connection c, Compra compra) throws SQLException {

        ActivoCripto activo = new ActivoCripto(compra.getCantidad(), compra.getCripto().getNomenclatura());

        if (!activoCriptoDAO.activoExiste(c, compra.getCripto().getNomenclatura())) {
            activoCriptoDAO.generarActivoCripto(activo);
        } else {
            activoCriptoDAO.actualizarActivo(c, activo);
        }
        monedaDAO.actualizarMoneda(c, -compra.getCantidad(), compra.getCripto().getNomenclatura());

    }

    public void simularCompra(Criptomoneda cripto, Fiat fiat, double cantidad) {
        Connection c = null;
        Scanner s = new Scanner(System.in);
        try {
            c = DriverManager.getConnection("jdbc:sqlite:test.BilleteraVirtual.db");

            // verifica si la moneda existe en la billetera
            if (!monedaDAO.monedaExiste(c, cripto.getNomenclatura())
                    || !monedaDAO.monedaExiste(c, fiat.getNomenclatura())) {
                throw new Exception("Moneda no existente");
            }
            // verifica si se tiene sufuciente activoFiat
            if (!activoFiatDAO.verificarCantidad(c, fiat.getNomenclatura(), cantidad)) {
                throw new Exception("Saldo insuficiente");
            }

            double equivalenteDolarCripto = monedaDAO.equivalenteDolar(c, cripto.getNomenclatura());
            double equivalenteDolarFiat = monedaDAO.equivalenteDolar(c, fiat.getNomenclatura());

            // verifica si hay suficiente stock en la billetera
            double equivalente = (cantidad * equivalenteDolarFiat / equivalenteDolarCripto);
            if (!monedaDAO.VerificarStock(c, cripto.getNomenclatura(), equivalente)) {
                throw new Exception("No hay suficiente stock de " + cripto.getNomenclatura());
            }

            System.out.println("\nResumen de criptomoneda a comprar:");
            System.out.println("Cantidad a comprar de " + cripto.getNomenclatura() + ": " + equivalente);
            System.out.println("Cantidad a gastar de " + fiat.getNomenclatura() + ": " + cantidad);

            System.out.println("\n¿Está seguro que desea comprar este activo? (y/n)");
            String confirmacion = s.nextLine();
            if (confirmacion.equalsIgnoreCase("y")) {
                Compra compra = new Compra(LocalDateTime.now(), fiat.getNomenclatura(), cripto.getNomenclatura(),
                        cantidad);
                generarCompra(c, compra);
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
