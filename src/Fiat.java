/**
 * La clase Fiat representa una criptomoneda en el sistema Billetera
 * Virtual
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class Fiat extends Moneda {

    public Fiat() {

    }

    public Fiat(char tipo, String nombre, String nomenclatura, double valor_dolar) {
        super(tipo, nombre, nomenclatura, valor_dolar);
    }

}
