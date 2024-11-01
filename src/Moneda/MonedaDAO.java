package Moneda;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * La clase MonedaDAO es responsable de realizar operaciones de acceso a datos
 * para las monedas (Fiat y Criptomonedas) en la base de datos de la Billetera
 * Virtual. Proporciona métodos para generar, listar, actualizar y eliminar
 * monedas, así como verificar su existencia y stock.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class MonedaDAO implements IMonedaDAO {

    /**
     * Genera una nueva moneda (Fiat o Criptomoneda) en la base de datos.
     *
     * Si la criptomoneda ya existe, se actualizará su stock.
     *
     * @param moneda La moneda a generar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean generarMoneda(Moneda moneda) {
        boolean exito = true;
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            if (moneda instanceof Criptomoneda) {
                Criptomoneda cripto = (Criptomoneda) moneda;
                if (monedaExiste(c, moneda.getNomenclatura()))
                    actualizarMoneda(c, cripto.getStock(), cripto.getNomenclatura());
                else
                    insertarCripto(c, cripto);
            } else if (!monedaExiste(c, moneda.getNomenclatura())) {
                Fiat fiat = (Fiat) moneda;
                insertarFiat(c, fiat);
            } else
                exito = false;
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return exito;
    }

    /**
     * Verifica si una moneda existe en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura de la moneda a verificar.
     * @return true si la moneda existe, false en caso contrario.
     */
    @Override
    public boolean monedaExiste(Connection c, String nomenclatura) {
        boolean existe = false;
        String sql = "SELECT 1 FROM MONEDA WHERE NOMENCLATURA = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            existe = pstmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return existe;
    }

    /**
     * Actualiza el stock de una criptomoneda existente en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param stock        La cantidad de stock a agregar.
     * @param nomenclatura La nomenclatura de la moneda a actualizar.
     */
    public void actualizarMoneda(Connection c, double stock, String nomenclatura) {
        try {
            String sql = "UPDATE MONEDA SET STOCK = STOCK + ? WHERE NOMENCLATURA = ?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setDouble(1, stock);
            pstmt.setString(2, nomenclatura);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Inserta una moneda Fiat en la base de datos.
     *
     * @param c    La conexión a la base de datos.
     * @param fiat La moneda Fiat a insertar.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    private void insertarFiat(Connection c, Fiat fiat) throws SQLException {
        String sql = "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(fiat.getTipo()));
            pstmt.setString(2, fiat.getNombre());
            pstmt.setString(3, fiat.getNomenclatura());
            pstmt.setDouble(4, fiat.getValor_dolar());
            pstmt.executeUpdate();
        }
    }

    /**
     * Inserta una Criptomoneda en la base de datos.
     *
     * @param c      La conexión a la base de datos.
     * @param cripto La Criptomoneda a insertar.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    private void insertarCripto(Connection c, Criptomoneda cripto) throws SQLException {
        String sql = "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, STOCK) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(cripto.getTipo()));
            pstmt.setString(2, cripto.getNombre());
            pstmt.setString(3, cripto.getNomenclatura());
            pstmt.setDouble(4, cripto.getValor_dolar());
            pstmt.setDouble(5, cripto.getVolatilidad());
            pstmt.setDouble(6, cripto.getStock());
            pstmt.executeUpdate();
        }
    }

    /**
     * Verifica si hay suficiente stock disponible para una moneda dada.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura de la moneda.
     * @param cantidad     La cantidad a verificar.
     * @return true si hay suficiente stock, false en caso contrario.
     */
    @Override
    public boolean VerificarStock(Connection c, String nomenclatura, double cantidad) {
        boolean suficiente = false;
        String sql = "SELECT STOCK FROM MONEDA WHERE NOMENCLATURA = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double disponible = rs.getDouble("STOCK");
                if (disponible >= cantidad)
                    suficiente = true;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return suficiente;
    }

    /**
     * Obtiene el valor en dólares de una moneda dada.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura de la moneda.
     * @return El valor en dólares de la moneda, o -1 si no se encuentra.
     */
    @Override
    public double equivalenteDolar(Connection c, String nomenclatura) {
        double valor = -1;
        String sql = "SELECT VALOR_DOLAR FROM MONEDA WHERE NOMENCLATURA = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                valor = rs.getDouble("VALOR_DOLAR");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return valor;
    }

    /**
     * Lista todas las monedas disponibles en la base de datos.
     *
     * @return Una lista de monedas (Fiat y Criptomonedas).
     */
    @Override
    public List<Moneda> listarMonedas() {
        List<Moneda> monedas = new LinkedList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA");
            while (rs.next()) {
                Moneda moneda;
                if (rs.getString("TIPO").charAt(0) == 'C') {
                    moneda = new Criptomoneda(rs.getString("TIPO").charAt(0), rs.getString("NOMBRE"),
                            rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"), rs.getDouble("VOLATILIDAD"),
                            rs.getDouble("STOCK"));
                } else {
                    moneda = new Fiat(rs.getString("TIPO").charAt(0), rs.getString("NOMBRE"),
                            rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"));
                }
                monedas.add(moneda);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return monedas;
    }

    /**
     * Genera un stock aleatorio para las criptomonedas en la base de datos
     * y devuelve un informe de las actualizaciones realizadas.
     *
     * @return Una lista de strings con el informe de actualización de stock.
     */
    @Override
    public List<String> generarStock() {
        List<String> informe = new LinkedList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            String sql = "UPDATE MONEDA SET STOCK = ? WHERE NOMENCLATURA = ?";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA");
            PreparedStatement pstmt = c.prepareStatement(sql);
            while (rs.next()) {
                if (rs.getString("TIPO").charAt(0) == 'C') {
                    String nomenclatura = rs.getString("NOMENCLATURA");
                    double numeroAleatorio = ThreadLocalRandom.current().nextDouble(0.0, 1000.0);
                    numeroAleatorio = Math.round(numeroAleatorio * 100.0) / 100.0;

                    pstmt.setDouble(1, numeroAleatorio);
                    pstmt.setString(2, nomenclatura);
                    pstmt.executeUpdate();
                    informe.add("Stock actualizado para: " + nomenclatura + "\n");
                }
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return informe;
    }

    /**
     * Lista el stock actual de las criptomonedas en la base de datos.
     *
     * @return Una lista de objetos Stock que contienen la nomenclatura y el stock.
     */
    @Override
    public List<Stock> listarStock() {
        Connection c = null;
        Statement stmt = null;
        List<Stock> stocks = new LinkedList<>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA");
            while (rs.next()) {
                if (rs.getString("TIPO").charAt(0) == 'C') {
                    Stock stock = new Stock(rs.getString("NOMENCLATURA"), rs.getDouble("STOCK"));
                    stocks.add(stock);
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return stocks;
    }

    /**
     * Elimina una moneda de la base de datos según su nomenclatura.
     *
     * @param nomenclatura La nomenclatura de la moneda a eliminar.
     */
    @Override
    public void borrarMoneda(String nomenclatura) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            String sql = "DELETE FROM MONEDA WHERE NOMENCLATURA = ? ";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new RuntimeException("No se encontró el activo cripto: " + nomenclatura);
            }
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Operación realizada con éxito");
    }
}
