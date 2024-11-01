package Moneda;

/**
 * La clase Stock representa el stock de una criptomoneda en el sistema.
 * Contiene la nomenclatura de la moneda y la cantidad disponible.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class Stock {

    private String nomenclatura; // Nomenclatura de la criptomoneda
    private double cantidad; // Cantidad disponible de la criptomoneda

    /**
     * Constructor que inicializa un objeto Stock con la nomenclatura y cantidad
     * especificadas.
     *
     * @param nomenclatura La nomenclatura de la criptomoneda.
     * @param cantidad     La cantidad disponible de la criptomoneda.
     */
    public Stock(String nomenclatura, double cantidad) {
        this.nomenclatura = nomenclatura;
        this.cantidad = cantidad;
    }

    /**
     * Constructor por defecto de la clase Stock.
     */
    public Stock() {
    }

    /**
     * Obtiene la nomenclatura de la criptomoneda.
     *
     * @return La nomenclatura de la criptomoneda.
     */
    public String getNomenclatura() {
        return nomenclatura;
    }

    /**
     * Establece la nomenclatura de la criptomoneda.
     *
     * @param nomenclatura La nueva nomenclatura de la criptomoneda.
     */
    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    /**
     * Obtiene la cantidad disponible de la criptomoneda.
     *
     * @return La cantidad disponible de la criptomoneda.
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible de la criptomoneda.
     *
     * @param cantidad La nueva cantidad disponible de la criptomoneda.
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Stock.
     *
     * @return Una cadena que muestra la nomenclatura y la cantidad de la
     *         criptomoneda.
     */
    @Override
    public String toString() {
        return " Cantidad de " + nomenclatura + " : " + cantidad + "\n";
    }
}
