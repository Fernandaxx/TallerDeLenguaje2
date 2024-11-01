import Activo.ActivoCripto;
import Activo.ActivoCriptoDAO;
import Activo.ActivoFiat;
import Activo.ActivoFiatDAO;
import Comparadores.ComparadorCantidadStock;
import Comparadores.ComparadorNomenclaturaActivoCripto;
import Comparadores.ComparadorNomenclaturaActivoFiat;
import Comparadores.ComparadorNomenclaturaStock;
import Comparadores.ComparatorCantidadActivo;
import Comparadores.ComparatorNomenclaturaMoneda;
import Comparadores.ComparatorValorDolar;
import Moneda.Criptomoneda;
import Moneda.Fiat;
import Moneda.Moneda;
import Moneda.MonedaDAO;
import Moneda.Stock;
import Transaccion.GestorSwap;
import Transaccion.Result;
import Transaccion.Compra;
import Transaccion.GestorCompra;
import java.util.*;

public class BilleteraVirtualManager {
    private ActivoCriptoDAO activoCriptoOp = new ActivoCriptoDAO();
    private ActivoFiatDAO activoFiatOp = new ActivoFiatDAO();
    private MonedaDAO monedaOp = new MonedaDAO();
    private static final List<String> nomenclaturasPermitidas = new LinkedList<>(
            Arrays.asList("BTC", "ETH", "USDC", "DOGE", "USDT", "ARS", "USD", "BS", "EUR", "UYU"));

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
        System.out.println("   -Esta opción permite registrar nuevas monedas en el sistema.");

        System.out.println("\n2. Listar Monedas");
        System.out.println("   -Muestra todas las monedas registradas en el sistema.");

        System.out.println("\n3. Generar Stock");
        System.out.println("   -Genera cantidades aleatorias de monedas para la billetera.");

        System.out.println("\n4. Listar Stock");
        System.out.println("   -Muestra el inventario disponible de todas las monedas");

        System.out.println("\n5. Generar Mis Activos");
        System.out.println("   -Permite registrar nuevas cantidades de monedas en los activos personales.");

        System.out.println("\n6. Listar Mis Activos");
        System.out.println("   -Muestra los activos disponibles en la cartera personal.");

        System.out.println("\n7. Simular Compra");
        System.out.println("   -Permite realizar una compra de criptomonedas usando moneda fiat.");

        System.out.println("\n8. Simular SWAP");
        System.out.println("   -Permite intercambiar entre diferentes criptomonedas.");

        System.out.println("\n9. Cambiar menú");
        System.out.println("   - Alterna entre menú simple y detallado");

        System.out.println("\n0. Salir");
        System.out.println("   - Finaliza la ejecución del programa");

