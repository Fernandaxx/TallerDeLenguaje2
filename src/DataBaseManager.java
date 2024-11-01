import java.sql.*;

public class DataBaseManager {
        /**
         * Este método se encarga de la creación de las tablas donde se
         * almacenará la
         * información de los objetos. Una vez establecida una conexión, debería
         * ser lo próximo a ser ejecutado.
         *
         * @param connection objeto conexion a la base de datos SQLite
         * @throws SQLException
         */
        private static void creaciónDeTablasEnBD(Connection connection, Statement stmt) throws SQLException {

                stmt = connection.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS MONEDA "
                                + "("
                                + " TIPO       VARCHAR(1)    NOT NULL, "
                                + " NOMBRE       VARCHAR(50)    NOT NULL, "
                                + " NOMENCLATURA VARCHAR(10)  PRIMARY KEY   NOT NULL, "
                                + " VALOR_DOLAR	REAL     NOT NULL, "
                                + " VOLATILIDAD	REAL     NULL, "
                                + " STOCK	REAL     NULL " + ")";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS ACTIVO_CRIPTO"
                                + "("
                                + " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
                                + " CANTIDAD	REAL    NOT NULL " + ")";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS ACTIVO_FIAT"
                                + "("
                                + " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
                                + " CANTIDAD	REAL    NOT NULL " + ")";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS TRANSACCION"
                                + "("
                                + " RESUMEN VARCHAR(1000)   NOT NULL, "
                                + " FECHA_HORA	TIMESTAMP  NOT NULL " + ")";
                stmt.executeUpdate(sql);
                stmt.close();
        }

        public void CreateDatabase() {
                Connection c = null;
                Statement stmt = null;
                try {
                        c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
                        stmt = c.createStatement();
                        creaciónDeTablasEnBD(c, stmt);

                        stmt.close();
                        c.close();

                } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                }
        }

        public void borrarTabla(String nombreTabla) {
                String sql = "DROP TABLE IF EXISTS " + nombreTabla;

                try (Connection c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
                                Statement stmt = c.createStatement()) {
                        stmt.executeUpdate(sql);
                        System.out.println("Tabla " + nombreTabla + " eliminada exitosamente.");
                } catch (Exception e) {
                        System.err.println("Error al eliminar la tabla: " + e.getMessage());
                }
        }

}