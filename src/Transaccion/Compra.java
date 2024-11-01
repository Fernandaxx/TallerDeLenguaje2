package Transaccion;

import java.time.LocalDateTime;

import Moneda.Criptomoneda;
import Moneda.Fiat;

public class Compra extends Transaccion {
    private Fiat fiat;
    private Criptomoneda cripto;
    private double cantidad;
    private double cantidadFiat;

    public Compra(LocalDateTime fecha, Fiat fiat, Criptomoneda cripto, double cantidad) {
        super.setFecha(fecha);
        this.fiat = fiat;
        this.cripto = cripto;
        this.cantidad = cantidad;
    }

    public Compra(LocalDateTime fecha, Fiat fiat, Criptomoneda cripto, double cantidad, double cantidadFiat,
            String resumen) {
        super(fecha, resumen);
        this.fiat = fiat;
        this.cripto = cripto;
        this.cantidad = cantidad;
        this.cantidadFiat = cantidadFiat;
    }

    public Compra() {

    }

    public Fiat getFiat() {
        return fiat;
    }

    public void setFiat(Fiat fiat) {
        this.fiat = fiat;
    }

    public Criptomoneda getCripto() {
        return cripto;
    }

    public void setCripto(Criptomoneda cripto) {
        this.cripto = cripto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidadFiat() {
        return this.cantidadFiat;
    }

    public void setCantidadFiat(double cantidadFiat) {
        this.cantidadFiat = cantidadFiat;
    }

}
