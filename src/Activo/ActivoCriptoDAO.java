package Activo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import Moneda.MonedaDAO;

public class ActivoCriptoDAO implements IActivoCriptoDAO {
    private MonedaDAO monedaDAO = new MonedaDAO();

    @Override
    public boolean generarActivoCripto(ActivoCripto activoCripto) {
        boolean exito = false;
        if (activoCripto == null) {
            System.out.println("No ingreso un Activo");
            return exito;
        }

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            // Verificar si la nomenclatura existe en la tabla MONEDA
            if (!monedaDAO.monedaExiste(c, activoCripto.getCripto().getNomenclatura())) {
                return exito;
            }
            // Verificar si el activo ya existe y actualizarlo, o crear uno nuevo
            if (activoExiste(c, activoCripto.getCripto().getNomenclatura())) {
                actualizarActivo(c, activoCripto);
                exito = true;
            } else {
                insertarActivo(c, activoCripto);
                exito = true;
            }
            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        return exito;

    }

    public boolean activoExiste(Connection c, String nomenclatura) throws SQLException {
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

    public void insertarActivo(Connection c, ActivoCripto activo) throws SQLException {
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
            String sql = "DELETE FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ? ";
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, nomenclatura);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                // No se encontró el activo con esa nomenclatura
                throw new RuntimeException("No se encontró el activo cripto: " + nomenclatura);
            }
            pstmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
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
