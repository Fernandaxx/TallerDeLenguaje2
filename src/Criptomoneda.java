/**
 * La clase Criptomoneda representa una criptomoneda en el sistema Billetera
 * Virtual, con su
 * nombre, sigla y cantidad de la misma.
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public class Criptomoneda extends Moneda {
<<<<<<< HEAD
    private double volatilidad;
    private double stock;
=======
    private double volatilidad; // va en criptomoneda -> para Lu
>>>>>>> ab4d176c86c56741b58675ec032ed14c0fe88970

    /**
     * Constructor por defecto de la clase Criptomoneda.
     */

    public Criptomoneda() {
        

    }

<<<<<<< HEAD
    public Criptomoneda(char tipo, String nombre, String nomenclatura, double valor_dolar, double volatilidad,
            double stock) {
        super(tipo, nombre, nomenclatura, valor_dolar);
        this.volatilidad = volatilidad;
        this.stock = stock;
=======
    
    public Criptomoneda(char tipo, String nombre, String nomenclatura, double valor_dolar, double volatilidad,
            double stock, String nombre2, String sigla, double volatilidad2) {
        super(tipo, nombre, nomenclatura, valor_dolar, volatilidad, stock);
        this.volatilidad = volatilidad;
    }


    public Criptomoneda(String nombre, String sigla, double volatilidad) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.volatilidad = volatilidad;
    }


    /**
     * Obtiene el nombre de la criptomoneda.
     * 
     * @return El nombre de la criptomoneda.
     */
    public String getNombre() {
        return nombre;
>>>>>>> ab4d176c86c56741b58675ec032ed14c0fe88970
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
     * @param nombre El stock de la criptomoneda.
     */
    public void setNStock(double stock) {
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
     * @param sigla La nueva volatilidad de la criptomoneda.
     */
    public void setSVolatilidad(double volatilidad) {
        this.volatilidad = volatilidad;
    }

}
