/*
 * package Activo;
 * 
 * import java.sql.*;
 * import java.util.*;
 * 
 * import Comparadores.ComparatorCantidad;
 * 
 * public class ActivoDaoImpl implements ActivoDAO {
 * 
 * private boolean nomenclaturaExiste(Connection c, String nomenclatura, String
 * tipo) throws SQLException {
 * String sql =
 * "SELECT COUNT(*) FROM MONEDA WHERE NOMENCLATURA = ? AND TIPO = ?";
 * try (PreparedStatement pstmt = c.prepareStatement(sql)) {
 * pstmt.setString(1, nomenclatura);
 * pstmt.setString(2, tipo);
 * try (ResultSet rs = pstmt.executeQuery()) {
 * return rs.next() && rs.getInt(1) > 0;
 * }
 * }
 * }
 * 
 * public boolean activoExiste(Connection c, String nomenclatura, boolean
 * esCripto) throws SQLException {
 * String tableName = esCripto ? "ACTIVO_CRIPTO" : "ACTIVO_FIAT";
 * String sql = "SELECT CANTIDAD FROM " + tableName + " WHERE NOMENCLATURA = ?";
 * try (PreparedStatement pstmt = c.prepareStatement(sql)) {
 * pstmt.setString(1, nomenclatura);
 * try (ResultSet rs = pstmt.executeQuery()) {
 * return rs.next();
 * }
 * }
 * }
 * 
 * public void actualizarActivo(Connection c, Activo activo, boolean esCripto)
 * throws SQLException {
 * String tableName = esCripto ? "ACTIVO_CRIPTO" : "ACTIVO_FIAT";
 * String sql = "UPDATE " + tableName +
 * " SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
 * try (PreparedStatement pstmt = c.prepareStatement(sql)) {
 * pstmt.setDouble(1, activo.getCantidad());
 * pstmt.setString(2, activo.obtenerNomenclatura());
 * pstmt.executeUpdate();
 * }
 * }
 * 
 * private void insertarActivo(Connection c, Activo activo, boolean esCripto)
 * throws SQLException {
 * String tableName = esCripto ? "ACTIVO_CRIPTO" : "ACTIVO_FIAT";
 * String sql = "INSERT INTO " + tableName +
 * " (NOMENCLATURA, CANTIDAD) VALUES (?, ?)";
 * try (PreparedStatement pstmt = c.prepareStatement(sql)) {
 * pstmt.setString(1, activo.obtenerNomenclatura());
 * pstmt.setDouble(2, activo.getCantidad());
 * pstmt.executeUpdate();
 * }
 * }
 * 
 * @Override
 * public void generarActivo(Activo activo) {
 * if (activo == null) {
 * System.err.println("No ingreso un Activo");
 * return;
 * }
 * 
 * boolean esCripto = activo instanceof ActivoCripto;
 * String tipo = esCripto ? "C" : "F";
 * 
 * Connection c = null;
 * 
 * try {
 * // Class.forName("org.sqlite.JDBC");
 * c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
 * c.setAutoCommit(false);
 * System.out.println("Opened database successfully");
 * 
 * // Verificar si la nomenclatura existe en la tabla MONEDA
 * if (!nomenclaturaExiste(c, activo.obtenerNomenclatura(), tipo)) {
 * System.err.println("La nomenclatura " + activo.obtenerNomenclatura() +
 * " no existe como "
 * + (esCripto ? "criptomoneda" : "moneda FIAT"));
 * return;
 * }
 * 
 * // Verificar si el activo ya existe y actualizarlo, o crear uno nuevo
 * if (activoExiste(c, activo.obtenerNomenclatura(), esCripto)) {
 * actualizarActivo(c, activo, esCripto);
 * } else {
 * insertarActivo(c, activo, esCripto);
 * }
 * c.commit();
 * 
 * } catch (SQLException e) {
 * System.err.println(e.getClass().getName() + ": " + e.getMessage());
 * System.exit(1);
 * }
 * }
 * 
 * @Override
 * 
 * 
 * public void listarActivos(boolean esCripto, boolean ordenarPorNomenclatura) {
 * Connection c = null;
 * Statement stmt = null;
 * List<Activo> activos = new LinkedList<>();
 * 
 * try {
 * Class.forName("org.sqlite.JDBC");
 * c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
 * c.setAutoCommit(false);
 * System.out.println("Opened database successfully");
 * 
 * stmt = c.createStatement();
 * String tableName = esCripto ? "ACTIVO_CRIPTO" : "ACTIVO_FIAT";
 * ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
 * 
 * while (rs.next()) {
 * Activo activo;
 * if (esCripto) {
 * activo = new ActivoCripto(rs.getDouble("CANTIDAD"),
 * rs.getString("NOMENCLATURA"));
 * } else {
 * activo = new ActivoFiat(rs.getDouble("CANTIDAD"),
 * rs.getString("NOMENCLATURA"));
 * }
 * activos.add(activo);
 * }
 * 
 * if (ordenarPorNomenclatura) {
 * Collections.sort(activos, new ComparadorNomenclaturaActivo());
 * } else {
 * Collections.sort(activos, new ComparatorCantidad());
 * }
 * for (Activo act : activos) {
 * System.out.println("NOMENCLATURA = " + act.obtenerNomenclatura() +
 * ", Cantidad = " + act.getCantidad());
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
 * 
 * public boolean verificarStock(Connection c, String nomenclatura, double
 * cantidad, boolean esCripto)
 * throws SQLException {
 * String tableName = esCripto ? "ACTIVO_CRIPTO" : "ACTIVO_FIAT";
 * String sql = "SELECT COUNT(*) FROM " + tableName +
 * "WHERE NOMENCLATURA = ? AND CANTIDAD >= ?";
 * try (PreparedStatement pstmt = c.prepareStatement(sql)) {
 * pstmt.setString(1, nomenclatura);
 * pstmt.setDouble(2, cantidad);
 * try (ResultSet rs = pstmt.executeQuery()) {
 * return rs.next() && rs.getInt(1) > 0;
 * ;
 * }
 * }
 * }
 * 
 * }
 */