package Moneda;

/**
 * La clase Fiat representa una moneda Fiat en el sistema Billetera
 * Virtual.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class Fiat extends Moneda {

    /**
     * Constructor por defecto de la clase Fiat.
     */
    public Fiat() {
    }

    /**
     * Constructor de la clase Fiat que inicializa todos los atributos.
     *
     * @param tipo         El tipo de la moneda Fiat.
     * @param nombre       El nombre de la moneda Fiat.
     * @param nomenclatura La nomenclatura de la moneda Fiat.
     * @param valor_dolar  El valor en dólares de la moneda Fiat.
     */
    public Fiat(char tipo, String nombre, String nomenclatura, double valor_dolar) {
        super(tipo, nombre, nomenclatura, valor_dolar);
    }

    /**
     * Constructor de la clase Fiat que inicializa la nomenclatura.
     *
     * @param nomenclatura La nomenclatura de la moneda Fiat.
     */
    public Fiat(String nomenclatura) {
        super(nomenclatura);
    }
}
