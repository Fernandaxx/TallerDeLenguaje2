import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MonedaDaoImpl implements MonedaDAO {

    @Override
    public void crearMonedas(Moneda moneda) {
        String sql = "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, STOCK) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Verify if coin already exists
            if (existeMoneda(conn, moneda.getNomenclatura())) {
                throw new SQLException("La moneda con nomenclatura " + moneda.getNomenclatura() + " ya existe.");
            }

            // Set parameters using PreparedStatement to prevent SQL injection
            pstmt.setString(1, String.valueOf(moneda.getTipo()));
            pstmt.setString(2, moneda.getNombre());
            pstmt.setString(3, moneda.getNomenclatura());
            pstmt.setDouble(4, moneda.getValor_dolar());
            pstmt.setDouble(5, moneda.getVolatilidad());
            pstmt.setDouble(6, moneda.getStock());

            // Execute the insert
            pstmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    // Helper method to check if a coin already exists
    private boolean existeMoneda(Connection conn, String nomenclatura) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public List<Moneda> listarMonedas() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA;");

            List<Moneda> monedas = new LinkedList<Moneda>();
            while (rs.next()) {
                Moneda moneda = new Moneda(
                        rs.getString("TIPO").charAt(0),
                        rs.getString("NOMBRE"),
                        rs.getString("NOMENCLATURA"),
                        rs.getDouble("VALOR_DOLAR"),
                        rs.getDouble("VOLATILIDAD"),
                        rs.getDouble("STOCK"));
                monedas.add(moneda);
                System.out.println(moneda.toString());
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return null;
    }

    @Override
    public Moneda ListarPorNomenclatura(String nomenclatura) {
        return null;
    }

    @Override
    public void actualizarStock(String nomenclatura, double nuevoStock) {

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
        Connection c = null;
        Statement stmt = null;
        List<Moneda> monedas = new LinkedList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA;");

            while (rs.next()) {
                Moneda moneda = new Moneda(
                        rs.getString("TIPO").charAt(0),
                        rs.getString("NOMBRE"),
                        rs.getString("NOMENCLATURA"),
                        rs.getDouble("VALOR_DOLAR"),
                        rs.getDouble("VOLATILIDAD"),
                        rs.getDouble("STOCK"));
                monedas.add(moneda);
            }

            // Ordenar la lista según el criterio seleccionado
            if (ordenarPorNomenclatura) {
                Collections.sort(monedas, new NomenclaturaComparator());
            } else {
                Collections.sort(monedas, new StockComparator());
            }

            // Mostrar resultados
            for (Moneda moneda : monedas) {
                System.out.println("NOMENCLATURA = " + moneda.getNomenclatura() +
                        ", STOCK = " + moneda.getStock());
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Operation done successfully");
    }

}
