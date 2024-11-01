package Moneda;

/**
 * La clase Criptomoneda representa una moneda criptomoneda en el sistema
 * Billetera Virtual, con su volatilidad y el stock de la misma.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class Criptomoneda extends Moneda {

    private double volatilidad;
    private double stock;

    /**
     * Constructor por defecto de la clase Criptomoneda.
     */
    public Criptomoneda() {
    }

    /**
     * Constructor de la clase Criptomoneda que inicializa todos los atributos.
     *
     * @param tipo         El tipo de la criptomoneda.
     * @param nombre       El nombre de la criptomoneda.
     * @param nomenclatura La nomenclatura de la criptomoneda.
     * @param valor_dolar  El valor en dólares de la criptomoneda.
     * @param volatilidad  La volatilidad de la criptomoneda.
     * @param stock        El stock de la criptomoneda.
     */
    public Criptomoneda(char tipo, String nombre, String nomenclatura, double valor_dolar, double volatilidad,
            double stock) {
        super(tipo, nombre, nomenclatura, valor_dolar);
        this.volatilidad = volatilidad;
        this.stock = stock;
    }

    /**
     * Constructor de la clase Criptomoneda que inicializa la nomenclatura.
     *
     * @param nomenclatura La nomenclatura de la criptomoneda.
     */
    public Criptomoneda(String nomenclatura) {
        super(nomenclatura);
    }

    /**
     * Obtiene el stock de la criptomoneda.
     * 
     * @return El stock de la criptomoneda.
     */
    public double getStock() {
        return stock;
    }

    /**
     * Establece el stock de la criptomoneda.
     * 
     * @param stock El nuevo stock de la criptomoneda.
     */
    public void setStock(double stock) {
        this.stock = stock;
    }

    /**
     * Obtiene la volatilidad de la criptomoneda.
     * 
     * @return La volatilidad de la criptomoneda.
     */
    public double getVolatilidad() {
        return volatilidad;
    }

    /**
     * Establece la volatilidad de la criptomoneda.
     * 
     * @param volatilidad La nueva volatilidad de la criptomoneda.
     */
    public void setVolatilidad(double volatilidad) {
        this.volatilidad = volatilidad;
    }

    /**
     * Representa la información de la criptomoneda como una cadena de texto.
     * 
     * @return Una cadena que representa la criptomoneda con sus atributos.
     */
    @Override
    public String toString() {
        return super.toString() +
                "\tVolatilidad = " + volatilidad + "\n" +
                "\tStock = " + stock + "\n";
    }
}
