import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MonedaDaoImpl implements MonedaDAO {
    private Connection connection;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarMonedas'");
    }

    @Override
    public Moneda ListarPorNomenclatura(String nomenclatura) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ListarPorNomenclatura'");
    }

    @Override
    public void actualizarStock(String nomenclatura, double nuevoStock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarStock'");
    }

    @Override
    public List<Double> ListarStock() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MONEDA;");

            while (rs.next()) {
                double stock = rs.getDouble("STOCK");

                System.out.println("cant = " + stock);

                System.out.println();
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

}
