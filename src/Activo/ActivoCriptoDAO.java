package Activo;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import Moneda.MonedaDAO;

/**
 * Clase para gestionar la persistencia de activos criptomonedas en la base de
 * datos.
 * Implementa la interfaz {@link IActivoCriptoDAO} para las operaciones
 * relacionadas con la creación, actualización, eliminación y listado de activos
 * cripto.
 */
public class ActivoCriptoDAO implements IActivoCriptoDAO {
    private MonedaDAO monedaDAO = new MonedaDAO();

    /**
     * Genera un nuevo activo cripto o actualiza uno existente en la base de
     * datos.
     *
     * @param activoCripto El activo cripto a generar o actualizar.
     * @return true si la operación se realizó con éxito, false en caso contrario.
     */
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
            if (!monedaDAO.monedaExiste(c, activoCripto.getCripto().getNomenclatura())) {
                return exito;
            }
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

    /**
     * Verifica si un activo cripto ya existe en la base de datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura del activo a verificar.
     * @return true si el activo existe, false en caso contrario.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public boolean activoExiste(Connection c, String nomenclatura) throws SQLException {
        String sql = "SELECT CANTIDAD FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, nomenclatura);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Actualiza la cantidad de un activo cripto existente en la base de
     * datos.
     *
     * @param c      La conexión a la base de datos.
     * @param activo El activo cripto a actualizar.
     * @throws SQLException Si ocurre un error en la actualización.
     */
    public void actualizarActivo(Connection c, ActivoCripto activo) throws SQLException {
        String sql = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setDouble(1, activo.getCantidad());
            pstmt.setString(2, activo.getCripto().getNomenclatura());
            pstmt.executeUpdate();
        }
    }

    /**
     * Inserta un nuevo activo cripto en la base de datos.
     *
     * @param c      La conexión a la base de datos.
     * @param activo El activo cripto a insertar.
     * @throws SQLException Si ocurre un error en la inserción.
     */
    public void insertarActivo(Connection c, ActivoCripto activo) throws SQLException {
        String sql = "INSERT INTO ACTIVO_CRIPTO (NOMENCLATURA, CANTIDAD) VALUES (?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, activo.getCripto().getNomenclatura());
            pstmt.setDouble(2, activo.getCantidad());
            pstmt.executeUpdate();
        }
    }

    /**
     * Lista todos los activos criptomoneda existentes en la base de datos.
     *
     * @return Una lista de activos cripto.
     */
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

    /**
     * Elimina un activo criptográfico de la base de datos por su nomenclatura.
     *
     * @param nomenclatura La nomenclatura del activo a eliminar.
     * @throws RuntimeException Si no se encuentra el activo.
     */
    @Override
    public void borrarActivoCripto(String nomenclatura) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            String sql = "DELETE FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ?";
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

    /**
     * Verifica si hay suficiente cantidad de un activo cripto en la base de
     * datos.
     *
     * @param c            La conexión a la base de datos.
     * @param nomenclatura La nomenclatura del activo a verificar.
     * @param cantidad     La cantidad a verificar.
     * @return true si hay suficiente cantidad, false en caso contrario.
     * @throws SQLException Si ocurre un error en la consulta.
     */
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
