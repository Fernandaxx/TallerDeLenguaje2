import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ActivoDaoImpl implements ActivoDao {

    @Override
    /*
     * (o como ud. haya nombrado a la cantidad de una determinada moneda en posesión
     * del usuario)
     * Permitir ingresar la cantidad y la nomenclatura.
     * Si el usuario confirma, se guarda en la base de datos.
     * La nomenclatura ingresada debe existir entre las criptomonedas creadas en el
     * punto anterior para
     * guardarse en la base de datos, caso contrario el programa informa un error.
     */

    // falta: verificar que tipo de activo es -- verificar si el activo ya esta en
    // la tabla y sumar -- verificar si la nomenclatura es un activo verdadero
    // instans of
    public void GenerarActivo(String nomenclatura, double cantidad) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            String sql = "INSERT INTO ACTIVO_CRIPTO (NOMENCLATURA,CANTIDAD) " +
                    "VALUES ('" + nomenclatura + "', " + cantidad + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (

        Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    @Override
    /*
     * Listar Mis Activos
     * Muestra en pantalla información de los activos disponibles.
     * Si bien los Activos se mostrarán ordenados por cantidad de manera
     * descendente, debe ser capaz de ordenarse por nomenclatura de la moneda.
     * Se espera que use alguno de los mecanismos de interfaz vistos en teoría
     */

    public List<Activo> listarActivos() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ACTIVO_CRIPTO;");

            while (rs.next()) {
                String nom = rs.getString("NOMENCLATURA");
                double cant = rs.getDouble("CANTIDAD");

                System.out.println("nom = " + nom);
                System.out.println("cant = " + cant);

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
