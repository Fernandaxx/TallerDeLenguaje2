import java.util.List;
import java.util.Scanner;

import Activo.ActivoCripto;
import Activo.ActivoCriptoDAO;
import Activo.ActivoFiat;
import Activo.ActivoFiatDAO;
import Comparadores.ComparadorCantidadStock;
import Comparadores.ComparadorNomenclaturaActivoCripto;
import Comparadores.ComparadorNomenclaturaActivoFiat;
import Comparadores.ComparadorNomenclaturaStock;
import Comparadores.ComparatorCantidadActivo;
import Moneda.Criptomoneda;
import Moneda.Fiat;
import Moneda.MonedaDAOnew;
import Moneda.Stock;
import Transaccion.GestorSwap;

public class BilleteraVirtualManager {
    private ActivoCriptoDAO activoCriptoOp = new ActivoCriptoDAO();
    private ActivoFiatDAO activoFiatOp = new ActivoFiatDAO();
    private MonedaDAOnew monedaOp = new MonedaDAOnew();
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";

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

    private void generarActivos(Scanner s) {

        System.out.println("=== Ingresar Nuevo Activo ===");
        System.out.println("¿Qué tipo de activo desea ingresar?");
        System.out.println("1. Criptomoneda");
        System.out.println("2. Fiat");
        int tipoActivo = s.nextInt();
        s.nextLine();
        System.out.println("Ingrese la cantidad de activo:");
        double cantidad = s.nextDouble();
        s.nextLine();
        System.out.println("Ingrese la nomenclatura (BTC, ETH, USD, EUR, etc):");
        String nomenclatura = s.nextLine();

        // Mostrar resumen
        System.out.println("\nResumen del activo a ingresar:");
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Nomenclatura: " + nomenclatura);

        System.out.println(
                "\n¿Está seguro que desea ingresar el activo a la base de datos? (y/n)");
        String confirmacion = s.nextLine();
        if (confirmacion.equalsIgnoreCase("y")) {

            switch (tipoActivo) {
                case 1: // Criptomoneda
                    ActivoCripto activoCripto = new ActivoCripto();
                    activoCripto.setCantidad(cantidad);
                    // Crear y configurar la criptomoneda
                    Criptomoneda cripto = new Criptomoneda();
                    cripto.setNomenclatura(nomenclatura);
                    activoCripto.setCripto(cripto);

                    activoCriptoOp.generarActivoCripto(activoCripto);

                    break;
                case 2: // Fiat
                    ActivoFiat activoFiat = new ActivoFiat();
                    activoFiat.setCantidad(cantidad);
                    // Crear y configurar el Fiat
                    Fiat fiat = new Fiat();
                    // System.out.println("Ingrese el nombre de la moneda:");
                    // fiat.setNombre(s.nextLine());
                    fiat.setNomenclatura(nomenclatura);
                    activoFiat.setFiat(fiat);
                    activoFiatOp.generarActivoFiat(activoFiat);
                    break;
                default:
                    System.out.println("Opción de Activo no válida");
                    return;
            }
            System.out.println("Activo ingresado exitosamente");

        } else {
            System.out.println("Operación cancelada");
        }

    }

    private void listarActivos(Scanner s) {
        System.out.println("===== Listar Activos =====");
        System.out.println("¿Qué tipo de activo desea listar? ");
        System.out.println("1. Criptomoneda");
        System.out.println("2. Fiat");
        int tipoAct = s.nextInt();
        s.nextLine();

        System.out.println("¿Desea ordenar por nomenclatura? (y/n)");
        boolean ordenarPorNomenclatura = s.nextLine().equalsIgnoreCase("y");

        switch (tipoAct) {
            case 1:
                List<ActivoCripto> lc = activoCriptoOp.listarActivosCripto();
                if (ordenarPorNomenclatura) {
                    lc.sort(new ComparadorNomenclaturaActivoCripto());
                    System.out.println(lc);
                } else {
                    lc.sort(new ComparatorCantidadActivo());
                    System.out.println(lc);
                }
                break;
            case 2:
                List<ActivoFiat> lf = activoFiatOp.listarActivosFiat();
                if (ordenarPorNomenclatura) {
                    lf.sort(new ComparadorNomenclaturaActivoFiat());
                    System.out.println(lf);
                } else {
                    lf.sort(new ComparatorCantidadActivo());
                    System.out.println(lf);
                }
                break;
            default:
                System.out.println("Tipo de Activo inválido");
        }

    }

    private void listarStock(Scanner s) {
        System.out.println("===== Listar Stock =====");
        System.out.println("¿Desea ordenar por nomenclatura? (y/n)");

        boolean ordenarPorNomenclatura = s.nextLine().equalsIgnoreCase("y");

        List<Stock> l = monedaOp.listarStock();
        if (ordenarPorNomenclatura) {
            l.sort(new ComparadorNomenclaturaStock());
            System.out.println(l);
        } else {
            l.sort(new ComparadorCantidadStock());
            System.out.println(l);

        }

    }

    private void simularSwap(Scanner s) {
        System.out.println("======= SWAP =======");
        System.out.println("\n" + ANSI_CYAN + "╔════════════════════════════════╗");
        System.out.println("║        CRYPTO SWAP             ║");
        System.out.println("╚════════════════════════════════╝" + ANSI_RESET + "\n");

        System.out.print("Ingrese la criptomoneda que desea intercambiar (ej. BTC): ");
        Criptomoneda cInicial = new Criptomoneda(s.nextLine());
        System.out.print("Ingrese la criptomoneda que desea recibir (ej. ETH): ");
        Criptomoneda cFinal = new Criptomoneda(s.nextLine());
        System.out.print("Ingrese la cantidad que desea intercambiar: ");
        double cantidad = s.nextDouble();
        s.nextLine();
        GestorSwap swap = new GestorSwap();
        double valorSimulado = swap.valorFinalSwap(cInicial, cantidad, cFinal);
        System.out.printf("Se ha simulado el intercambio de %.2f %s a %.2f %s.\n",
                cantidad, cInicial.getNomenclatura(),
                valorSimulado, cFinal.getNomenclatura());
        System.out.print("¿Desea confirmar esta operación? (s/n): ");
        String confirmacion = s.nextLine().toLowerCase();

        if (confirmacion.equals("s")) {
            swap.simularSwap(cInicial, cantidad, cFinal);
            System.out.println("Operación completada exitosamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    public void iniciar() {
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
            s.nextLine();
            switch (user_input) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    listarStock(s);
                    break;
                case 5:
                    generarActivos(s);
                    break;
                case 6:
                    listarActivos(s);
                    break;

                case 7:

                    break;
                case 8:
                    simularSwap(s);
                    break;
                case 9:
                    menu_detallado = !menu_detallado;
                    break;
                case 10:
                    monedaOp.borrarMoneda("ars");
                case 0:

                    break;

                default:

                    break;
            }
        }

        s.close();

    }

}
