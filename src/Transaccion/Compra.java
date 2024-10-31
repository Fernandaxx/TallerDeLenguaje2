package Transaccion;

import java.time.LocalDateTime;

import Moneda.Criptomoneda;
import Moneda.Fiat;

public class Compra extends Transaccion {
    private Fiat fiat;
    private Criptomoneda cripto;
    private double cantidad;

    public Compra(LocalDateTime fecha, String fiat, String cripto, double cantidad) {
        super(fecha);
        this.fiat = fiat;
        this.cripto = cripto;
        this.cantidad = cantidad;
    }
    
    public Compra(){

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

    
    //pa que quedan estos??
    @Override
    public void obtenerComision() {
        // Implementar la lógica para obtener la comisión.
    }

    @Override
    public void opcionesDemora() {
        // Implementar la lógica para seleccionar opciones de demora.
    }

   
}
