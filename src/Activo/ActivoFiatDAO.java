package Activo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import Moneda.MonedaDAO;

/**
 * Clase que gestiona la persistencia de activos fiduciarios en una base de
 * datos.
 * Implementa la interfaz {@link IActivoFiatDAO} y proporciona métodos para
 * crear, leer, actualizar y eliminar activos fiduciarios en la base de datos.
 */
public class ActivoFiatDAO implements IActivoFiatDAO {
    private MonedaDAO m = new MonedaDAO();

    /**
     * Genera un nuevo activo fiduciario en la base de datos.
     * Si el activo ya existe, se actualiza la cantidad.
     *
     * @param activoFiat El activo fiduciario a generar.
     * @return true si la operación fue exitosa; false en caso contrario.
     */
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

            if (!m.monedaExiste(c, activoFiat.getFiat().getNomenclatura())) {
                return exito;
            }

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

    /**
     * Verifica si un activo fiduciario ya existe en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura del activo fiduciario a verificar.
     * @return true si el activo existe; false en caso contrario.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
    public boolean activoExiste(Connection c, String nomenclatura) throws SQLException {
        String sql = "SELECT CANTIDAD FROM ACTIVO_FIAT WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Actualiza la cantidad de un activo fiduciario existente en la base de datos.
     *
     * @param c      La conexión a la base de datos.
     * @param activo El activo fiduciario a actualizar.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
    public void actualizarActivo(Connection c, ActivoFiat activo) throws SQLException {
        String sql = "UPDATE ACTIVO_FIAT SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setDouble(1, activo.getCantidad());
            pstmt.setString(2, activo.getFiat().getNomenclatura());
            pstmt.executeUpdate();
        }
    }

    /**
     * Inserta un nuevo activo fiduciario en la base de datos.
     *
     * @param c      La conexión a la base de datos.
     * @param activo El activo fiduciario a insertar.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
    public void insertarActivo(Connection c, ActivoFiat activo) throws SQLException {
        String sql = "INSERT INTO ACTIVO_FIAT (NOMENCLATURA, CANTIDAD) VALUES (?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, activo.getFiat().getNomenclatura());
            pstmt.setDouble(2, activo.getCantidad());
            pstmt.executeUpdate();
        }
    }

    /**
     * Lista todos los activos fiduciarios en la base de datos.
     *
     * @return Una lista de activos fiduciarios.
     */
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

    /**
     * Elimina un activo fiduciario de la base de datos por su nomenclatura.
     *
     * @param nomenclatura La nomenclatura del activo fiduciario a eliminar.
     * @throws RuntimeException Si no se encuentra el activo a eliminar.
     */
    @Override
    public void borrarActivoFiat(String nomenclatura) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            String sql = "DELETE FROM ACTIVO_FIAT WHERE NOMENCLATURA = ?";
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

    /**
     * Verifica si hay suficiente cantidad de un activo fiduciario en la base de
     * datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura del activo fiduciario.
     * @param cantidad     La cantidad a verificar.
     * @return true si hay suficiente cantidad; false en caso contrario.
     * @throws SQLException Si ocurre un error en la consulta a la base de datos.
     */
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