        System.out.print("\nSeleccione una opción: ");
    }

    private void generarCompra(Scanner s) {
        System.out.println("=====================================");
        System.out.println("          Comprar Criptomoneda       ");
        System.out.println("=====================================");

        System.out.print("Ingrese la nomenclatura de la criptomoneda que desea comprar: ");
        String nomenclaturaCripto = s.next();
        Criptomoneda cripto = new Criptomoneda(nomenclaturaCripto);

        System.out.print("Ingrese la nomenclatura de la moneda Fiat con la que desea comprar: ");
        String nomenclaturaFiat = s.next();
        Fiat fiat = new Fiat(nomenclaturaFiat);

        System.out.print("Ingrese la cantidad de " + nomenclaturaFiat + " para realizar la compra: ");
        Double cantidad = s.nextDouble();

        GestorCompra gestor = new GestorCompra();
        Compra compra = gestor.simularCompra(cripto, fiat, cantidad);

        System.out.println("\n-------------------------------------");
        System.out.println("           Resumen de Compra         ");
        System.out.println("-------------------------------------");
        System.out.println("Cantidad a comprar: " + compra.getCantidad() + " " + compra.getCripto().getNomenclatura());
        System.out.println("Total a gastar: " + cantidad + " " + compra.getFiat().getNomenclatura());
        System.out.println("-------------------------------------");

        // el que se guarda en transaccion
        String resumen = ("Compra:" + compra.getCantidad() + " de " + compra.getCripto().getNomenclatura()
                + "\nTotal a gastar: " + cantidad + " de " + compra.getFiat().getNomenclatura());

        System.out.print("\n¿Está seguro que desea confirmar la compra? (y/n): ");
        String confirmacion = s.next();

        if (confirmacion.equalsIgnoreCase("y")) {
            gestor.generarCompra(
                    new Compra(compra.getFecha(), fiat, cripto, compra.getCantidad(), cantidad, resumen));
            System.out.println("\nMoneda comprada exitosamente.\n");
        } else {
            System.out.println("\nOperación cancelada.\n");
        }

        System.out.println("=====================================");
    }

    private void generarActivos(Scanner s) {

        System.out.println("=====================================");
        System.out.println("        Ingresar Nuevo Activo        ");
        System.out.println("=====================================");

        System.out.println("¿Qué tipo de activo desea ingresar?");
        System.out.println("1. Criptomoneda");
        System.out.println("2. Moneda Fiat");
        int tipoActivo = s.nextInt();
        s.nextLine();

        System.out.print("Ingrese la cantidad del activo: ");
        double cantidad = s.nextDouble();
        s.nextLine();
        System.out.print("Ingrese la nomenclatura (BTC, ETH, USD, EUR, etc.): ");
        String nomenclatura = s.nextLine();

        System.out.println("\n-------------------------------------");
        System.out.println("   Resumen del Activo a Ingresar ");
        System.out.println("-------------------------------------");
        System.out.println("Tipo de Activo: " + (tipoActivo == 1 ? "Criptomoneda" : "Moneda Fiat"));
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Nomenclatura: " + nomenclatura);
        System.out.println("-------------------------------------");

        System.out.print("\n¿Está seguro que desea ingresar el activo a la base de datos? (y/n): ");
        String confirmacion = s.nextLine();

        if (confirmacion.equalsIgnoreCase("y")) {

            switch (tipoActivo) {
                case 1: // Criptomoneda
                    ActivoCripto activoCripto = new ActivoCripto();
                    activoCripto.setCantidad(cantidad);
                    Criptomoneda cripto = new Criptomoneda();
                    cripto.setNomenclatura(nomenclatura);
                    activoCripto.setCripto(cripto);
                    if (activoCriptoOp.generarActivoCripto(activoCripto)) {
                        System.out.println("\nCriptomoneda ingresada exitosamente.");
                    } else {
                        System.out.println("\nError al ingresar la criptomoneda.");
                    }
                    break;
                case 2: // Fiat
                    ActivoFiat activoFiat = new ActivoFiat();
                    activoFiat.setCantidad(cantidad);
                    Fiat fiat = new Fiat();
                    fiat.setNomenclatura(nomenclatura);
                    activoFiat.setFiat(fiat);
                    if (activoFiatOp.generarActivoFiat(activoFiat)) {
                        System.out.println("\nMoneda Fiat ingresada exitosamente.");
                    } else {
                        System.out.println("\nError al ingresar la moneda Fiat.");
                    }
                    break;
                default:
                    System.out.println("\nOpción de activo no válida.");
                    return;
            }
        } else {
            System.out.println("\nOperación cancelada.");
        }

        System.out.println("=====================================");
    }

    private void listarActivos(Scanner s) {
        System.out.println("=====================================");
        System.out.println("           Listar Activos            ");
        System.out.println("=====================================");

        System.out.println("¿Qué tipo de activo desea listar?");
        System.out.println("1. Criptomoneda");
        System.out.println("2. Moneda Fiat");
        int tipoAct = s.nextInt();
        s.nextLine();

        System.out.print("¿Desea ordenar por nomenclatura? (y/n): ");
        boolean ordenarPorNomenclatura = s.nextLine().equalsIgnoreCase("y");

        System.out.println("\n-------------------------------------");
        System.out.println("        Listado de Activos           ");
        System.out.println("-------------------------------------");

        switch (tipoAct) {
            case 1: // Listar Criptomonedas
                List<ActivoCripto> listaCripto = activoCriptoOp.listarActivosCripto();
                if (ordenarPorNomenclatura) {
                    listaCripto.sort(new ComparadorNomenclaturaActivoCripto());
                } else {
                    listaCripto.sort(new ComparatorCantidadActivo());
                }

                // Mostrar lista de criptomonedas
                System.out.println("Tipo de Activo: Criptomonedas\n");
                for (ActivoCripto activo : listaCripto) {
                    System.out.println("Nomenclatura: " + activo.getCripto().getNomenclatura());
                    System.out.println("Cantidad: " + activo.getCantidad());
                    System.out.println("-------------------------------------");
                }
                break;

            case 2: // Listar Monedas Fiat
                List<ActivoFiat> listaFiat = activoFiatOp.listarActivosFiat();
                if (ordenarPorNomenclatura) {
                    listaFiat.sort(new ComparadorNomenclaturaActivoFiat());
                } else {
                    listaFiat.sort(new ComparatorCantidadActivo());
                }

                // Mostrar lista de monedas fiat
                System.out.println("Tipo de Activo: Monedas Fiat\n");
                for (ActivoFiat activo : listaFiat) {
                    System.out.println("Nomenclatura: " + activo.getFiat().getNomenclatura());
                    System.out.println("Cantidad: " + activo.getCantidad());
                    System.out.println("-------------------------------------");
                }
                break;

            default:
                System.out.println("Tipo de Activo inválido.");
        }

        System.out.println("=====================================");
    }

    private void listarStock(Scanner s) {
        System.out.println("=====================================");
        System.out.println("            Listar Stock             ");
        System.out.println("=====================================");

        System.out.print("¿Desea ordenar por nomenclatura? (y/n): ");
        boolean ordenarPorNomenclatura = s.nextLine().equalsIgnoreCase("y");

        List<Stock> listaStock = monedaOp.listarStock();
        if (ordenarPorNomenclatura) {
            listaStock.sort(new ComparadorNomenclaturaStock());
        } else {
            listaStock.sort(new ComparadorCantidadStock());
        }

        System.out.println("\n-------------------------------------");
        System.out.println("          Detalle del Stock       ");
        System.out.println("-------------------------------------");

        for (Stock stock : listaStock) {
            System.out.println("Nomenclatura: " + stock.getNomenclatura());
            System.out.println("Cantidad: " + stock.getCantidad());
            System.out.println("-------------------------------------");
        }

        System.out.println("=====================================");
    }

    private void simularSwap(Scanner s) {
        System.out.println("=====================================");
        System.out.println("               SWAP                  ");
        System.out.println("=====================================");

        System.out.print("Ingrese nomenclatura de la criptomoneda que desea intercambiar (ej. BTC): ");
        Criptomoneda cInicial = new Criptomoneda(s.nextLine().toUpperCase());
        System.out.print("Ingrese nomenclatura de la criptomoneda que desea recibir (ej. ETH): ");
        Criptomoneda cFinal = new Criptomoneda(s.nextLine().toUpperCase());

        System.out.print("Ingrese la cantidad que desea intercambiar: ");
        double cantidad = s.nextDouble();
        s.nextLine();

        GestorSwap swap = new GestorSwap();
        double valorSimulado = swap.valorFinalSwap(cInicial, cantidad, cFinal);

        System.out.println("\n-------------------------------------");
        System.out.printf("Intercambio de %.2f %s a %.2f %s.\n",
                cantidad, cInicial.getNomenclatura(),
                valorSimulado, cFinal.getNomenclatura());
        System.out.println("-------------------------------------");

        System.out.print("¿Desea confirmar esta operación? (y/n): ");
        String confirmacion = s.nextLine().toLowerCase();

        if (confirmacion.equals("y")) {
            Result resultado = swap.simularSwap(cInicial, cantidad, cFinal, valorSimulado);
            System.out.println(resultado.informar());
        } else {
            System.out.println("\nOperación cancelada.");
        }

        System.out.println("=====================================");
    }

    private void listarMonedas(Scanner s) {
        System.out.println("=====================================");
        System.out.println("           Listar Monedas            ");
        System.out.println("=====================================");

        System.out.print("¿Desea ordenar por nomenclatura? (y/n): ");
        String ordenar = s.next().toLowerCase();

        boolean ordenarPorNomenclatura;
        if (ordenar.equals("y")) {
            ordenarPorNomenclatura = true;
        } else if (ordenar.equals("n")) {
            ordenarPorNomenclatura = false;
        } else {
            System.out.println("Opción no válida. Debe ser 'y' (sí) o 'n' (no).");
            return;
        }

        List<Moneda> lista = monedaOp.listarMonedas();
        if (ordenarPorNomenclatura) {
            lista.sort(new ComparatorNomenclaturaMoneda());
        } else {
            lista.sort(new ComparatorValorDolar());
        }

        System.out.println("\n-------------------------------------");
        System.out.println("         Detalle de Monedas        ");
        System.out.println("-------------------------------------");

        for (Moneda moneda : lista) {
            System.out.println(moneda);
            System.out.println("-------------------------------------");
        }

        System.out.println("=====================================");
    }

    private void generarMonedas(Scanner s) {
        System.out.println("=====================================");
        System.out.println("        Ingresar Nueva Moneda      ");
        System.out.println("=====================================");

        System.out.println("¿Qué tipo de moneda desea ingresar?");
        System.out.println("1. Criptomoneda");
        System.out.println("2. Fiat");
        int tipoMoneda = s.nextInt();
        s.nextLine();

        Moneda moneda = null;

        System.out.print("Ingrese el nombre de la moneda: ");
        String nombre = s.nextLine();

        System.out.print("Ingrese la nomenclatura de la moneda: ");
        String nomenclatura = s.nextLine().toUpperCase();

        if (!nomenclaturasPermitidas.contains(nomenclatura)) {
            System.err.println("Nomenclatura inválida. Intente nuevamente con una nomenclatura válida.");
            return;
        }

        System.out.print("Ingrese el valor en dolares de la moneda: ");
        double valorDolar = s.nextDouble();
        s.nextLine();

        switch (tipoMoneda) {
            case 1: // Criptomoneda
                System.out.print("Ingrese la volatilidad de la moneda: ");
                double volatilidad = s.nextDouble();
                s.nextLine();
                System.out.print("Ingrese el stock de la moneda: ");
                double stock = s.nextDouble();
                s.nextLine();
                moneda = new Criptomoneda('C', nombre, nomenclatura, valorDolar, volatilidad, stock);
                break;

            case 2: // Fiat
                moneda = new Fiat('F', nombre, nomenclatura, valorDolar);
                break;

            default:
                System.out.println("Opción no válida. Operación cancelada.");
                return;
        }

        System.out.println("\n-------------------------------------");
        System.out.println("Resumen de la Moneda a Ingresar:");
        System.out.println(moneda);
        System.out.println("-------------------------------------");

        System.out.print("\n¿Está seguro que desea ingresar la moneda a la base de datos? (y/n): ");
        String confirmacion = s.nextLine();

        if (confirmacion.equalsIgnoreCase("y")) {
            boolean exito = monedaOp.generarMoneda(moneda);
            if (exito) {
                System.out.println("Moneda ingresada exitosamente.");
            } else {
                System.out.println("Error en el ingreso de la moneda.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }

        System.out.println("=====================================");
    }

    private void generarStock(Scanner s) {
        System.out.println("=====================================");
        System.out.println("       Generar Stock de Monedas      ");
        System.out.println("=====================================");

        // Confirmación para generar stock aleatorio
        System.out.print("¿Desea generar una cantidad de monedas aleatoria para los usuarios de la billetera? (y/n): ");
        boolean generar = s.nextLine().equalsIgnoreCase("y");

        if (generar) {
            List<String> lista = monedaOp.generarStock();

            if (lista.isEmpty()) {
                System.out.println("No se ha generado stock. Verifique la configuración o existencia de monedas.");
            } else {
                // Mostrar lista de monedas generadas con formato
                System.out.println("\n-------------------------------------");
                System.out.println("           Stock Generado            ");
                System.out.println("-------------------------------------");
                for (String moneda : lista) {
                    System.out.println("- " + moneda);
                }
                System.out.println("-------------------------------------");
                System.out.println("Total de monedas generadas: " + lista.size());
            }
        } else {
            System.out.println("Operación cancelada. No se ha generado stock.");
        }

        System.out.println("=====================================");
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
                    generarMonedas(s);
                    break;
                case 2:
                    listarMonedas(s);
                    break;
                case 3:
                    generarStock(s);
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
                    generarCompra(s);

                    break;
                case 8:
                    simularSwap(s);
                    break;
                case 9:
                    menu_detallado = !menu_detallado;
                    break;
                case 0:
                    System.out.println("Finalizando la ejecución del programa!");
                    break;
                default:
                    System.out.println("Opcion incorrecta ");
                    break;
            }
        }

        s.close();

    }

}
