package Activo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ActivoCriptoDAO implements IActivoCriptoDAO {

    @Override
    public void generarActivoCripto(ActivoCripto activoCripto) {
        if (activoCripto == null) {
            System.out.println("No ingreso un Activo");
            return;
        }

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            // Verificar si la nomenclatura existe en la tabla MONEDA
            if (!nomenclaturaExiste(c, activoCripto.getCripto().getNomenclatura())) {
                System.out.println("La nomenclatura " + activoCripto.getCripto().getNomenclatura()
                        + " no existe como moneda CRIPTOMONEDA");
                return;
            }
            // Verificar si el activo ya existe y actualizarlo, o crear uno nuevo
            if (activoExiste(c, activoCripto.getCripto().getNomenclatura())) {
                actualizarActivo(c, activoCripto);
            } else {
                insertarActivo(c, activoCripto);
            }
            c.commit();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }

    }

    private boolean nomenclaturaExiste(Connection c, String nomenclatura) throws SQLException {
        String sql = "SELECT COUNT(*) FROM MONEDA WHERE NOMENCLATURA = ? ";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public boolean activoExiste(Connection c, String nomenclatura) throws SQLException{
        String sql = "SELECT CANTIDAD FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void actualizarActivo(Connection c, ActivoCripto activo) throws SQLException {
        String sql = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setDouble(1, activo.getCantidad());
            pstmt.setString(2, activo.getCripto().getNomenclatura());
            pstmt.executeUpdate();
        }
    }

    private void insertarActivo(Connection c, ActivoCripto activo) throws SQLException {
        String sql = "INSERT INTO ACTIVO_CRIPTO (NOMENCLATURA, CANTIDAD) VALUES (?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, activo.getCripto().getNomenclatura());
            pstmt.setDouble(2, activo.getCantidad());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<ActivoCripto> listarActivosCripto() {
        Connection c = null;
        Statement stmt = null;
        List<ActivoCripto> activos = new LinkedList<>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ACTIVO_CRIPTO");
            while (rs.next()) {
                ActivoCripto activo = new ActivoCripto(rs.getDouble("CANTIDAD"), rs.getString("NOMENCLATURA"));
                activos.add(activo);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return activos;
    }

    @Override
    public void borrarActivoCripto(String nomenclatura) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String sql = "DELETE FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ? ";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                // No se encontró el activo con esa nomenclatura
                throw new RuntimeException("No se encontró el activo cripto: " + nomenclatura);
            }
            c.commit();
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Operation done successfully");
    }

    public boolean verificarCantidad(Connection c, String nomenclatura, double cantidad) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ? AND CANTIDAD >= ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            pstmt.setDouble(2, cantidad);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
