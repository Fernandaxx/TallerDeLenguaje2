package Transaccion;

import java.time.LocalDateTime;

import Moneda.Criptomoneda;

public class Swap extends Transaccion {
    private Criptomoneda criptoEnvio;
    private double cantidad;
    private Criptomoneda criptoRecepcion;
    private double cantidadRecepcion;

    public Swap(Criptomoneda criptoEnvio, double cantidad, Criptomoneda criptoRecepcion, double cantidadRecepcion,
            LocalDateTime fecha, String resumen) {
        super(fecha, resumen);
        this.criptoEnvio = criptoEnvio;
        this.cantidad = cantidad;
        this.criptoRecepcion = criptoRecepcion;
        this.cantidadRecepcion = cantidadRecepcion;
    }

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
