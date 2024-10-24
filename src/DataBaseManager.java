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
                System.out.println("\n=== SISTEMA DE GESTION DE CRIPTOMONEDAS ===\n");
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
                System.out.println("\n====== SISTEMA DE GESTION DE CRIPTOMONEDAS ======\n");
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

        private static void crearMonedas() {

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

                                        Moneda moneda = new Moneda('F', "peso", "ARS", 1000, 0, 800);

                                        monedita.crearMonedas(moneda);

                                        break;
                                case 2:
                                        System.out.println("Usuario opina 2.");
                                        System.out.println("Llamar a funcion 2.");
                                        MonedaDAO monedi = new MonedaDaoImpl();
                                        monedi.listarMonedas();
                                case 3:
                                        System.out.println("Usuario opina 3.");
                                        System.out.println("Llamar a funcion 3.");
                                        break;
                                case 4:
                                        System.out.println("Usuario opina 4.");
                                        System.out.println("Llamar a funcion 4.");

                                        System.out.println("==Listar stock== ");

                                        boolean ordenarPorNomenclaturaa;
                                        System.out.println("¿Desea ordenar por nomenclatura? (y/n)");
                                        s.nextLine();
                                        String ordenar = s.nextLine();
                                        if (ordenar.equals("y")) {
                                                ordenarPorNomenclaturaa = true;
                                        } else if (ordenar.equals("n")) {
                                                ordenarPorNomenclaturaa = false;
                                        } else {
                                                System.out.println("Opción no válida. Debe ser 'y' (sí) o 'n' (no).");
                                                return;
                                        }

                                        MonedaDAO listarS = new MonedaDaoImpl();
                                        listarS.ListarStock(ordenarPorNomenclaturaa);
                                        System.out.println("Stock listado correctamente");
                                        break;
                                case 5:
                                        System.out.println("=== Ingresar Nuevo Activo ===");
                                        System.out.println("¿Qué tipo de activo desea ingresar?");
                                        System.out.println("1. Criptomoneda");
                                        System.out.println("2. Fiat");

                                        Activo activo = null;
                                        int tipoActivo = s.nextInt();

                                        System.out.println("Ingrese la cantidad:");
                                        double cantidad = s.nextDouble();
                                        s.nextLine();
                                        switch (tipoActivo) {
                                                case 1: // Criptomoneda
                                                        ActivoCripto activoCripto = new ActivoCripto();
                                                        activoCripto.setCantidad(cantidad);

                                                        // Crear y configurar la criptomoneda
                                                        Criptomoneda cripto = new Criptomoneda();
                                                        System.out.println("Ingrese la nomenclatura (BTC, ETH, etc):");
                                                        cripto.setNomenclatura(s.nextLine());

                                                        activoCripto.setCripto(cripto);
                                                        activo = activoCripto;
                                                        break;

                                                case 2: // Fiat
                                                        ActivoFiat activoFiat = new ActivoFiat();
                                                        activoFiat.setCantidad(cantidad);

                                                        // Crear y configurar el Fiat
                                                        Fiat fiat = new Fiat();
                                                        // System.out.println("Ingrese el nombre de la moneda:");
                                                        // fiat.setNombre(s.nextLine());
                                                        System.out.println("Ingrese la nomenclatura (USD, EUR, etc):");
                                                        fiat.setNomenclatura(s.nextLine());

                                                        activoFiat.setFiat(fiat);
                                                        activo = activoFiat;
                                                        break;

                                                default:
                                                        System.out.println("Opción no válida");
                                                        return;
                                        }

                                        // Mostrar resumen usando el método obtenerNomenclatura()
                                        System.out.println("\nResumen del activo a ingresar:");
                                        System.out.println("Cantidad: " + activo.getCantidad());
                                        System.out.println("Nomenclatura: " + activo.obtenerNomenclatura());

                                        System.out.println(
                                                        "\n¿Está seguro que desea ingresar el activo a la base de datos? (y/n)");

                                        String confirmacion = s.nextLine();
                                        if (confirmacion.equalsIgnoreCase("y")) {
                                                ActivoDao generar = new ActivoDaoImpl();
                                                generar.generarActivo(activo);
                                                System.out.println("Activo ingresado exitosamente");

                                        } else {
                                                System.out.println("Operación cancelada");
                                        }

                                        break;
                                case 6:
                                        boolean esCripto = false;
                                        boolean ordenarPorNomenclatura = false;
                                        System.out.println("===== Listar Activos =====");
                                        System.out.println("¿Qué tipo de activo desea listar? ");
                                        System.out.println("1. Criptomoneda");
                                        System.out.println("2. Fiat");
                                        int tipoAct = s.nextInt();
                                        s.nextLine();
                                        switch (tipoAct) {
                                                case 1: // Criptomoneda
                                                        esCripto = true;
                                                        break;
                                                case 2: // Fiat
                                                        esCripto = false;
                                                        break;

                                                default:
                                                        System.out.println("Opción no válida");
                                                        return;
                                        }
                                        System.out.println("¿Desea ordenar por nomenclatura? (y/n)");
                                        String ordenar2 = s.nextLine();
                                        if (ordenar2.equals("y")) {
                                                ordenarPorNomenclatura = true;
                                        } else if (ordenar2.equals("n")) {
                                                ordenarPorNomenclatura = false;
                                        } else {
                                                System.out.println("Opción no válida. Debe ser 'y' (sí) o 'n' (no).");
                                                return;
                                        }

                                        ActivoDao listar = new ActivoDaoImpl();
                                        listar.listarActivos(esCripto, ordenarPorNomenclatura);
                                        System.out.println("Listado generado.");
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