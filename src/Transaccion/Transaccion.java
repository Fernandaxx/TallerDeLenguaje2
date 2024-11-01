package Transaccion;

import java.time.LocalDateTime;

/**
 * La clase abstracta Transaccion representa una transacción dentro del sistema
 * billetera virtual.
 * Define atributos comunes como el costo de la comisión, la demora, la fecha de
 * la transacción y el código asociado.
 * Esta clase deberá ser extendida por tipos específicos de transacciones.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public abstract class Transaccion {

    private LocalDateTime fecha;
    private String resumen;

    /**
     * Constructor por defecto de la clase Transaccion.
     */
    public Transaccion() {

    }

    public Transaccion(LocalDateTime fecha, String resumen) {
        this.fecha = fecha;
        this.resumen = resumen;

    }

    /**
     * Obtiene la fecha de la transacción.
     * 
     * @return La fecha de la transacción como una instancia de LocalDate.
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la transacción.
     * 
     * @param fecha La nueva fecha como una instancia de LocalDate.
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

}
