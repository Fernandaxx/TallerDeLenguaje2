package Activo;

import Moneda.Fiat;

/**
 * Representa un activo financiero específico de tipo moneda fiduciaria.
 * Esta clase extiende la clase {@link Activo} y proporciona funcionalidad
 * para manejar activos que son monedas fiduciarias.
 */
public class ActivoFiat extends Activo {
    private Fiat fiat = new Fiat();

    /**
     * Crea un nuevo activo fiduciario por defecto.
     */
    public ActivoFiat() {
        super();
    }

    /**
     * Crea un nuevo activo fiduciario con la cantidad especificada
     * y una instancia de moneda fiduciaria.
     *
     * @param cantidad La cantidad del activo fiduciario.
     * @param fiat     La moneda fiduciaria asociada a este activo.
     */
    public ActivoFiat(double cantidad, Fiat fiat) {
        super(cantidad);
        this.fiat = fiat;
    }

    /**
     * Crea un nuevo activo fiduciario con la cantidad especificada
     * y una nomenclatura de moneda fiduciaria.
     *
     * @param cantidad     La cantidad del activo fiduciario.
     * @param nomenclatura La nomenclatura de la moneda fiduciaria.
     */
    public ActivoFiat(double cantidad, String nomenclatura) {
        super.setCantidad(cantidad);
        this.fiat.setNomenclatura(nomenclatura);
    }

    /**
     * Obtiene la moneda fiduciaria asociada a este activo fiduciario.
     *
     * @return La moneda fiduciaria asociada.
     */
    public Fiat getFiat() {
        return fiat;
    }

    /**
     * Establece la moneda fiduciaria asociada a este activo fiduciario.
     *
     * @param fiat La nueva moneda fiduciaria asociada.
     */
    public void setFiat(Fiat fiat) {
        this.fiat = fiat;
    }

    /**
     * Devuelve una representación en cadena de este activo fiduciario,
     * incluyendo su nomenclatura y cantidad.
     *
     * @return Una cadena que representa el activo fiduciario.
     */
    @Override
    public String toString() {
        return "ActivoFiat " + fiat.getNomenclatura() + " : " + super.getCantidad();
    }
}
