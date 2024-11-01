package Activo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ActivoFiatDAO implements IActivoFiatDAO {

    @Override
    public boolean generarActivoFiat(ActivoFiat activoFiat) {
        boolean exito = false;
        if (activoFiat == null) {
            System.out.println("No ingreso un Activo");
            return exito;
        }
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            // Verificar si la nomenclatura existe en la tabla MONEDA
            if (!nomenclaturaExiste(c, activoFiat.getFiat().getNomenclatura())) {
                return exito;
            }
            // Verificar si el activo ya existe y actualizarlo, o crear uno nuevo
            if (activoExiste(c, activoFiat.getFiat().getNomenclatura())) {
                actualizarActivo(c, activoFiat);
                exito = true;
            } else {
                insertarActivo(c, activoFiat);
                exito = true;
            }
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return exito;

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

    public boolean activoExiste(Connection c, String nomenclatura) throws SQLException {
        String sql = "SELECT CANTIDAD FROM ACTIVO_FIAT WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void actualizarActivo(Connection c, ActivoFiat activo) throws SQLException {
        String sql = "UPDATE ACTIVO_FIAT SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setDouble(1, activo.getCantidad());
            pstmt.setString(2, activo.getFiat().getNomenclatura());
            pstmt.executeUpdate();
        }
    }

    private void insertarActivo(Connection c, ActivoFiat activo) throws SQLException {
        String sql = "INSERT INTO ACTIVO_FIAT (NOMENCLATURA, CANTIDAD) VALUES (?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, activo.getFiat().getNomenclatura());
            pstmt.setDouble(2, activo.getCantidad());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<ActivoFiat> listarActivosFiat() {
        Connection c = null;
        Statement stmt = null;
        List<ActivoFiat> activos = new LinkedList<>();
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ACTIVO_FIAT");
            while (rs.next()) {
                ActivoFiat activo = new ActivoFiat(rs.getDouble("CANTIDAD"), rs.getString("NOMENCLATURA"));
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
    public void borrarActivoFiat(String nomenclatura) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            String sql = "DELETE FROM ACTIVO_FIAT WHERE NOMENCLATURA = ? ";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                // No se encontró el activo con esa nomenclatura
                throw new RuntimeException("No se encontró el activo fiat: " + nomenclatura);
            }
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    public boolean verificarCantidad(Connection c, String nomenclatura, double cantidad) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ACTIVO_FIAT WHERE NOMENCLATURA = ? AND CANTIDAD >= ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            pstmt.setDouble(2, cantidad);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

}
