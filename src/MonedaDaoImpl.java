import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MonedaDaoImpl implements MonedaDAO {

    public double equivalenteDolar(Connection c, String nomenclatura)throws SQLException{
        String sql = "SELECT VALOR_DOLAR FROM MONEDA WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setString(1,nomenclatura);
            try (ResultSet rs = pstmt.executeQuery()){
                return rs.getDouble("VALOR_DOLAR");
            }
        }
    }

    public boolean monedaExiste(Connection c, String nomenclatura, boolean esCripto) throws SQLException {
        String sql = "SELECT 1 FROM MONEDA WHERE NOMENCLATURA = ? AND TIPO = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            pstmt.setString(2, esCripto ? "C" : "F");
            return pstmt.executeQuery().next();
        }
    }

    private void actualizarMoneda(Connection c, Moneda moneda, boolean esCripto) throws SQLException {
        String sql = esCripto
                ? "UPDATE MONEDA SET VALOR_DOLAR = ?, VOLATILIDAD = ?, STOCK = ? WHERE NOMENCLATURA = ?"
                : "UPDATE MONEDA SET VALOR_DOLAR = ? WHERE NOMENCLATURA = ?";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setDouble(1, moneda.getValor_dolar());

            if (esCripto) {
                Criptomoneda cripto = (Criptomoneda) moneda;
                pstmt.setDouble(2, cripto.getVolatilidad());
                pstmt.setDouble(3, cripto.getStock());
            }

            pstmt.executeUpdate();
            System.out.println("Moneda actualizada exitosamente.");
        }
    }

    private void insertarMoneda(Connection c, Moneda moneda, boolean esCripto) throws SQLException {
        String sql = esCripto
                ? "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, STOCK) VALUES (?, ?, ?, ?, ?, ?)"
                : "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(moneda.getTipo()));
            pstmt.setString(2, moneda.getNombre());
            pstmt.setString(3, moneda.getNomenclatura());
            pstmt.setDouble(4, moneda.getValor_dolar());

            if (esCripto) {
                Criptomoneda cripto = (Criptomoneda) moneda;
                pstmt.setDouble(5, cripto.getVolatilidad());
                pstmt.setDouble(6, cripto.getStock());
            }

            pstmt.executeUpdate();
            System.out.println("Moneda insertada exitosamente.");
        }
    }

    @Override
    public void generarMoneda(Moneda moneda) {
        if (moneda == null) {
            System.err.println("No ingreso una moneda");
            return;
        }
        boolean esCripto = moneda instanceof Criptomoneda;

        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            // Verificar si la moneda ya existe; actualizar o insertar según sea el caso
            if (monedaExiste(c, moneda.getNomenclatura(), esCripto)) {
                actualizarMoneda(c, moneda, esCripto);
            } else {
                insertarMoneda(c, moneda, esCripto);
            }
            c.commit();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }
    private void borrar(Connection c){
        try{
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("DELETE FROM MONEDA WHERE TIPO=b");
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void ListarMonedas(boolean ordenarPorNomenclatura) {
        
        List<Moneda> monedas = new LinkedList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            borrar(c);
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

            if (ordenarPorNomenclatura) {
                Collections.sort(monedas, new ComparatorNomenclaturaMoneda());
            } else {
                Collections.sort(monedas, new ComparatorValorDolar());
            }
            for (Moneda act : monedas) {
                System.err.println(act.toString());
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Operation done successfully");

    }

    @Override
    public void GenerarStock() {

        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
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
                    System.out.println("Stock actualizado para: " + nomenclatura);
                }
            }
            c.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Operation done successfully");

    }

    /*
     * Listar Stock
     * Muestra en pantalla información del stock disponible.
     * Si bien el Stock se mostrará ordenado por cantidad de manera descendente,
     * debe ser capaz de ordenarse por nomenclatura. Se espera que use alguno de
     * los mecanismos de interfaces vistos en teoría
     */

    @Override
    public void ListarStock(boolean ordenarPorNomenclatura) {
        /*
         * Connection c = null;
         * Statement stmt = null;
         * List<Moneda> monedas = new LinkedList<>();
         * 
         * try {
         * Class.forName("org.sqlite.JDBC");
         * c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
         * c.setAutoCommit(false);
         * System.out.println("Opened database successfully");
         * 
         * stmt = c.createStatement();
         * ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA;");
         * 
         * while (rs.next()) {
         * Moneda moneda = new Moneda(
         * rs.getString("TIPO").charAt(0),
         * rs.getString("NOMBRE"),
         * rs.getString("NOMENCLATURA"),
         * rs.getDouble("VALOR_DOLAR"),
         * rs.getDouble("VOLATILIDAD"),
         * rs.getDouble("STOCK"));
         * monedas.add(moneda);
         * }
         * 
         * // Ordenar la lista según el criterio seleccionado
         * if (ordenarPorNomenclatura) {
         * Collections.sort(monedas, new NomenclaturaComparator());
         * } else {
         * Collections.sort(monedas, new StockComparator());
         * }
         * 
         * // Mostrar resultados
         * for (Moneda moneda : monedas) {
         * System.out.println("NOMENCLATURA = " + moneda.getNomenclatura() +
         * ", STOCK = " + moneda.getStock());
         * }
         * 
         * rs.close();
         * stmt.close();
         * c.close();
         * } catch (Exception e) {
         * System.err.println(e.getClass().getName() + ": " + e.getMessage());
         * System.exit(0);
         * }
         * 
         * System.out.println("Operation done successfully");
         */
    }

}
