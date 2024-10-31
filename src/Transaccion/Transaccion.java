package Transaccion;

import java.time.Duration;
import java.time.LocalDate;

/**
 * La clase abstracta Transaccion representa una transacción dentro del sistema
 * billetera virtual.
 * Define atributos comunes como el costo de la comisión, la demora, la fecha de
 * la transacción y el código asociado.
 * Esta clase deberá ser extendida por tipos específicos de transacciones.
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public abstract class Transaccion {
    // private double costoComision;
    // private Duration demora;
    private LocalDate fecha;
    private String codigo;

    /**
     * Constructor por defecto de la clase Transaccion.
     */
    public Transaccion() {

    }

    /**
     * Método para obtener la comisión de la transacción (mediante una Blockchain).
     * Implementación a ser definida por las subclases.
     */
    public abstract void obtenerComision();
    // Lógica para obtener la comisión

    public Transaccion(LocalDate fecha) {
        // this.costoComision = costoComision;
        // this.demora = demora;
        this.fecha = fecha;
        // this.codigo = codigo;
    }

    /**
     * Obtiene las opciones de demora para la transacción (mediante una
     * Blockchain).
     * Se espera que las subclases implementen la lógica específica para calcularlo.
     * .
     */
    public abstract void opcionesDemora();
    // Implementar lógica de opciones de demora

    /**
     * Obtiene el costo de la comisión asociado a la transacción.
     * 
     * @return El costo de la comisión.
     */
    public double getCostoComision() {
        return costoComision;
    }

    /**
     * Establece el costo de la comisión asociado a la transacción.
     * 
     * @param costoComision El nuevo costo de la comisión.
     */
    public void setCostoComision(double costoComision) {
        this.costoComision = costoComision;
    }

    /**
     * Obtiene la demora esperada para la transacción.
     * 
     * @return La demora como una instancia de Duration.
     */
    public Duration getDemora() {
        return demora;
    }

    /**
     * Establece la demora esperada para la transacción.
     * 
     * @param demora demora asociada a la transaccion.
     */
    public void setDemora(Duration demora) {
        this.demora = demora;
    }

    /**
     * Obtiene la fecha de la transacción.
     * 
     * @return La fecha de la transacción como una instancia de LocalDate.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la transacción.
     * 
     * @param fecha La nueva fecha como una instancia de LocalDate.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el código único de la transacción.
     * 
     * @return El código de la transacción.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código único de la transacción.
     * 
     * @param codigo El código de la transacción.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
