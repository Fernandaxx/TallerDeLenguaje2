package Transaccion;

import Activo.ActivoCripto;
import Activo.ActivoCriptoDAO;
import Activo.ActivoFiat;

import java.sql.*;
import java.time.LocalDateTime;

import Activo.ActivoFiatDAO;
import Moneda.Criptomoneda;
import Moneda.Fiat;
import Moneda.MonedaDAO;

public class GestorCompra {
    private ActivoFiatDAO activoFiatDAO = new ActivoFiatDAO();
    private ActivoCriptoDAO activoCriptoDAO = new ActivoCriptoDAO();
    private TransaccionDAO transaccionDAO = new TransaccionDAO();
    private MonedaDAO monedaDAO = new MonedaDAO();

    public void generarCompra(Compra compra) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            ActivoCripto activoCripto = new ActivoCripto(compra.getCantidad(), compra.getCripto().getNomenclatura());
            ActivoFiat activoFiat = new ActivoFiat(-compra.getCantidadFiat(), compra.getFiat().getNomenclatura());
            if (!activoCriptoDAO.activoExiste(c, compra.getCripto().getNomenclatura())) {
                activoCriptoDAO.generarActivoCripto(activoCripto);
            } else {
                activoCriptoDAO.actualizarActivo(c, activoCripto);
            }
            monedaDAO.actualizarMoneda(c, -compra.getCantidad(), compra.getCripto().getNomenclatura());
            activoFiatDAO.actualizarActivo(c, activoFiat);
            transaccionDAO.registrarTransaccion(c, compra);
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public Compra simularCompra(Criptomoneda cripto, Fiat fiat, double cantidad) {
        Compra compra = new Compra();
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");

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
            compra = new Compra(LocalDateTime.now(), fiat, cripto, equivalente);

            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return compra;
    }
}
