package Moneda;

import java.sql.;import java.util.;

import java.util.concurrent.ThreadLocalRandom;

//Borrar la anterior y el new
public class MonedaDAOnew implements IMonedaDAO {

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
        try  {
            String sql = "UPDATE MONEDA SET  STOCK = STOCK + ? WHERE NOMENCLATURA = ?";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setDouble(1, stock);
            pstmt.setString(2,nomenclatura);
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }     
            System.out.println("Moneda actualizada exitosamente.");
        }


    private void insertarMoneda(Connection c, Moneda moneda) throws SQLException {
        
        try  {
            PreparedStatement pstmt = c.prepareStatement(moneda instanceof Criptomoneda
            ? "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, STOCK) VALUES (?, ?, ?, ?, ?, ?)"
                : "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, String.valueOf(moneda.getTipo()));
            pstmt.setString(2, moneda.getNombre());
            pstmt.setString(3, moneda.getNomenclatura());
            pstmt.setDouble(4, moneda.getValor_dolar());

            if (moneda instanceof Criptomoneda) {
                Criptomoneda cripto = (Criptomoneda) moneda;
                pstmt.setDouble(5, cripto.getVolatilidad());
                pstmt.setDouble(6, cripto.getStock());
            }

            pstmt.executeUpdate();
            System.out.println("Moneda insertada exitosamente.");
        } catch (SQLException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void generarMoneda(Moneda moneda) {
        if (moneda == null) {
            System.err.println("No ingreso una moneda");
            return;
        }
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");

            // Verificar si la moneda ya existe; actualizar o insertar según sea el caso
            if (monedaExiste(c, moneda.getNomenclatura()) && (moneda instanceof Criptomoneda)) {
                Criptomoneda cripto = (Criptomoneda)moneda;
                actualizarMoneda(c, cripto.getStock(),cripto.getNomenclatura());
            } else {
                insertarMoneda(c, moneda);
            }

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }
    @Override
    public List<Moneda> listarMonedas() {

        List<Moneda> monedas = new LinkedList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
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
        System.out.println("Operation done successfully");
        return monedas;
    }
    /* 
     * @Override
     * public void listarMonedas(boolean ordenarPorNomenclatura) {
     * 
     * List<Moneda> monedas = new LinkedList<>();
     * try {
     * Connection c =
     * DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
     * c.setAutoCommit(false);
     * System.out.println("Opened database successfully");
     * Statement stmt = c.createStatement();
     * ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA");
     * 
     * while (rs.next()) {
     * Moneda moneda;
     * if (rs.getString("TIPO").charAt(0) == 'C') {
     * moneda = new Criptomoneda(rs.getString("TIPO").charAt(0),
     * rs.getString("NOMBRE"),
     * rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"),
     * rs.getDouble("VOLATILIDAD"),
     * rs.getDouble("STOCK"));
     * } else {
     * moneda = new Fiat(rs.getString("TIPO").charAt(0), rs.getString("NOMBRE"),
     * rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"));
     * }
     * monedas.add(moneda);
     * }
     * 
     * if (ordenarPorNomenclatura) {
     * Collections.sort(monedas, new ComparatorNomenclaturaMoneda());
     * } else {
     * Collections.sort(monedas, new ComparatorValorDolar());
     * }
     * for (Moneda act : monedas) {
     * System.err.println(act.toString());
     * }
     * rs.close();
     * stmt.close();
     * c.close();
     * } catch (Exception e) {
     * System.err.println(e.getClass().getName() + ": " + e.getMessage());
     * System.exit(1);
     * }
     * System.out.println("Operation done successfully");
     * 
     * }
     */

    @Override
    public void generarMonedaCripto(Criptomoneda criptomoneda) {
        if (criptomoneda == null) {
            System.err.println("No ingreso una moneda");
            return;
        }
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");

            // Verificar si la moneda ya existe; actualiza o inserta según sea el caso
            if (monedaExiste(c, criptomoneda.getNomenclatura())) {
                actualizarCripto(c, criptomoneda);
            } else {
                insertarCripto(c, criptomoneda);
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    // @Override
    // public void listarMonedas(boolean ordenarPorNomenclatura) {

    // List<Moneda> monedas = new LinkedList<>();
    // try {
    // Connection c =
    // DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
    // c.setAutoCommit(false);
    // System.out.println("Opened database successfully");
    // Statement stmt = c.createStatement();
    // ResultSet rs = stmt.executeQuery("SELECT FROM MONEDA");

    // while (rs.next()) {
    // Moneda moneda;
    // if (rs.getString("TIPO").charAt(0) == 'C') {
    // moneda = new Criptomoneda(rs.getString("TIPO").charAt(0),
    // rs.getString("NOMBRE"),
    // rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"),
    // rs.getDouble("VOLATILIDAD"),
    // rs.getDouble("STOCK"));
    // } else {
    // moneda = new Fiat(rs.getString("TIPO").charAt(0), rs.getString("NOMBRE"),
    // rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"));
    // }
    // monedas.add(moneda);
    // }

    // if (ordenarPorNomenclatura) {
    // Collections.sort(monedas, new ComparatorNomenclaturaMoneda());
    // } else {
    // Collections.sort(monedas, new ComparatorValorDolar());
    // }
    // for (Moneda act : monedas) {
    // System.err.println(act.toString());
    // }
    // rs.close();
    // stmt.close();
    // c.close();
    // } catch (Exception e) {
    // System.err.println(e.getClass().getName() + ": " + e.getMessage());
    // System.exit(1);
    // }
    // System.out.println("Operation done successfully");

    // }

    // @Override
    // public void generarStock() {

    // try {
    // Connection c =
    // DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
    // c.setAutoCommit(false);
    // System.out.println("Opened database successfully");
    // String sql = "UPDATE MONEDA SET STOCK = ? WHERE NOMENCLATURA = ?";
    // Statement stmt = c.createStatement();
    // ResultSet rs = stmt.executeQuery("SELECT FROM MONEDA");
    // PreparedStatement pstmt = c.prepareStatement(sql);
    // while (rs.next()) {
    // if (rs.getString("TIPO").charAt(0) == 'C') {
    // String nomenclatura = rs.getString("NOMENCLATURA");
    // double numeroAleatorio = ThreadLocalRandom.current().nextDouble(0.0, 1000.0);
    // numeroAleatorio = Math.round(numeroAleatorio 100.0) / 100.0;

    // pstmt.setDouble(1, numeroAleatorio);
    // pstmt.setString(2, nomenclatura);
    // pstmt.executeUpdate();
    // System.out.println("Stock actualizado para: " + nomenclatura);
    // }
    // }
    // c.commit();
    // } catch (Exception e) {
    // System.err.println(e.getClass().getName() + ": " + e.getMessage());
    // System.exit(1);
    // }
    // System.out.println("Operation done successfully");
    // }

    @Override
    public List<Stock> listarStock() {
        Connection c = null;
        Statement stmt = null;
        List<Stock> stocks = new LinkedList<>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT   FROM MONEDA");
            while (rs.next()) {
                Stock stock = new Stock(rs.getString("NOMBRE"), rs.getDouble("STOCK"));
                stocks.add(stock);
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
            System.out.println("Opened database successfully");

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
