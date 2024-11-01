package Transaccion;

import java.time.LocalDateTime;

import Moneda.Criptomoneda;
import Moneda.Fiat;

/**
 * La clase Compra representa una transacción de compra en el sistema Billetera
 * Virtual.
 * Esta transacción involucra la compra de una criptomoneda utilizando una
 * moneda fiat.
 * 
 * @author Grupo13
 * @version 2.0
 * @since 2024
 */
public class Compra extends Transaccion {
    private Fiat fiat; // Moneda fiat utilizada en la compra
    private Criptomoneda cripto; // Criptomoneda comprada
    private double cantidad; // Cantidad de criptomonedas compradas
    private double cantidadFiat; // Cantidad de moneda fiat gastada

    /**
     * Constructor que inicializa una transacción de compra con la fecha, moneda
     * fiat,
     * criptomoneda y cantidad de criptomonedas.
     *
     * @param fecha    La fecha y hora de la transacción.
     * @param fiat     La moneda fiat utilizada en la compra.
     * @param cripto   La criptomoneda comprada.
     * @param cantidad La cantidad de criptomonedas compradas.
     */
    public Compra(LocalDateTime fecha, Fiat fiat, Criptomoneda cripto, double cantidad) {
        super.setFecha(fecha);
        this.fiat = fiat;
        this.cripto = cripto;
        this.cantidad = cantidad;
    }

    /**
     * Constructor que inicializa una transacción de compra con todos los atributos,
     * incluyendo la cantidad de moneda fiat gastada y un resumen de la transacción.
     *
     * @param fecha        La fecha y hora de la transacción.
     * @param fiat         La moneda fiat utilizada en la compra.
     * @param cripto       La criptomoneda comprada.
     * @param cantidad     La cantidad de criptomonedas compradas.
     * @param cantidadFiat La cantidad de moneda fiat gastada en la compra.
     * @param resumen      Un resumen de la transacción.
     */
    public Compra(LocalDateTime fecha, Fiat fiat, Criptomoneda cripto, double cantidad, double cantidadFiat,
            String resumen) {
        super(fecha, resumen);
        this.fiat = fiat;
        this.cripto = cripto;
        this.cantidad = cantidad;
        this.cantidadFiat = cantidadFiat;
    }

    /**
     * Constructor por defecto de la clase Compra.
     */
    public Compra() {
    }

    /**
     * Obtiene la moneda fiat utilizada en la compra.
     *
     * @return La moneda fiat utilizada en la compra.
     */
    public Fiat getFiat() {
        return fiat;
    }

    /**
     * Establece la moneda fiat utilizada en la compra.
     *
     * @param fiat La nueva moneda fiat utilizada en la compra.
     */
    public void setFiat(Fiat fiat) {
        this.fiat = fiat;
    }

    /**
     * Obtiene la criptomoneda comprada.
     *
     * @return La criptomoneda comprada.
     */
    public Criptomoneda getCripto() {
        return cripto;
    }

    /**
     * Establece la criptomoneda comprada.
     *
     * @param cripto La nueva criptomoneda comprada.
     */
    public void setCripto(Criptomoneda cripto) {
        this.cripto = cripto;
    }

    /**
     * Obtiene la cantidad de criptomonedas compradas.
     *
     * @return La cantidad de criptomonedas compradas.
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de criptomonedas compradas.
     *
     * @param cantidad La nueva cantidad de criptomonedas compradas.
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la cantidad de moneda fiat gastada en la compra.
     *
     * @return La cantidad de moneda fiat gastada en la compra.
     */
    public double getCantidadFiat() {
        return this.cantidadFiat;
    }

    /**
     * Establece la cantidad de moneda fiat gastada en la compra.
     *
     * @param cantidadFiat La nueva cantidad de moneda fiat gastada en la compra.
     */
    public void setCantidadFiat(double cantidadFiat) {
        this.cantidadFiat = cantidadFiat;
    }

}
