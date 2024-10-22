import java.sql.*;
import java.util.Scanner;

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

                // stmt = connection.createStatement();
                String sql = "CREATE TABLE MONEDA "
                                + "("
                                + " TIPO       VARCHAR(1)    NOT NULL, "
                                + " NOMBRE       VARCHAR(50)    NOT NULL, "
                                + " NOMENCLATURA VARCHAR(10)  PRIMARY KEY   NOT NULL, "
                                + " VALOR_DOLAR	REAL     NOT NULL, "
                                + " VOLATILIDAD	REAL     NULL, "
                                + " STOCK	REAL     NULL " + ")";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE ACTIVO_CRIPTO"
                                + "("
                                + " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
                                + " CANTIDAD	REAL    NOT NULL " + ")";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE ACTIVO_FIAT"
                                + "("
                                + " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
                                + " CANTIDAD	REAL    NOT NULL " + ")";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE TRANSACCION"
                                + "("
                                + " RESUMEN VARCHAR(1000)   NOT NULL, "
                                + " FECHA_HORA		DATETIME  NOT NULL " + ")";
                stmt.executeUpdate(sql);
                stmt.close();
        }

        private static void CreateDatabase() {
                Connection c = null;
                Statement stmt = null;
                try {
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection("jdbc:sqlite:BilleteraVirtual.db");
                        System.err.println("Aqui");
                        c.setAutoCommit(false);
                        System.out.println("Opened database successfully");
                        stmt = c.createStatement();
                        creaciónDeTablasEnBD(c, stmt);

                        stmt.close();
                        c.commit();
                        c.close();

                } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                }
                System.out.println("Table created successfully");
        }

        private static void printMenu() {
                System.out.println("\n=== SISTEMA DE GESTIÓN DE CRIPTOMONEDAS ===\n");
                System.out.println("1. Crear Monedas");
                System.out.println("2. Listar Monedas");
                System.out.println("3. Generar Stock");
                System.out.println("4. Listar Stock");
                System.out.println("5. Generar Mis Activos");
                System.out.println("6. Listar Mis Activos");
                System.out.println("7. Simular Compra de Criptomoneda");
                System.out.println("8. Simular SWAP entre Criptomonedas");
                System.out.println("9. Mostrar menú detallado");
                System.out.println("0. Salir");
                System.out.print("\nSeleccione una opción: ");
        }

        private static void printMenuDetallado() {
                System.out.println("\n====== SISTEMA DE GESTIÓN DE CRIPTOMONEDAS ======\n");
                System.out.println("1. Crear Monedas");
                System.out.println("   - Registrar nueva moneda (Cripto o FIAT)");
                System.out.println("   - Ingresar nombre, nomenclatura y valor en USD");
                System.out.println("\n2. Listar Monedas");
                System.out.println("   - Ver todas las monedas ordenadas por valor");
                System.out.println("\n3. Generar Stock");
                System.out.println("   - Generar cantidades aleatorias de monedas");
                System.out.println("\n4. Listar Stock");
                System.out.println("   - Ver inventario disponible ordenado por cantidad");
                System.out.println("\n5. Generar Mis Activos");
                System.out.println("   - Registrar nuevas cantidades de monedas en cartera");
                System.out.println("\n6. Listar Mis Activos");
                System.out.println("   - Ver mi cartera de inversiones");
                System.out.println("\n7. Simular Compra");
                System.out.println("   - Comprar criptomonedas usando FIAT");
                System.out.println("\n8. Simular SWAP");
                System.out.println("   - Intercambiar entre diferentes criptomonedas");
                System.out.println("\n9. Cambiar menú");
                System.out.println("   - Se oculta el detalle de las opciones en el menú");
                System.out.println("\n0. Salir");
                System.out.print("\nSeleccione una opción: ");
        }

        public static void main(String args[]) {
                // CreateDatabase();

                Scanner s = new Scanner(System.in);
                int user_input = -1;
                boolean menu_detallado = false;
                while (user_input != 0) {
                        if (menu_detallado) {
                                printMenuDetallado();
                        } else {
                                printMenu();
                        }

                        System.out.println("Ingrese una opcion: ");
                        user_input = s.nextInt();

                        switch (user_input) {
                                case 1:
                                        System.out.println("Usuario opina 1.");
                                        System.out.println("Llamar a funcion 1.");

                                        MonedaDAO monedita = new MonedaDaoImpl();

                                        Moneda moneda = new Moneda('b', "algo", "uwu", 15.546, 0.3, 344.9);

                                        monedita.crearMonedas(moneda);

                                        break;
                                case 2:
                                        System.out.println("Usuario opina 2.");
                                        System.out.println("Llamar a funcion 2.");
                                        break;
                                case 3:
                                        System.out.println("Usuario opina 3.");
                                        System.out.println("Llamar a funcion 3.");
                                        break;
                                case 4:
                                        System.out.println("Usuario opina 4.");
                                        System.out.println("Llamar a funcion 4.");

                                        MonedaDAO monedaa = new MonedaDaoImpl();
                                        monedaa.ListarStock();
                                        break;
                                case 5:
                                        System.out.println("Usuario opina 5.");
                                        System.out.println("Llamar a funcion 5.");
                                        break;
                                case 6:
                                        System.out.println("Usuario opina 6.");
                                        System.out.println("Llamar a funcion 6.");
                                        break;
                                case 7:
                                        System.out.println("Usuario opina 7.");
                                        System.out.println("Llamar a funcion 7.");
                                        break;
                                case 8:
                                        System.out.println("Usuario opina 8.");
                                        System.out.println("Llamar a funcion 8.");
                                        break;
                                case 9:
                                        menu_detallado = !menu_detallado;
                                        break;
                                case 0:
                                        System.out.println();
                                        System.out.println("¡Gracias por usar el Sistema de Gestión de Criptomonedas!");
                                        System.out.println("Sesión finalizada exitosamente.");
                                        break;

                                default:
                                        System.out.println("Usuario no sabe leer un simple menu.");
                                        System.out.println("Try again.");
                                        break;
                        }
                }

                s.close();

        }

}