package Activo;

/**
 * Representa un activo financiero abstracto.
 * Esta clase proporciona una representación básica de un activo con una
 * cantidad asociada.
 */
public abstract class Activo {
    private double cantidad;

    /**
     * Crea un nuevo activo con la cantidad especificada.
     *
     * @param cantidad La cantidad del activo.
     */
    public Activo(double cantidad) {
        // Inicialización de la cantidad
    }

    /**
     * Constructor por defecto
     */
    public Activo() {
    }

    /**
     * Obtiene la cantidad del activo.
     *
     * @return La cantidad del activo.
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del activo.
     *
     * @param cantidad La nueva cantidad del activo.
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
