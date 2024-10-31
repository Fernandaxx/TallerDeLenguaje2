package Moneda;

/**
 * La clase Fiat representa una moneda Fiat en el sistema Billetera
 * Virtual
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
     * Constructor de la clase Fiat.
     */

    public Fiat(char tipo, String nombre, String nomenclatura, double valor_dolar) {
        super(tipo, nombre, nomenclatura, valor_dolar);
    }

}
