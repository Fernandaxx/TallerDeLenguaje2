package Moneda;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

//Borrar la anterior y el new
public class MonedaDAO implements IMonedaDAO {

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

    public void actualizarMoneda(Connection c, double stock, String nomenclatura) {
        try {
            String sql = "UPDATE MONEDA SET  STOCK = STOCK + ? WHERE NOMENCLATURA = ?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setDouble(1, stock);
            pstmt.setString(2, nomenclatura);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

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

    @Override
    public boolean VerificarStock(Connection c, String nomenclatura, double cantidad) {
        boolean suficiente = false;
        String sql = "SELECT STOCK FROM MONEDA WHERE NOMENCLATURA = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            ResultSet rs = pstmt.executeQuery();
            double disponible = rs.getDouble("STOCK");
            if (disponible >= cantidad)
                suficiente = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return suficiente;
    }

    @Override
    public double equivalenteDolar(Connection c, String nomenclatura) {
        double valor = -1;
        String sql = "SELECT VALOR_DOLAR FROM MONEDA WHERE NOMENCLATURA = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            ResultSet rs = pstmt.executeQuery();
            valor = rs.getDouble("VALOR_DOLAR");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return valor;
    }

    @Override
    public List<Moneda> listarMonedas() {

        List<Moneda> monedas = new LinkedList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
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

    @Override
    public List<String> generarStock() {
        List<String> informe = new LinkedList<String>();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
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
        System.out.println("Operation done successfully");
    }

}
