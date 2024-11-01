package Transaccion;

import java.time.LocalDateTime;

import Moneda.Criptomoneda;

/**
 * La clase Swap representa una transacción de intercambio (swap) entre dos
 * criptomonedas.
 * Hereda de la clase Transaccion y contiene información sobre la criptomoneda
 * enviada,
 * la cantidad enviada, la criptomoneda recibida y la cantidad recibida.
 * 
 * @author Grupo13
 * @version 1.0
 * @since 2024
 */
public class Swap extends Transaccion {
    private Criptomoneda criptoEnvio;
    private double cantidad;
    private Criptomoneda criptoRecepcion;
    private double cantidadRecepcion;

    /**
     * Constructor de la clase Swap que inicializa los atributos de la transacción.
     *
     * @param criptoEnvio       La criptomoneda que se envía.
     * @param cantidad          La cantidad de la criptomoneda que se envía.
     * @param criptoRecepcion   La criptomoneda que se recibe.
     * @param cantidadRecepcion La cantidad de la criptomoneda que se recibe.
     * @param fecha             La fecha y hora de la transacción.
     * @param resumen           Un resumen de la transacción.
     */
    public Swap(Criptomoneda criptoEnvio, double cantidad, Criptomoneda criptoRecepcion, double cantidadRecepcion,
            LocalDateTime fecha, String resumen) {
        super(fecha, resumen);
        this.criptoEnvio = criptoEnvio;
        this.cantidad = cantidad;
        this.criptoRecepcion = criptoRecepcion;
        this.cantidadRecepcion = cantidadRecepcion;
    }

    /**
     * Constructor de la clase Swap que inicializa los atributos de la transacción
     * sin resumen.
     *
     * @param criptoEnvio     La criptomoneda que se envía.
     * @param cantidad        La cantidad de la criptomoneda que se envía.
     * @param criptoRecepcion La criptomoneda que se recibe.
     */
    public Swap(Criptomoneda criptoEnvio, double cantidad, Criptomoneda criptoRecepcion) {
        this.criptoEnvio = criptoEnvio;
        this.cantidad = cantidad;
        this.criptoRecepcion = criptoRecepcion;
    }

    public Criptomoneda getCriptoEnvio() {
        return criptoEnvio;
    }

    public void setCriptoEnvio(Criptomoneda criptoEnvio) {
        this.criptoEnvio = criptoEnvio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Criptomoneda getCriptoRecepcion() {
        return criptoRecepcion;
    }

    public void setCriptoRecepcion(Criptomoneda criptoRecepcion) {
        this.criptoRecepcion = criptoRecepcion;
    }

    public double getCantidadRecepcion() {
        return cantidadRecepcion;
    }

    public void setCantidadRecepcion(double cantidadRecepcion) {
        this.cantidadRecepcion = cantidadRecepcion;
    }
}
