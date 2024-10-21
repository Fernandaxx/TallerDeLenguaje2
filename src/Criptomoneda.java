/**
 * La clase Criptomoneda representa una criptomoneda en el sistema Billetera
 * Virtual, con su
 * nombre, sigla y cantidad de la misma.
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public class Criptomoneda {
    private String nombre;
    private String sigla;
    private double cantidad;

    /**
     * Constructor por defecto de la clase Criptomoneda.
     */

    public Criptomoneda() {

    }

    /**
     * Obtiene el nombre de la criptomoneda.
     * 
     * @return El nombre de la criptomoneda.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la criptomoneda.
     * 
     * @param nombre El nombre de la criptomoneda.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la sigla (abreviatura) de la criptomoneda.
     * 
     * @return La sigla de la criptomoneda.
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Establece la sigla (abreviatura) de la criptomoneda.
     * 
     * @param sigla La nueva sigla de la criptomoneda.
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * Obtiene la cantidad de la criptomoneda disponible.
     * 
     * @return La cantidad de la criptomoneda.
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de la criptomoneda disponible.
     * 
     * @param cantidad La nueva cantidad de la criptomoneda.
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
